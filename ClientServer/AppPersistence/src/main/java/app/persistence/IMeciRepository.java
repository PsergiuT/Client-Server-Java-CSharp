package app.persistence;

import app.model.implementation.Meci;

import java.util.List;

public interface IMeciRepository extends ICrudRepository<Long, Meci>{
    List<Meci> findAll();
    Meci findById(Long id_meci);
    void vanzareLocuri(Long id_meci, Integer nr_locuri);
    List<Meci> cautare(String adresaClient, String numeClient);
    void setSoldOut(Long id_meci, boolean sold_out);
}
