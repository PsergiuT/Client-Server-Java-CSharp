package app.persistence;

import app.model.implementation.Bilet;

public interface IBiletRepository extends ICrudRepository<Long, Bilet>{
    Bilet findById(Long id_bilet);
    void vanzareBilet(Long id_meci, String numeClient, String adresaClient, Integer nr_locuri);
    void modifica(Long id_bilet, Integer numarLocuri);
}
