package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import java.security.GeneralSecurityException;

public class UbiCredentialsException extends RuntimeException{
    public UbiCredentialsException(Exception e) {
        super(e);
    }
}
