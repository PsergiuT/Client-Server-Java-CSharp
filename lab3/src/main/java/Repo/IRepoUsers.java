package Repo;


public interface IRepoUsers {
    boolean login(String username, String password);
    void logout();
}
