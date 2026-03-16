package Repo;

import Domain.Meci;
import java.util.List;


public interface InterfaceRepoBilet {
    void vanzareBilet(Meci m, String numeClient, String adresaClient, Integer nr_locuri);
    List<Meci> cautare(String adresaClient, String numeClient);
    void modifica(Long id_bilet, Integer numarLocuri);
}
