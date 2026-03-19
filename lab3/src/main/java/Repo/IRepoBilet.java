package Repo;

import Domain.Bilet;
import Domain.Meci;
import java.util.List;


public interface IRepoBilet {
    Bilet findById(Long id_bilet);
    void vanzareBilet(Long id_meci, String numeClient, String adresaClient, Integer nr_locuri);
    void modifica(Long id_bilet, Integer numarLocuri);
}
