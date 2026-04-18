package Validator;

import java.util.List;

public class RepoException extends RuntimeException {
    private List<String> errors;

    public RepoException(List<String> errors) {
        super(String.join("\n", errors));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
