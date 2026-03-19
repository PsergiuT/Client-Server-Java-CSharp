package Service;

import Domain.Bilet;
import Domain.Meci;
import Repo.Implementation.RepoBiletDB;
import Repo.Implementation.RepoMeciDB;
import Validator.RepoException;
import Validator.ValidationException;

import java.text.ParseException;
import java.util.List;

public class ServiceCaserie {
    private final RepoMeciDB repoMeci;
    private final RepoBiletDB repoBilet;

    public ServiceCaserie(RepoMeciDB repoMeci, RepoBiletDB repoBilet) {
        this.repoMeci = repoMeci;
        this.repoBilet = repoBilet;
    }


    public List<Meci> findAll() throws ValidationException{
        try {
            return repoMeci.findAll();
        }
        catch(RepoException rex){
            throw new ValidationException(rex.getErrors());
        }
    }


    public void vanzareBilet(Meci m, String numeClient, String adresaClient, String nr_locuri_string) throws ValidationException{
        try {
            //field validation
            if(numeClient == "" || adresaClient == "" || nr_locuri_string == ""){
                throw new ValidationException(List.of("All fields must be filled"));
            }else if(m == null){
                throw new ValidationException(List.of("Meciul nu a fost selectat"));
            }

            //logic validation
            Integer nr_locuri = Integer.parseInt(nr_locuri_string);
            if(nr_locuri < 0){
                throw new ValidationException(List.of("Numarul de locuri trebuie sa fie pozitiv"));
            }else if(m.getNr_locuri() < nr_locuri){
                throw new ValidationException(List.of("Nu mai sunt destule locuri disponibile"));
            }

            //repo validation
            if(m.getNr_locuri() == nr_locuri){
                repoMeci.setSoldOut(m.getId(), true);
            }
            repoMeci.vanzareLocuri(m.getId(), nr_locuri);
            repoBilet.vanzareBilet(m.getId(), numeClient, adresaClient, nr_locuri);
        }
        catch(NumberFormatException nfe){
            throw new ValidationException(List.of("Introdu un numar valid de locuri"));
        }
        catch(RepoException rex){
            throw new ValidationException(rex.getErrors());
        }
    }


    public List<Meci> cautaMeciuri(String adresaClient, String numeClient) throws ValidationException{
        try {
            //repo validation
            return repoMeci.cautare(adresaClient, numeClient);
        }
        catch(RepoException rex){
            throw new ValidationException(rex.getErrors());
        }
    }


    public void modificaLocuri(String id_bilet_string, String numar_locuri_string) throws ValidationException{
        try{
            //field validation
            if(id_bilet_string == "" || numar_locuri_string == ""){
                throw new ValidationException(List.of("All fields must be filled"));
            }
            Long id_bilet = Long.parseLong(id_bilet_string);
            Integer numarLocuri = Integer.parseInt(numar_locuri_string);


            //Search for assigned Meci
            Bilet b = repoBilet.findById(id_bilet);
            if(b == null){
                throw new ValidationException(List.of("Biletul nu a fost gasit"));
            }
            Meci m = repoMeci.findById(b.getId_meci());
            if(m == null){
                throw new ValidationException(List.of("Meciul nu mai exista"));
            }

            //logic validation
            if(numarLocuri < 0){
                throw new ValidationException(List.of("Numarul de locuri trebuie sa fie pozitiv"));
            }
            if(numarLocuri - b.getNr_locuri() > 0) {
                if (m.getNr_locuri() < numarLocuri - b.getNr_locuri()) {
                    throw new ValidationException(List.of("Nu mai sunt destule locuri disponibile"));
                }
            }

            //repo validation
            if(m.getNr_locuri() == numarLocuri - b.getNr_locuri()){
                repoMeci.setSoldOut(m.getId(), true);
            }
            repoMeci.vanzareLocuri(m.getId(), numarLocuri - b.getNr_locuri());
            repoBilet.modifica(id_bilet, numarLocuri);


        }
        catch(NumberFormatException nfe){
            throw new ValidationException(List.of("Introdu un numar valid de locuri sau id valid pentru bilet"));
        }
        catch(RepoException rex){
            throw new ValidationException(rex.getErrors());
        }

    }

}
