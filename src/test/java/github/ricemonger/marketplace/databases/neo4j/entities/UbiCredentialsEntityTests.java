package github.ricemonger.marketplace.databases.neo4j.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UbiCredentialsEntityTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        Assertions.assertEquals(EntitiesStatics.UBI_CREDENTIALS, EntitiesStatics.UBI_CREDENTIALS_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        Assertions.assertNotEquals(EntitiesStatics.UBI_CREDENTIALS, EntitiesStatics.UBI_CREDENTIALS_ALT_ID);
        Assertions.assertNotEquals(EntitiesStatics.UBI_CREDENTIALS, EntitiesStatics.UBI_CREDENTIALS_ALT_EMAIL);
        Assertions.assertNotEquals(EntitiesStatics.UBI_CREDENTIALS, EntitiesStatics.UBI_CREDENTIALS_ALT_PASSWORD);
        Assertions.assertNotEquals(EntitiesStatics.UBI_CREDENTIALS, EntitiesStatics.UBI_CREDENTIALS_ALT_UBI_SESSION_ID);
        Assertions.assertNotEquals(EntitiesStatics.UBI_CREDENTIALS, EntitiesStatics.UBI_CREDENTIALS_ALT_UBI_AUTH_TICKET);
    }
}