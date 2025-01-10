package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UbiAccountEntryCredentialsEntityTest {

    @Test
    public void getUserId_should_return_user_id() {
        UbiAccountEntryCredentialsEntity entity = new UbiAccountEntryCredentialsEntity();
        entity.setUser(new UserIdEntity(1L));

        assertEquals(1L, entity.getUserId_());
    }

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UbiAccountEntryCredentialsEntity entity1 = new UbiAccountEntryCredentialsEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");
        entity1.setEncodedPassword("encodedPassword");
        entity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");

        UbiAccountEntryCredentialsEntity entity2 = new UbiAccountEntryCredentialsEntity();
        entity2.setUser(new UserIdEntity(1L));
        entity2.setEmail("email");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        UbiAccountEntryCredentialsEntity entity = new UbiAccountEntryCredentialsEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        UbiAccountEntryCredentialsEntity entity1 = new UbiAccountEntryCredentialsEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");
        entity1.setEncodedPassword("encodedPassword");
        entity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");

        UbiAccountEntryCredentialsEntity entity2 = new UbiAccountEntryCredentialsEntity();
        entity2.setUser(new UserIdEntity(1L));
        entity2.setEmail("email");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        UbiAccountEntryCredentialsEntity entity = new UbiAccountEntryCredentialsEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_id_user() {
        UbiAccountEntryCredentialsEntity entity1 = new UbiAccountEntryCredentialsEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");

        UbiAccountEntryCredentialsEntity entity2 = new UbiAccountEntryCredentialsEntity();
        entity2.setUser(new UserIdEntity(2L));
        entity2.setEmail("email");

        assertNotEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_different_id_email() {
        UbiAccountEntryCredentialsEntity entity1 = new UbiAccountEntryCredentialsEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");

        UbiAccountEntryCredentialsEntity entity2 = new UbiAccountEntryCredentialsEntity();
        entity2.setUser(new UserIdEntity(1L));
        entity2.setEmail("email1");

        assertNotEquals(entity1, entity2);
    }

}