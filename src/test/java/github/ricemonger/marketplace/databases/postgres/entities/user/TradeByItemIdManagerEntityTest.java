package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeByItemIdManagerEntityTest {
    @Test
    public void getUserId_should_return_user_id() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        manager.setUser(user);
        assertEquals(1L, manager.getUserId());
    }

    @Test
    public void getItemId_should_return_item_id() {
        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        manager.setItem(item);
        assertEquals("itemId", manager.getItemId());
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_same_object() {
        TradeByItemIdManagerEntity manager = new TradeByItemIdManagerEntity();
        assertTrue(manager.isFullyEqualExceptUser(manager));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_equal_except_user() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager1.setItem(new ItemEntity("itemId"));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager2.setItem(new ItemEntity("itemId"));
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setSellBoundaryPrice(100);
        manager2.setBuyBoundaryPrice(200);
        manager2.setPriorityMultiplier(2);

        assertTrue(manager1.isFullyEqualExceptUser(manager2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_false_if_not_equal_except_user() {
        TradeByItemIdManagerEntity manager1 = new TradeByItemIdManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager1.setItem(new ItemEntity("itemId"));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(2);

        TradeByItemIdManagerEntity manager2 = new TradeByItemIdManagerEntity();
        manager2.setUser(new UserEntity(1L));
        manager2.setItem(new ItemEntity("itemId"));
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setSellBoundaryPrice(100);
        manager2.setBuyBoundaryPrice(200);
        manager2.setPriorityMultiplier(2);

        manager1.setUser(new UserEntity(2L));
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setUser(new UserEntity(1L));
        manager1.getItem().setItemId("itemId2");
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.getItem().setItemId("itemId");
        manager1.setEnabled(false);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.SELL);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setSellBoundaryPrice(101);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setSellBoundaryPrice(100);
        manager1.setBuyBoundaryPrice(201);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setBuyBoundaryPrice(200);
        manager1.setPriorityMultiplier(3);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
    }
}