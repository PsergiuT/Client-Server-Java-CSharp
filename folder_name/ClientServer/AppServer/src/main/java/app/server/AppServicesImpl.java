package app.server;

import app.model.implementation.Bilet;
import app.model.implementation.Meci;
import app.model.implementation.Users;
import app.persistence.IBiletRepository;
import app.persistence.IMeciRepository;
import app.persistence.IUserRepository;
import app.persistence.jdbc.exceptions.RepoException;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServicesImpl implements IAppServices {
    private final IUserRepository userRepository;
    private final IBiletRepository biletRepository;
    private final IMeciRepository meciRepository;

    private Map<String, IAppObserver> loggedClients;            // for faster adding and removing clients
    private static final Logger logger = LogManager.getLogger();

    public AppServicesImpl(IUserRepository userRepository, IBiletRepository biletRepository, IMeciRepository meciRepository) {
        this.userRepository = userRepository;
        this.biletRepository = biletRepository;
        this.meciRepository = meciRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Users user, IAppObserver client) throws AppException {
        try{
            if(!userRepository.login(user.getUsername(), user.getPassword())){
                throw new AppException("Invalid username or password");
            }
            loggedClients.put(user.getUsername(), client);
        } catch (RepoException e) {
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public synchronized void logout(Users user, IAppObserver client) throws AppException {
        IAppObserver localObs = loggedClients.remove(user.getUsername());
        if(localObs == null){
            throw new AppException("User not logged in");
        }

    }

    @Override
    public synchronized List<Meci> findAll() throws AppException {
        try{
            return meciRepository.findAll();
        }catch(RepoException e){
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public synchronized void vanzareBilet(Meci m, String numeClient, String adresaClient, String nr_locuri_string) throws AppException {
        try {
            //field validation
            if(Objects.equals(numeClient, "") || Objects.equals(adresaClient, "") || Objects.equals(nr_locuri_string, "")){
                throw new AppException("All fields must be filled");
            }else if(m == null){
                throw new AppException("Meciul nu a fost selectat");
            }

            //logic validation
            Integer nr_locuri = Integer.parseInt(nr_locuri_string);
            if(nr_locuri < 0){
                throw new AppException("Numarul de locuri trebuie sa fie pozitiv");
            }else if(m.getNr_locuri() < nr_locuri){
                throw new AppException("Nu mai sunt destule locuri disponibile");
            }

            //repo validation
            if(Objects.equals(m.getNr_locuri(), nr_locuri)){
                meciRepository.setSoldOut(m.getId(), true);
            }
            meciRepository.vanzareLocuri(m.getId(), nr_locuri);
            biletRepository.vanzareBilet(m.getId(), numeClient, adresaClient, nr_locuri);

            notifyVanzareBilet();
        }
        catch(NumberFormatException nfe){
            throw new AppException("Introdu un numar valid de locuri");
        }
        catch(RepoException rex){
            throw new AppException(rex.getErrors().toString());
        }
    }

    private final int numberOfThreads = 3;
    private void notifyVanzareBilet() {
        logger.debug(loggedClients.toString());
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        // Implemented a thread pool so that the server isn't forced to sit and wait for the workers (clogged clients) to send the responses over the network
        List<Meci> meciuri = findAll();
        for(IAppObserver obs: loggedClients.values()){
            executor.execute( () -> obs.soldTicket(meciuri));
        }
        executor.shutdown();
    }





    @Override
    public synchronized List<Meci> cautaMeciuri(String adresaClient, String numeClient) throws AppException {
        try {
            //repo validation
            return meciRepository.cautare(adresaClient, numeClient);
        }
        catch(RepoException rex){
            throw new AppException(rex.getErrors().toString());
        }
    }





    @Override
    public synchronized void modificaLocuri(String id_bilet_string, String numar_locuri_string) throws AppException {
        try{
            //field validation
            if(Objects.equals(id_bilet_string, "") || Objects.equals(numar_locuri_string, "")){
                throw new AppException("All fields must be filled");
            }
            Long id_bilet = Long.parseLong(id_bilet_string);
            Integer numarLocuri = Integer.parseInt(numar_locuri_string);


            //Search for assigned Meci
            Bilet b = biletRepository.findById(id_bilet);
            if(b == null){
                throw new AppException("Biletul nu a fost gasit");
            }
            Meci m = meciRepository.findById(b.getId_meci());
            if(m == null){
                throw new AppException("Meciul nu mai exista");
            }

            //logic validation
            if(numarLocuri < 0){
                throw new AppException("Numarul de locuri trebuie sa fie pozitiv");
            }
            if(numarLocuri - b.getNr_locuri() > 0) {
                if (m.getNr_locuri() < numarLocuri - b.getNr_locuri()) {
                    throw new AppException("Nu mai sunt destule locuri disponibile");
                }
            }

            //repo validation
            if(m.getNr_locuri() == numarLocuri - b.getNr_locuri()){
                meciRepository.setSoldOut(m.getId(), true);
            }
            meciRepository.vanzareLocuri(m.getId(), numarLocuri - b.getNr_locuri());
            biletRepository.modifica(id_bilet, numarLocuri);

            notifyUpdateBilet();
        }
        catch(NumberFormatException nfe){
            throw new AppException("Introdu un numar valid de locuri sau id valid pentru bilet");
        }
        catch(RepoException rex){
            throw new AppException(rex.getErrors().toString());
        }
    }

    private void notifyUpdateBilet() {
        // Implemented a thread pool so that the server isn't forced to sit and wait for the workers (clogged clients) to send the responses over the network
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Meci> meciuri = findAll();
        for(IAppObserver obs: loggedClients.values()){
            executor.execute( () -> obs.updateTicket(meciuri));
        }
        executor.shutdown();
    }
}
