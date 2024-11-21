package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeByFiltersManagerEntityTest {
    @Test
    public void toTradeByFiltersManager_should_properly_map_with_all_fields() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntity filter1 = new ItemFilterEntity();
        filter1.setName("filter1");
        ItemFilterEntity filter2 = new ItemFilterEntity();
        filter2.setName("filter2");

        TradeByFiltersManagerEntity entity = new TradeByFiltersManagerEntity();
        entity.setUser(user);
        entity.setName("managerName");
        entity.setEnabled(true);
        entity.setTradeOperationType(TradeOperationType.BUY);
        entity.setAppliedFilters(List.of(filter1, filter2));
        entity.setMinBuySellProfit(100);
        entity.setMinProfitPercent(10);
        entity.setPriority(1);

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("managerName");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(filter1.toItemFilter(), filter2.toItemFilter()));
        expected.setMinBuySellProfit(100);
        expected.setMinProfitPercent(10);
        expected.setPriority(1);

        TradeByFiltersManager actual = entity.toTradeByFiltersManager();

        assertEquals(expected, actual);
    }

    @Test
    public void toTradeByFiltersManager_should_handle_null_appliedFilters_and_alt_fields() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        TradeByFiltersManagerEntity entity = new TradeByFiltersManagerEntity();
        entity.setUser(user);
        entity.setName("managerName");
        entity.setEnabled(false);
        entity.setTradeOperationType(TradeOperationType.SELL);
        entity.setAppliedFilters(null);
        entity.setMinBuySellProfit(10_000);
        entity.setMinProfitPercent(100);
        entity.setPriority(2);

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("managerName");
        expected.setEnabled(false);
        expected.setTradeOperationType(TradeOperationType.SELL);
        expected.setAppliedFilters(null);
        expected.setMinBuySellProfit(10_000);
        expected.setMinProfitPercent(100);
        expected.setPriority(2);

        TradeByFiltersManager actual = entity.toTradeByFiltersManager();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_properly_map_with_all_fields() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntity filter1 = new ItemFilterEntity();
        filter1.setName("filter1");
        ItemFilterEntity filter2 = new ItemFilterEntity();
        filter2.setName("filter2");

        TradeByFiltersManager tradeManager = new TradeByFiltersManager();
        tradeManager.setName("managerName");
        tradeManager.setEnabled(true);
        tradeManager.setTradeOperationType(TradeOperationType.BUY);
        tradeManager.setAppliedFilters(List.of(filter1.toItemFilter(), filter2.toItemFilter()));
        tradeManager.setMinBuySellProfit(100);
        tradeManager.setMinProfitPercent(10);
        tradeManager.setPriority(1);

        TradeByFiltersManagerEntity expected = new TradeByFiltersManagerEntity();
        expected.setUser(user);
        expected.setName("managerName");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(filter1, filter2));
        expected.setMinBuySellProfit(100);
        expected.setMinProfitPercent(10);
        expected.setPriority(1);

        TradeByFiltersManagerEntity actual = new TradeByFiltersManagerEntity(user, tradeManager);

        entitiesAreEqual(expected, actual);
    }

    private void entitiesAreEqual(TradeByFiltersManagerEntity expected, TradeByFiltersManagerEntity actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getTradeOperationType(), actual.getTradeOperationType());
        assertEquals(expected.getAppliedFilters(), actual.getAppliedFilters());
        assertEquals(expected.getMinBuySellProfit(), actual.getMinBuySellProfit());
        assertEquals(expected.getMinProfitPercent(), actual.getMinProfitPercent());
        assertEquals(expected.getPriority(), actual.getPriority());
    }
}