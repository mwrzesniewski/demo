package pl.wrzesniewski.demo.user.exception;

public class UserExistsException extends Exception {

    public UserExistsException(String email) {
        super("User with email: " + email + " exists");
    }
}
