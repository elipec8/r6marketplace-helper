package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.items.ItemResaleLock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemResaleLockEntityTest {
    @Test
    public void toItemResaleLock_should_properly_map_with_all_fields() {
        UbiAccountEntity ubiAccount = new UbiAccountEntity();
        ubiAccount.setUbiProfileId("ubiProfileId");

        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");

        LocalDateTime expiresAt = LocalDateTime.now();

        ItemResaleLockEntity entity = new ItemResaleLockEntity();
        entity.setUbiAccount(ubiAccount);
        entity.setItem(item);
        entity.setExpiresAt(expiresAt);

        ItemResaleLock expected = new ItemResaleLock("ubiProfileId", "itemId", expiresAt);

        ItemResaleLock actual = entity.toItemResaleLock();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_properly_map_with_all_fields() {
        UbiAccountEntity ubiAccount = new UbiAccountEntity();
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