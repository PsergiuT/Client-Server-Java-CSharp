package Repo;

import Domain.Meci;
import java.util.List;

public interface InterfaceRepoMeci {
    List<Meci> findAll();
    Meci findById(Long id_meci);
    void vanzare(Integer nr_locuri, Long id_meci);
}
