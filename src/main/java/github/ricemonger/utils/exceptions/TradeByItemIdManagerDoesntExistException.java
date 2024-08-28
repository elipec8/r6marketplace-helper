package github.ricemonger.utils.exceptions;

public class TradeByItemIdManagerDoesntExistException extends RuntimeException {
    public TradeByItemIdManagerDoesntExistException(String message) {
        super(message);
    }
}
