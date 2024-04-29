package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbiCredentialsEntityTests {

    public final static UbiCredentialsEntity UBI_CREDENTIALS = new UbiCredentialsEntity("1", "2", "3", "4", "5");

    public final static UbiCredentialsEntity SAME_UBI_CREDENTIALS = new UbiCredentialsEntity("1", "2", "3", "4", "5");

    public final static UbiCredentialsEntity ALT_ID = new UbiCredentialsEntity("99", "2", "3", "4", "5");

    public final static UbiCredentialsEntity ALT_EMAIL = new UbiCredentialsEntity("1", "99", "3", "4", "5");

    public final static UbiCredentialsEntity ALT_PASSWORD = new UbiCredentialsEntity("1", "2", "99", "4", "5");

    public final static UbiCredentialsEntity ALT_UBI_SESSION_ID = new UbiCredentialsEntity("1", "2", "3", "99", "5");

    public final static UbiCredentialsEntity ALT_UBI_AUTH_TICKET = new UbiCredentialsEntity("1", "2", "3", "4", "99");

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(UBI_CREDENTIALS, SAME_UBI_CREDENTIALS);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(UBI_CREDENTIALS, ALT_ID);
        assertNotEquals(UBI_CREDENTIALS, ALT_EMAIL);
        assertNotEquals(UBI_CREDENTIALS, ALT_PASSWORD);
        assertNotEquals(UBI_CREDENTIALS, ALT_UBI_SESSION_ID);
        assertNotEquals(UBI_CREDENTIALS, ALT_UBI_AUTH_TICKET);
    }
}