package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeByItemIdManagerEntityTest {
    @Test
    public void isEqual_should_return_true_if_same_object() {
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        assertTrue(manager.isEqual(manager));
    }

    @Test
    public void isEqual_should_return_true_if_equal_ids() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new ManageableUserEntity(1L));
        manager1.setItem(new ItemIdEntity("itemId"));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new ManageableUserEntity(1L));
        manager2.setItem(new ItemIdEntity("itemId"));

        assertTrue(manager1.isEqual(manager2));
    }

    @Test
    public void isEqual_should_return_false_if_null() {
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        assertFalse(manager.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_if_different_ids() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new ManageableUserEntity(1L));
        manager1.setItem(new ItemIdEntity("itemId"));

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new ManageableUserEntity(1L));
        manager2.setItem(new ItemIdEntity("itemId"));

        manager1.setUser(new ManageableUserEntity(2L));
        assertFalse(manager1.isEqual(manager2));
        manager1.setUser(new ManageableUserEntity(1L));
        manager1.setItem(new ItemIdEntity("itemId2"));
        assertFalse(manager1.isEqual(manager2));
    }

    @Test
    public void getUserId_should_return_user_idField() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        manager.setUser(user);
        assertEquals(1L, manager.getUserId_());
    }

    @Test
    public void getItemId_should_return_item_id() {
        ItemIdEntity item = new ItemIdEntity();
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
        manager1.setUser(new ManageableUserEntity(1L));
        manager1.setItem(new ItemIdEntity("itemId"));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new ManageableUserEntity(1L));
        manager2.setItem(new ItemIdEntity("itemId"));
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
        manager1.setUser(new ManageableUserEntity(1L));
        manager1.setItem(new ItemIdEntity("itemId"));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new ManageableUserEntity(1L));
        manager2.setItem(new ItemIdEntity("itemId"));
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setSellBoundaryPrice(100);
        manager2.setBuyBoundaryPrice(200);
        manager2.setPriorityMultiplier(2);

        manager1.setUser(new ManageableUserEntity(2L));
        assertFalse(manager1.isFullyEqual(manager2));
        manager1.setUser(new ManageableUserEntity(1L));
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