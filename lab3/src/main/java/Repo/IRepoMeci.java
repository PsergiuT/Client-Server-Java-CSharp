package Repo;

import Domain.Meci;
import java.util.List;

public interface IRepoMeci {
    List<Meci> findAll();
    Meci findById(Long id_meci);
    void vanzareLocuri(Long id_meci, Integer nr_locuri);
    List<Meci> cautare(String adresaClient, String numeClient);
    void setSoldOut(Long id_meci, boolean sold_out);
}
