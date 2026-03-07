package Repo;

public interface RepoUsers {
    boolean login(String username, String password);
    void logout();
}
