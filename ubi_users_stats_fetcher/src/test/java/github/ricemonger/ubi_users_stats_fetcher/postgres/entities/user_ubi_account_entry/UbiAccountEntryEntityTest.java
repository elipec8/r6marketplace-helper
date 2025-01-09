package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountEntryEntityTest {

    @Test
    public void getProfileId_should_return_ubi_account_stats_profile_id() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(new UbiAccountStatsIdCreditAmountEntity());
        entity.getUbiAccountStats().setUbiProfileId("profileId");
        assertEquals("profileId", entity.getProfileId_());
    }

    @Test
    public void getProfileId_should_return_null_if_user_is_null() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(null);
        assertNull(entity.getProfileId_());
    }

    @Test
    public void getCreditAmount_should_return_ubi_account_stats_credit_amount() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(new UbiAccountStatsIdCreditAmountEntity());
        entity.getUbiAccountStats().setCreditAmount(100);
        assertEquals(100, entity.getCreditAmount_());
    }

    @Test
    public void getCreditAmount_should_return_null_if_user_is_null() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(null);
        assertNull(entity.getCreditAmount_());
    }
}