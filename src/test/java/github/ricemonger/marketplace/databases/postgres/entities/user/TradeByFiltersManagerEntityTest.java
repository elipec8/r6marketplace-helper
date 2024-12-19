package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TradeByFiltersManagerEntityTest {
    @Test
    public void getUserId_should_return_user_id() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        TradeByFiltersManagerEntity manager = new TradeByFiltersManagerEntity();
        manager.setUser(user);
        assertEquals(1L, manager.getUserId());
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_equal_except_user() {
        TradeByFiltersManagerEntity manager1 = new TradeByFiltersManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager1.setName("managerName");
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setAppliedFilters(List.of(new ItemFilterEntity()));
        manager1.setMinDifferenceFromMedianPrice(10);
        manager1.setMinDifferenceFromMedianPricePercent(5);
        manager1.setPriorityMultiplier(2);

        TradeByFiltersManagerEntity manager2 = new TradeByFiltersManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager2.setName("managerName");
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setAppliedFilters(List.of(new ItemFilterEntity()));
        manager2.setMinDifferenceFromMedianPrice(10);
        manager2.setMinDifferenceFromMedianPricePercent(5);
        manager2.setPriorityMultiplier(2);

        assertTrue(manager1.isFullyEqualExceptUser(manager2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_false_if_not_equal_except_user() {
        TradeByFiltersManagerEntity manager1 = new TradeByFiltersManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager1.setName("managerName1");
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setAppliedFilters(List.of(new ItemFilterEntity()));
        manager1.setMinDifferenceFromMedianPrice(10);
        manager1.setMinDifferenceFromMedianPricePercent(5);
        manager1.setPriorityMultiplier(2);

        TradeByFiltersManagerEntity manager2 = new TradeByFiltersManagerEntity();
        manager1.setUser(new UserEntity(1L));
        manager2.setName("managerName1");
        manager2.setEnabled(true);
        manager2.setTradeOperationType(TradeOperationType.BUY);
        manager2.setAppliedFilters(List.of(new ItemFilterEntity()));
        manager2.setMinDifferenceFromMedianPrice(10);
        manager2.setMinDifferenceFromMedianPricePercent(5);
        manager2.setPriorityMultiplier(2);

        manager1.setUser(new UserEntity(2L));
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setUser(new UserEntity(1L));
        manager1.setName("managerName2");
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setName("managerName1");
        manager1.setEnabled(false);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setEnabled(true);
        manager1.setTradeOperationType(TradeOperationType.SELL);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setTradeOperationType(TradeOperationType.BUY);
        manager1.setAppliedFilters(List.of());
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setAppliedFilters(null);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setAppliedFilters(List.of(new ItemFilterEntity()));
        manager1.setMinDifferenceFromMedianPrice(20);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setMinDifferenceFromMedianPrice(10);
        manager1.setMinDifferenceFromMedianPricePercent(10);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
        manager1.setMinDifferenceFromMedianPricePercent(5);
        manager1.setPriorityMultiplier(3);
        assertFalse(manager1.isFullyEqualExceptUser(manager2));
    }
}