package app.server;

import app.model.implementation.Meci;
import app.model.implementation.Users;
import app.persistence.IBiletRepository;
import app.persistence.IMeciRepository;
import app.persistence.IUserRepository;
import app.persistence.jdbc.exceptions.RepoException;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppServicesImpl implements IAppServices {
    private final IUserRepository userRepository;
    private final IBiletRepository biletRepository;
    private final IMeciRepository meciRepository;

    private Map<String, IAppObserver> loggedClients;            // for faster adding and removing clients

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
    public List<Meci> findAll() throws AppException {
        return List.of();
    }

    @Override
    public void vanzareBilet(Meci m, String numeClient, String adresaClient, String nr_locuri_string) throws AppException {

    }

    @Override
    public List<Meci> cautaMeciuri(String adresaClient, String numeClient) throws AppException {
        return List.of();
    }

    @Override
    public void modificaLocuri(String id_bilet_string, String numar_locuri_string) throws AppException {

    }
}
