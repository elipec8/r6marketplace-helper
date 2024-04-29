package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.database.neo4j.entities.EntitiesStatics.*;
import static org.junit.jupiter.api.Assertions.*;

class UbiCredentialsEntityTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(UBI_CREDENTIALS, UBI_CREDENTIALS_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(UBI_CREDENTIALS, UBI_CREDENTIALS_ALT_ID);
        assertNotEquals(UBI_CREDENTIALS, UBI_CREDENTIALS_ALT_EMAIL);
        assertNotEquals(UBI_CREDENTIALS, UBI_CREDENTIALS_ALT_PASSWORD);
        assertNotEquals(UBI_CREDENTIALS, UBI_CREDENTIALS_ALT_UBI_SESSION_ID);
        assertNotEquals(UBI_CREDENTIALS, UBI_CREDENTIALS_ALT_UBI_AUTH_TICKET);
    }
}