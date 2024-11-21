package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemResaleLockWithUbiAccountEntityTest {
    @Test
    public void toItemResaleLock_should_properly_map_with_all_fields() {
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("ubiProfileId");

        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");

        LocalDateTime expiresAt = LocalDateTime.now();

        ItemResaleLockEntity entity = new ItemResaleLockEntity();
        entity.setUbiAccount(ubiAccount);
        entity.setItem(item);
        entity.setExpiresAt(expiresAt);

        ItemResaleLockWithUbiAccount expected = new ItemResaleLockWithUbiAccount("ubiProfileId", "itemId", expiresAt);

        ItemResaleLockWithUbiAccount actual = entity.toItemResaleLock();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_properly_map_with_all_fields() {
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("ubiProfileId");

        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");

        LocalDateTime expiresAt = LocalDateTime.now();

        ItemResaleLockEntity expected = new ItemResaleLockEntity();
        expected.setUbiAccount(ubiAccount);
        expected.setItem(item);
        expected.setExpiresAt(expiresAt);

        ItemResaleLockEntity actual = new ItemResaleLockEntity(ubiAccount, item, expiresAt);

        assertEquals(expected, actual);
    }
}