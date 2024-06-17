package github.ricemonger.utils.exceptions;

public class TagDoesntExistException extends RuntimeException{
    public TagDoesntExistException(String message) {
        super(message);
    }
}
