package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserIdEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UserIdEntity id1 = new UserIdEntity();
        id1.setId(1L);

        UserIdEntity id2 = new UserIdEntity();
        id2.setId(1L);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        UserIdEntity id = new UserIdEntity();
        assertEquals(id, id);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        UserIdEntity id1 = new UserIdEntity();
        id1.setId(1L);

        UserIdEntity id2 = new UserIdEntity();
        id2.setId(1L);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        UserIdEntity id = new UserIdEntity();
        assertNotEquals(null, id);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        UserIdEntity id1 = new UserIdEntity();
        id1.setId(1L);

        UserIdEntity id2 = new UserIdEntity();
        id2.setId(2L);

        assertNotEquals(id1, id2);
    }
}