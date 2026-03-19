package Service;

import Repo.Implementation.RepoUsersDB;
import Validator.RepoException;
import Validator.ValidationException;

import java.util.List;

public class ServiceUser {
    private final RepoUsersDB repoUsers;

    public ServiceUser(RepoUsersDB repoUsers) {
        this.repoUsers = repoUsers;
    }

    public void checkCreditentials(String username, String password) throws ValidationException{
        if(username == null || password == null){
            throw new ValidationException(List.of("Username and password cannot be null"));
        }

        try {
            if (repoUsers.login(username, password))
                return;
        }
        catch(RepoException rex){
            throw new ValidationException(rex.getErrors());
        }

        throw new ValidationException(List.of("Invalid username or password"));
    }
}
