package github.ricemonger.marketplace.graphs.database.redis.services;

public class InvalidUbiCredentialsException extends RuntimeException{
    public InvalidUbiCredentialsException(String s) {
        super(s);
    }
}
