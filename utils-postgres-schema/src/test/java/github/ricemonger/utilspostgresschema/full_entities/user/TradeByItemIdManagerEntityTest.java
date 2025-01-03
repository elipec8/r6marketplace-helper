package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeByItemIdManagerEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids(){
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity());
        manager1.getUser().setId(1L);
        manager1.setItem(new ItemEntity());
        manager1.getItem().setItemId("itemId");
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new UserEntity());
        manager2.getUser().setId(1L);
        manager2.setItem(new ItemEntity());
        manager2.getItem().setItemId("itemId");

        assertEquals(manager1.hashCode(), manager2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same_object() {
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        assertEquals(manager, manager);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity());
        manager1.getUser().setId(1L);
        manager1.setItem(new ItemEntity());
        manager1.getItem().setItemId("itemId");
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new UserEntity());
        manager2.getUser().setId(1L);
        manager2.setItem(new ItemEntity());
        manager2.getItem().setItemId("itemId");

        assertEquals(manager1, manager2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        assertNotEquals(null, manager);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity());
        manager1.getUser().setId(1L);
        manager1.setItem(new ItemEntity());
        manager1.getItem().setItemId("itemId");

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new UserEntity());
        manager2.getUser().setId(1L);
        manager2.setItem(new ItemEntity());
        manager2.getItem().setItemId("itemId");

        manager1.getUser().setId(2L);
        assertNotEquals(manager1, manager2);
        manager1.getUser().setId(1L);
        manager1.getItem().setItemId("itemId2");
        assertNotEquals(manager1, manager2);
    }

    @Test
    public void getItemId_should_return_item_id() {
        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        manager.setItem(item);
        assertEquals("itemId", manager.getItemId_());
    }

    @Test
    public void isFullyEqual_should_return_true_if_same_object() {
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        assertTrue(manager.isFullyEqual(manager));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_equal_() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity());
        manager1.getUser().setId(1L);
        manager1.setItem(new ItemEntity());
        manager1.getItem().setItemId("itemId");
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new UserEntity());
        manager2.getUser().setId(1L);
        manager2.setItem(new ItemEntity());
        manager2.getItem().setItemId("itemId");
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setSellBoundaryPrice(100);
        manager2.setBuyBoundaryPrice(200);
        manager2.setPriorityMultiplier(2);

        assertTrue(manager1.isFullyEqual(manager2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_false_if_not_equal_() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity());
        manager1.getUser().setId(1L);
        manager1.setItem(new ItemEntity());
        manager1.getItem().setItemId("itemId");
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new UserEntity());
        manager2.getUser().setId(1L);
        manager2.setItem(new ItemEntity());
        manager2.getItem().setItemId("itemId");
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setSellBoundaryPrice(100);
        manager2.setBuyBoundaryPrice(200);
        manager2.setPriorityMultiplier(2);

        manager1.getUser().setId(2L);
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.getUser().setId(1L);
        manager1.getItem().setItemId("itemId2");
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.getItem().setItemId("itemId");
        manager1.setEnabled(false);
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.SELL);
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(101);
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(201);
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(3);
        assertFalse(manager1.isFullyEqual(manager2));
    }
}