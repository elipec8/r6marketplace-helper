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
        entity.setMinDifferenceFromMedianPrice(100);
        entity.setMinDifferenceFromMedianPricePercent(10);
        entity.setPriorityMultiplier(1);

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("managerName");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(filter1.toItemFilter(), filter2.toItemFilter()));
        expected.setMinDifferenceFromMedianPrice(100);
        expected.setMinDifferenceFromMedianPricePercent(10);
        expected.setPriorityMultiplier(1);

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
        entity.setMinDifferenceFromMedianPrice(10_000);
        entity.setMinDifferenceFromMedianPricePercent(100);
        entity.setPriorityMultiplier(2);

        TradeByFiltersManager expected = new TradeByFiltersManager();
        expected.setName("managerName");
        expected.setEnabled(false);
        expected.setTradeOperationType(TradeOperationType.SELL);
        expected.setAppliedFilters(null);
        expected.setMinDifferenceFromMedianPrice(10_000);
        expected.setMinDifferenceFromMedianPricePercent(100);
        expected.setPriorityMultiplier(2);

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
        tradeManager.setMinDifferenceFromMedianPrice(100);
        tradeManager.setMinDifferenceFromMedianPricePercent(10);
        tradeManager.setPriorityMultiplier(1);

        TradeByFiltersManagerEntity expected = new TradeByFiltersManagerEntity();
        expected.setUser(user);
        expected.setName("managerName");
        expected.setEnabled(true);
        expected.setTradeOperationType(TradeOperationType.BUY);
        expected.setAppliedFilters(List.of(filter1, filter2));
        expected.setMinDifferenceFromMedianPrice(100);
        expected.setMinDifferenceFromMedianPricePercent(10);
        expected.setPriorityMultiplier(1);

        TradeByFiltersManagerEntity actual = new TradeByFiltersManagerEntity(user, tradeManager);

        entitiesAreEqual(expected, actual);
    }

    private void entitiesAreEqual(TradeByFiltersManagerEntity expected, TradeByFiltersManagerEntity actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getTradeOperationType(), actual.getTradeOperationType());
        assertEquals(expected.getAppliedFilters().stream().map(ItemFilterEntity::toItemFilter).toList(), actual.getAppliedFilters().stream().map(ItemFilterEntity::toItemFilter).toList());
        assertEquals(expected.getMinDifferenceFromMedianPrice(), actual.getMinDifferenceFromMedianPrice());
        assertEquals(expected.getMinDifferenceFromMedianPricePercent(), actual.getMinDifferenceFromMedianPricePercent());
        assertEquals(expected.getPriorityMultiplier(), actual.getPriorityMultiplier());
    }
}