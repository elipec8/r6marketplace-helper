package github.ricemonger.utils.exceptions;

public class TradeManagerByItemIdDoesntExistException extends RuntimeException {
    public TradeManagerByItemIdDoesntExistException(String message) {
        super(message);
    }
}
