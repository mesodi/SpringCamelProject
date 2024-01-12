package es.wacoco.SpringCamelProject.Auth.error;

public class DuplicateEmailException extends RuntimeException {

    private final String email;

    public DuplicateEmailException(String email) {
        super("Email '" + email + "' is already registered. Please use a different email address.");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
