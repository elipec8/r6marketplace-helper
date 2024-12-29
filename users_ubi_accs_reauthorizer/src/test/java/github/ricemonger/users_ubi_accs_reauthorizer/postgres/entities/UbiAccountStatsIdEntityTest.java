package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UbiAccountStatsIdEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UbiAccountStatsIdEntity id1 = new UbiAccountStatsIdEntity("ubiProfileId");
        UbiAccountStatsIdEntity id2 = new UbiAccountStatsIdEntity("ubiProfileId");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        UbiAccountStatsIdEntity id = new UbiAccountStatsIdEntity("ubiProfileId");
        assertEquals(id, id);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        UbiAccountStatsIdEntity id1 = new UbiAccountStatsIdEntity("ubiProfileId");
        UbiAccountStatsIdEntity id2 = new UbiAccountStatsIdEntity("ubiProfileId");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        UbiAccountStatsIdEntity id = new UbiAccountStatsIdEntity("ubiProfileId");
        assertNotEquals(null, id);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        UbiAccountStatsIdEntity id1 = new UbiAccountStatsIdEntity("ubiProfileId1");
        UbiAccountStatsIdEntity id2 = new UbiAccountStatsIdEntity("ubiProfileId2");

        assertNotEquals(id1, id2);
    }
}