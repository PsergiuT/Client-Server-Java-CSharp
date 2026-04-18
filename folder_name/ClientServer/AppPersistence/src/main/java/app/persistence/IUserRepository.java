package app.persistence;

import app.model.implementation.Users;

public interface IUserRepository extends ICrudRepository<Long, Users>{
    boolean login(String username, String password);
    void logout();
}
