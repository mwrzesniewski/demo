package pl.wrzesniewski.demo.user.exception;



public class UserNotFoundException extends Exception{

        public UserNotFoundException(String identifier) {
        super("The user with username" + identifier+ "was not found");

    }


}
