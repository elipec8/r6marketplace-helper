package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemResaleLockEntityTest {

    @Test
    public void getItemId_should_return_item_itemId(){
        ItemResaleLockEntity itemResaleLockEntity = new ItemResaleLockEntity();
        ItemIdEntity itemIdEntity = new ItemIdEntity();
        itemIdEntity.setItemId("itemId");
        itemResaleLockEntity.setItem(itemIdEntity);
        assertEquals("itemId", itemResaleLockEntity.getItemId_());
    }

    @Test
    public void isEqual_should_return_true_for_same(){
        ItemResaleLockEntity itemResaleLockEntity = new ItemResaleLockEntity();
        assertTrue(itemResaleLockEntity.isEqual(itemResaleLockEntity));
    }

    @Test
    public void isEqual_should_return_true_for_same_ids(){
        ItemResaleLockEntity itemResaleLockEntity = new ItemResaleLockEntity();
        ItemIdEntity itemIdEntity = new ItemIdEntity();
        itemIdEntity.setItemId("itemId");
        itemResaleLockEntity.setItem(itemIdEntity);
        itemResaleLockEntity.setUbiAccount(new UbiAccountStatsEntity("ubiAccount"));
        ItemResaleLockEntity itemResaleLockEntity1 = new ItemResaleLockEntity();
        ItemIdEntity itemIdEntity1 = new ItemIdEntity();
        itemIdEntity1.setItemId("itemId");
        itemResaleLockEntity1.setItem(itemIdEntity1);
        itemResaleLockEntity1.setUbiAccount(new UbiAccountStatsEntity("ubiAccount"));

        assertTrue(itemResaleLockEntity.isEqual(itemResaleLockEntity1));
    }




}