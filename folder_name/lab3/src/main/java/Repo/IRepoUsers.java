package Repo;


import Domain.Users;

public interface IRepoUsers extends IRepo<Long, Users>{
    boolean login(String username, String password);
    void logout();
}
