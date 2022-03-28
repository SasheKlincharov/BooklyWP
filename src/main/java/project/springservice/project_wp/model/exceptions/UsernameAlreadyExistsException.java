package project.springservice.project_wp.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with name %s already exists",username));
    }
}
