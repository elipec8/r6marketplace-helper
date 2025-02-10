package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.DTOs.PersonalItem;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalBuyTrade;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalSellTrade;
import github.ricemonger.trades_manager.services.PotentialTradeStatsService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.common.PrioritizedPotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utils.services.calculators.ItemTradePriorityByExpressionCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PotentialTradeFactoryTest {
    @SpyBean
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private ItemTradePriorityByExpressionCalculator itemTradePriorityByExpressionCalculator;
    @MockBean
    private PotentialTradeStatsService potentialTradeStatsService;

    @Test
    public void getResultingPersonalSellTrades_should_return_expected_potential_Sell_trades_more_trades_limit_left_choose_with_highest_priority_for_each_item() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);
        List<ItemResaleLock> resaleLocks = List.of();

        int soldIn24h = 5;
        int sellSlots = 5;
        int sellLimit = 6;

        List<PotentialPersonalSellTrade> sellTrades = List.of(
                new PotentialPersonalSellTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L)),
                new PotentialPersonalSellTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 2L)),
                new PotentialPersonalSellTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(4, 5, 7L)),
                new PotentialPersonalSellTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L)),
                new PotentialPersonalSellTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(7, 8, 4L))
        );

        doReturn(sellTrades).when(potentialTradeFactory).getFilteredPotentialSellTradesForUser(eq("expression"), same(personalItems), same(resaleLocks));

        List<PotentialPersonalSellTrade> result = potentialTradeFactory.getResultingPersonalSellTrades("expression", personalItems, resaleLocks, soldIn24h, sellSlots, sellLimit);
        assertEquals(2, result.size());
        assertTrue(result.contains(new PotentialPersonalSellTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L))));
        assertTrue(result.contains(new PotentialPersonalSellTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L))));
    }

    @Test
    public void getResultingPersonalSellTrades_should_return_expected_potential_Sell_trades_more_trades_then_slots_choose_with_highest_priority_for_each_item() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);
        List<ItemResaleLock> resaleLocks = List.of();

        int soldIn24h = 0;
        int sellSlots = 2;
        int sellLimit = 10;

        List<PotentialPersonalSellTrade> sellTrades = List.of(
                new PotentialPersonalSellTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L)),
                new PotentialPersonalSellTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(4, 5, 7L)),
                new PotentialPersonalSellTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L)),
                new PotentialPersonalSellTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(7, 8, 4L))
        );

        doReturn(sellTrades).when(potentialTradeFactory).getFilteredPotentialSellTradesForUser(eq("expression"), same(personalItems), same(resaleLocks));

        List<PotentialPersonalSellTrade> result = potentialTradeFactory.getResultingPersonalSellTrades("expression", personalItems, resaleLocks, soldIn24h,
                sellSlots, sellLimit);
        assertEquals(2, result.size());
        assertTrue(result.contains(new PotentialPersonalSellTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L))));
        assertTrue(result.contains(new PotentialPersonalSellTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(7, 8, 4L))));
    }

    @Test
    public void getResultingPersonalSellTrades_should_return_expected_potential_Sell_trades_more_slots_then_trades() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);
        List<ItemResaleLock> resaleLocks = List.of();

        int soldIn24h = 0;
        int sellSlots = 2;
        int sellLimit = 10;

        List<PotentialPersonalSellTrade> sellTrades = List.of(
                new PotentialPersonalSellTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L))
        );

        doReturn(sellTrades).when(potentialTradeFactory).getFilteredPotentialSellTradesForUser(eq("expression"), same(personalItems), same(resaleLocks));

        List<PotentialPersonalSellTrade> result = potentialTradeFactory.getResultingPersonalSellTrades("expression", personalItems, resaleLocks, soldIn24h, sellSlots, sellLimit);
        assertEquals(1, result.size());
        assertEquals(new PotentialPersonalSellTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L)), result.get(0));
    }

    @Test
    public void getFilteredPotentialSellTradesForUser_should_filter_personal_items_and_create_potential_sell_trades() {
        PersonalItem notOwnedPersonalItem = new PersonalItem();
        notOwnedPersonalItem.setItem(new Item("1"));
        notOwnedPersonalItem.setIsOwned(false);
        notOwnedPersonalItem.setTradeOperationType(TradeOperationType.SELL);
        PotentialTradeStats potentialTradeStats1 = new PotentialTradeStats(1, 2);
        when(potentialTradeStatsService.getPotentialSellTradesStatsOfItem(notOwnedPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats1));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", notOwnedPersonalItem.getItem(), potentialTradeStats1.getPrice(), potentialTradeStats1.getTime())).thenReturn(3L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats1 = new PrioritizedPotentialTradeStats(1, 2, 3L);

        PersonalItem wrongOperationTypePersonalItem = new PersonalItem();
        wrongOperationTypePersonalItem.setItem(new Item("2"));
        wrongOperationTypePersonalItem.setIsOwned(true);
        wrongOperationTypePersonalItem.setTradeOperationType(TradeOperationType.BUY);
        PotentialTradeStats potentialTradeStats2 = new PotentialTradeStats(4, 5);
        when(potentialTradeStatsService.getPotentialSellTradesStatsOfItem(wrongOperationTypePersonalItem.getItem())).thenReturn(List.of(potentialTradeStats2));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", wrongOperationTypePersonalItem.getItem(), potentialTradeStats2.getPrice(), potentialTradeStats2.getTime())).thenReturn(6L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats2 = new PrioritizedPotentialTradeStats(4, 5, 6L);

        PersonalItem resaleLockedPersonalItem = new PersonalItem();
        resaleLockedPersonalItem.setItem(new Item("6"));
        resaleLockedPersonalItem.setIsOwned(true);
        resaleLockedPersonalItem.setTradeOperationType(TradeOperationType.SELL);
        PotentialTradeStats potentialTradeStats12 = new PotentialTradeStats(4, 5);
        when(potentialTradeStatsService.getPotentialSellTradesStatsOfItem(resaleLockedPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats12));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", resaleLockedPersonalItem.getItem(), potentialTradeStats12.getPrice(), potentialTradeStats12.getTime())).thenReturn(9L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats12 = new PrioritizedPotentialTradeStats(4, 5, 9L);

        PersonalItem alreadyExistingTradeWithWrongCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithWrongCategoryPersonalItem.setItem(new Item("3"));
        alreadyExistingTradeWithWrongCategoryPersonalItem.setIsOwned(true);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setCategory(TradeCategory.Buy);
        ubiUbiTrade1.setProposedPaymentPrice(30);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setTradeAlreadyExists(true);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setExistingTrade(ubiUbiTrade1);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setSellBoundaryPrice(25);
        PotentialTradeStats potentialTradeStats3 = new PotentialTradeStats(25, 8);
        PotentialTradeStats potentialTradeStats4 = new PotentialTradeStats(26, 11);
        PotentialTradeStats potentialTradeStatsBoundaryPrice7 = new PotentialTradeStats(24, 20);
        PotentialTradeStats ubiTradeStatsWrongCategory = new PotentialTradeStats(30, 20);
        when(potentialTradeStatsService.getPotentialSellTradesStatsOfItem(alreadyExistingTradeWithWrongCategoryPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats3, potentialTradeStats4, potentialTradeStatsBoundaryPrice7));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), potentialTradeStats3.getPrice(), potentialTradeStats3.getTime())).thenReturn(9L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), potentialTradeStats4.getPrice(), potentialTradeStats4.getTime())).thenReturn(12L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), potentialTradeStatsBoundaryPrice7.getPrice(), potentialTradeStatsBoundaryPrice7.getTime())).thenReturn(21L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), ubiTradeStatsWrongCategory.getPrice(), ubiTradeStatsWrongCategory.getTime())).thenReturn(20L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats3 = new PrioritizedPotentialTradeStats(25, 8, 9L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats4 = new PrioritizedPotentialTradeStats(26, 11, 12L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBoundaryPrice7 = new PrioritizedPotentialTradeStats(24, 20, 21L);
        PrioritizedPotentialTradeStats prioritizedUbiTradeStatsWrongCategory = new PrioritizedPotentialTradeStats(30, 20, 20L);

        PersonalItem alreadyExistingTradeWithProperCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithProperCategoryPersonalItem.setItem(new Item("4"));
        alreadyExistingTradeWithProperCategoryPersonalItem.setIsOwned(true);
        alreadyExistingTradeWithProperCategoryPersonalItem.setTradeOperationType(TradeOperationType.SELL);
        UbiTrade ubiUbiTrade2 = new UbiTrade();
        ubiUbiTrade2.setCategory(TradeCategory.Sell);
        ubiUbiTrade2.setProposedPaymentPrice(33);
        alreadyExistingTradeWithProperCategoryPersonalItem.setTradeAlreadyExists(true);
        alreadyExistingTradeWithProperCategoryPersonalItem.setExistingTrade(ubiUbiTrade2);
        alreadyExistingTradeWithProperCategoryPersonalItem.setSellBoundaryPrice(44);
        alreadyExistingTradeWithProperCategoryPersonalItem.getItem().setMonthMedianPrice(30);
        alreadyExistingTradeWithProperCategoryPersonalItem.setMinMedianPriceDifference(14);
        alreadyExistingTradeWithProperCategoryPersonalItem.setMinMedianPriceDifferencePercent(50);
        PotentialTradeStats potentialTradeStats5 = new PotentialTradeStats(45, 14);
        PotentialTradeStats potentialTradeStats6 = new PotentialTradeStats(46, 17);
        PotentialTradeStats potentialTradeStatsMedianPriceDiffPercent9 = new PotentialTradeStats(44, 26);
        PotentialTradeStats ubiTradeStatsProperCategory = new PotentialTradeStats(33, 20);
        when(potentialTradeStatsService.getPotentialSellTradesStatsOfItem(alreadyExistingTradeWithProperCategoryPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats5, potentialTradeStats6, potentialTradeStatsMedianPriceDiffPercent9));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStats5.getPrice(), potentialTradeStats5.getTime())).thenReturn(15L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStats6.getPrice(), potentialTradeStats6.getTime())).thenReturn(18L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStatsMedianPriceDiffPercent9.getPrice(), potentialTradeStatsMedianPriceDiffPercent9.getTime())).thenReturn(27L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), ubiTradeStatsProperCategory.getPrice(), ubiTradeStatsProperCategory.getTime())).thenReturn(20L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats5 = new PrioritizedPotentialTradeStats(45, 14, 15L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats6 = new PrioritizedPotentialTradeStats(46, 17, 18L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsMedianPriceDiffPercent9 = new PrioritizedPotentialTradeStats(44, 26, 27L);
        PrioritizedPotentialTradeStats prioritizedUbiTradeStatsProperCategory = new PrioritizedPotentialTradeStats(33, 20, 20L);

        PersonalItem noAlreadyExistingTradePersonalItem = new PersonalItem();
        noAlreadyExistingTradePersonalItem.setItem(new Item("5"));
        noAlreadyExistingTradePersonalItem.setIsOwned(true);
        noAlreadyExistingTradePersonalItem.setTradeAlreadyExists(false);
        noAlreadyExistingTradePersonalItem.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        noAlreadyExistingTradePersonalItem.setSellBoundaryPrice(200);
        noAlreadyExistingTradePersonalItem.getItem().setMonthMedianPrice(200);
        noAlreadyExistingTradePersonalItem.setMinMedianPriceDifference(50);
        noAlreadyExistingTradePersonalItem.setMinMedianPriceDifferencePercent(10);
        PotentialTradeStats potentialTradeStatsMedianPriceDiff8 = new PotentialTradeStats(249, 23);
        PotentialTradeStats potentialTradeStats10 = new PotentialTradeStats(250, 29);
        PotentialTradeStats potentialTradeStats11 = new PotentialTradeStats(251, 32);
        when(potentialTradeStatsService.getPotentialSellTradesStatsOfItem(noAlreadyExistingTradePersonalItem.getItem())).thenReturn(List.of(potentialTradeStatsMedianPriceDiff8, potentialTradeStats10, potentialTradeStats11));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", noAlreadyExistingTradePersonalItem.getItem(), potentialTradeStatsMedianPriceDiff8.getPrice(), potentialTradeStatsMedianPriceDiff8.getTime())).thenReturn(24L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", noAlreadyExistingTradePersonalItem.getItem(), potentialTradeStats10.getPrice(), potentialTradeStats10.getTime())).thenReturn(30L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", noAlreadyExistingTradePersonalItem.getItem(), potentialTradeStats11.getPrice(), potentialTradeStats11.getTime())).thenReturn(33L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsMedianPriceDiff8 = new PrioritizedPotentialTradeStats(249, 23, 24L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats10 = new PrioritizedPotentialTradeStats(250, 29, 30L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats11 = new PrioritizedPotentialTradeStats(251, 32, 33L);

        List<ItemResaleLock> resaleLocks = List.of(new ItemResaleLock("6", LocalDateTime.of(2021, 1, 1, 0, 0)));

        List<PotentialPersonalSellTrade> result = potentialTradeFactory.getFilteredPotentialSellTradesForUser("expression", List.of(notOwnedPersonalItem, wrongOperationTypePersonalItem, resaleLockedPersonalItem, alreadyExistingTradeWithWrongCategoryPersonalItem, alreadyExistingTradeWithProperCategoryPersonalItem, noAlreadyExistingTradePersonalItem), resaleLocks);

        verify(potentialTradeStatsService, times(0)).getPotentialSellTradesStatsOfItem(notOwnedPersonalItem.getItem());
        verify(potentialTradeStatsService, times(0)).getPotentialSellTradesStatsOfItem(wrongOperationTypePersonalItem.getItem());
        verify(potentialTradeStatsService, times(0)).getPotentialSellTradesStatsOfItem(resaleLockedPersonalItem.getItem());

        assertTrue(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithWrongCategoryPersonalItem,
                prioritizedPotentialTradeStats3)));
        assertTrue(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, prioritizedPotentialTradeStats4)));
        assertFalse(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, prioritizedPotentialTradeStatsBoundaryPrice7)));
        assertFalse(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, prioritizedUbiTradeStatsWrongCategory)));

        assertTrue(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedPotentialTradeStats5)));
        assertTrue(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedPotentialTradeStats6)));
        assertFalse(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedPotentialTradeStatsMedianPriceDiffPercent9)));
        assertTrue(result.contains(new PotentialPersonalSellTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedUbiTradeStatsProperCategory)));

        assertFalse(result.contains(new PotentialPersonalSellTrade(noAlreadyExistingTradePersonalItem, prioritizedPotentialTradeStatsMedianPriceDiff8)));
        assertTrue(result.contains(new PotentialPersonalSellTrade(noAlreadyExistingTradePersonalItem, prioritizedPotentialTradeStats10)));
        assertTrue(result.contains(new PotentialPersonalSellTrade(noAlreadyExistingTradePersonalItem, prioritizedPotentialTradeStats11)));

        assertEquals(7, result.size());
    }

    @Test
    public void getResultingPersonalBuyTrades_should_return_expected_potential_buy_trades_low_credit_amount_choose_with_highest_priority_for_each_item() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        int creditAmount = 10;
        int boughtIn24h = 5;
        int buySlots = 5;
        int buyLimit = 10;

        List<PotentialPersonalBuyTrade> buyTrades = List.of(
                new PotentialPersonalBuyTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(5, 5, 1L)),
                new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 2L)),
                new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(5, 2, 3L)),
                new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(1, 5, 7L)),
                new PotentialPersonalBuyTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(1, 8, 4L)),
                new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(10, 6, 11L))
        );

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);

        doReturn(buyTrades).when(potentialTradeFactory).getFilteredPotentialBuyTradesForUser(eq("expression"), same(personalItems));

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getResultingPersonalBuyTrades("expression", personalItems, creditAmount, boughtIn24h, buySlots, buyLimit);
        assertEquals(3, result.size());
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(10, 6, 11L))));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(5, 2, 3L))));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(5, 5, 1L))));
    }

    @Test
    public void getResultingPersonalBuyTrades_should_return_expected_potential_buy_trades_more_trades_limit_left_choose_with_highest_priority_for_each_item() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);

        int creditAmount = 1000;
        int boughtIn24h = 5;
        int buySlots = 5;
        int buyLimit = 6;

        List<PotentialPersonalBuyTrade> buyTrades = List.of(
                new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L)),
                new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 2L)),
                new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(4, 5, 7L)),
                new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L)),
                new PotentialPersonalBuyTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(7, 8, 4L))
        );

        doReturn(buyTrades).when(potentialTradeFactory).getFilteredPotentialBuyTradesForUser(eq("expression"), same(personalItems));

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getResultingPersonalBuyTrades("expression", personalItems, creditAmount, boughtIn24h, buySlots, buyLimit);
        assertEquals(2, result.size());
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L))));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L))));
    }

    @Test
    public void getResultingPersonalBuyTrades_should_return_expected_potential_buy_trades_more_trades_then_slots_choose_with_highest_priority_for_each_item() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);

        int creditAmount = 1000;
        int boughtIn24h = 0;
        int buySlots = 2;
        int buyLimit = 10;

        List<PotentialPersonalBuyTrade> buyTrades = List.of(
                new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L)),
                new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(4, 5, 7L)),
                new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L)),
                new PotentialPersonalBuyTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(7, 8, 4L))
        );

        doReturn(buyTrades).when(potentialTradeFactory).getFilteredPotentialBuyTradesForUser(eq("expression"), same(personalItems));

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getResultingPersonalBuyTrades("expression", personalItems, creditAmount, boughtIn24h, buySlots, buyLimit);
        assertEquals(2, result.size());
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemLowerPriority2, new PrioritizedPotentialTradeStats(5, 6, 11L))));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(personalItemHigherPriority3, new PrioritizedPotentialTradeStats(7, 8, 4L))));
    }

    @Test
    public void getResultingPersonalBuyTrades_should_return_expected_potential_buy_trades_more_slots_then_trades() {
        PersonalItem personalItemExistingTrade1 = new PersonalItem();
        personalItemExistingTrade1.setItem(new Item("1"));
        personalItemExistingTrade1.setPriorityMultiplier(1);
        personalItemExistingTrade1.setTradeAlreadyExists(true);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setProposedPaymentPrice(10);
        personalItemExistingTrade1.setExistingTrade(ubiUbiTrade1);

        PersonalItem personalItemLowerPriority2 = new PersonalItem();
        personalItemLowerPriority2.setItem(new Item("2"));
        personalItemLowerPriority2.setPriorityMultiplier(1);
        personalItemLowerPriority2.setTradeAlreadyExists(false);

        PersonalItem personalItemHigherPriority3 = new PersonalItem();
        personalItemHigherPriority3.setItem(new Item("3"));
        personalItemHigherPriority3.setPriorityMultiplier(2);
        personalItemHigherPriority3.setTradeAlreadyExists(false);

        List<PersonalItem> personalItems = List.of(personalItemExistingTrade1, personalItemLowerPriority2, personalItemHigherPriority3);

        int creditAmount = 1000;
        int boughtIn24h = 0;
        int buySlots = 2;
        int buyLimit = 10;

        List<PotentialPersonalBuyTrade> buyTrades = List.of(
                new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L))
        );

        doReturn(buyTrades).when(potentialTradeFactory).getFilteredPotentialBuyTradesForUser(eq("expression"), same(personalItems));

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getResultingPersonalBuyTrades("expression", personalItems, creditAmount, boughtIn24h, buySlots, buyLimit);
        assertEquals(1, result.size());
        assertEquals(new PotentialPersonalBuyTrade(personalItemExistingTrade1, new PrioritizedPotentialTradeStats(1, 2, 3L)), result.get(0));
    }

    @Test
    public void getFilteredPotentialBuyTradesForUser_should_filter_personal_items_and_create_potential_buy_trades() {
        PersonalItem ownedPersonalItem = new PersonalItem();
        ownedPersonalItem.setItem(new Item("1"));
        ownedPersonalItem.setIsOwned(true);
        ownedPersonalItem.setTradeOperationType(TradeOperationType.BUY);
        PotentialTradeStats potentialTradeStats1 = new PotentialTradeStats(1, 2);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(ownedPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats1));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", ownedPersonalItem.getItem(), potentialTradeStats1.getPrice(), potentialTradeStats1.getTime())).thenReturn(3L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats1 = new PrioritizedPotentialTradeStats(1, 2, 3L);

        PersonalItem wrongOperationTypePersonalItem = new PersonalItem();
        wrongOperationTypePersonalItem.setItem(new Item("2"));
        wrongOperationTypePersonalItem.setIsOwned(false);
        wrongOperationTypePersonalItem.setTradeOperationType(TradeOperationType.SELL);
        PotentialTradeStats potentialTradeStats2 = new PotentialTradeStats(4, 5);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(wrongOperationTypePersonalItem.getItem())).thenReturn(List.of(potentialTradeStats2));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", wrongOperationTypePersonalItem.getItem(), potentialTradeStats2.getPrice(), potentialTradeStats2.getTime())).thenReturn(6L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats2 = new PrioritizedPotentialTradeStats(4, 5, 6L);

        PersonalItem alreadyExistingTradeWithWrongCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithWrongCategoryPersonalItem.setItem(new Item("3"));
        alreadyExistingTradeWithWrongCategoryPersonalItem.setIsOwned(false);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        UbiTrade ubiUbiTrade1 = new UbiTrade();
        ubiUbiTrade1.setCategory(TradeCategory.Sell);
        ubiUbiTrade1.setProposedPaymentPrice(30);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setTradeAlreadyExists(true);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setExistingTrade(ubiUbiTrade1);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setBuyBoundaryPrice(25);
        PotentialTradeStats potentialTradeStats3 = new PotentialTradeStats(25, 8);
        PotentialTradeStats potentialTradeStats4 = new PotentialTradeStats(24, 11);
        PotentialTradeStats potentialTradeStatsBoundaryPrice7 = new PotentialTradeStats(26, 20);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(alreadyExistingTradeWithWrongCategoryPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats3, potentialTradeStats4, potentialTradeStatsBoundaryPrice7));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), potentialTradeStats3.getPrice(), potentialTradeStats3.getTime())).thenReturn(9L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), potentialTradeStats4.getPrice(), potentialTradeStats4.getTime())).thenReturn(12L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithWrongCategoryPersonalItem.getItem(), potentialTradeStatsBoundaryPrice7.getPrice(), potentialTradeStatsBoundaryPrice7.getTime())).thenReturn(21L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats3 = new PrioritizedPotentialTradeStats(25, 8, 9L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats4 = new PrioritizedPotentialTradeStats(24, 11, 12L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsBoundaryPrice7 = new PrioritizedPotentialTradeStats(26, 20, 21L);

        PersonalItem alreadyExistingTradeWithProperCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithProperCategoryPersonalItem.setItem(new Item("4"));
        alreadyExistingTradeWithProperCategoryPersonalItem.setIsOwned(false);
        alreadyExistingTradeWithProperCategoryPersonalItem.setTradeOperationType(TradeOperationType.BUY);
        UbiTrade ubiUbiTrade2 = new UbiTrade();
        ubiUbiTrade2.setCategory(TradeCategory.Buy);
        ubiUbiTrade2.setProposedPaymentPrice(30);
        alreadyExistingTradeWithProperCategoryPersonalItem.setTradeAlreadyExists(true);
        alreadyExistingTradeWithProperCategoryPersonalItem.setExistingTrade(ubiUbiTrade2);
        alreadyExistingTradeWithProperCategoryPersonalItem.setBuyBoundaryPrice(25);
        alreadyExistingTradeWithProperCategoryPersonalItem.getItem().setMonthMedianPrice(30);
        alreadyExistingTradeWithProperCategoryPersonalItem.setMinMedianPriceDifference(10);
        alreadyExistingTradeWithProperCategoryPersonalItem.setMinMedianPriceDifferencePercent(46);
        PotentialTradeStats potentialTradeStats5 = new PotentialTradeStats(13, 14);
        PotentialTradeStats potentialTradeStats6 = new PotentialTradeStats(16, 17);
        PotentialTradeStats potentialTradeStatsNullPrice = new PotentialTradeStats(null, 17);
        PotentialTradeStats potentialTradeStatsNullTime = new PotentialTradeStats(16, null);
        PotentialTradeStats potentialTradeStatsNullPriority = new PotentialTradeStats(16, 17);
        PotentialTradeStats potentialTradeStatsMedianPriceDiffPercent9 = new PotentialTradeStats(17, 26);
        PotentialTradeStats ubiTradeStatsProperCategory = new PotentialTradeStats(30, 20);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(alreadyExistingTradeWithProperCategoryPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats5, potentialTradeStats6, potentialTradeStatsMedianPriceDiffPercent9, potentialTradeStatsNullPrice, potentialTradeStatsNullTime, potentialTradeStatsNullPriority));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStats5.getPrice(), potentialTradeStats5.getTime())).thenReturn(15L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStats6.getPrice(), potentialTradeStats6.getTime())).thenReturn(18L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStatsNullPrice.getPrice(), potentialTradeStatsNullPrice.getTime())).thenReturn(17L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStatsNullTime.getPrice(), potentialTradeStatsNullTime.getTime())).thenReturn(16L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStatsNullPriority.getPrice(), potentialTradeStatsNullPriority.getTime())).thenReturn(17L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), potentialTradeStatsMedianPriceDiffPercent9.getPrice(), potentialTradeStatsMedianPriceDiffPercent9.getTime())).thenReturn(27L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", alreadyExistingTradeWithProperCategoryPersonalItem.getItem(), ubiTradeStatsProperCategory.getPrice(), ubiTradeStatsProperCategory.getTime())).thenReturn(20L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats5 = new PrioritizedPotentialTradeStats(13, 14, 15L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats6 = new PrioritizedPotentialTradeStats(16, 17, 18L);
        PrioritizedPotentialTradeStats priritizedPotentialTradeStatsNullPrice = new PrioritizedPotentialTradeStats(null, 17, 17L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsNullTime = new PrioritizedPotentialTradeStats(16, null, 16L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsNullPriority = new PrioritizedPotentialTradeStats(16, 17, 17L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsMedianPriceDiffPercent9 = new PrioritizedPotentialTradeStats(17, 26, 27L);
        PrioritizedPotentialTradeStats prioritizedUbiTradeStatsProperCategory = new PrioritizedPotentialTradeStats(30, 20, 20L);

        PersonalItem nullPriceExistingTradePersonalItem = new PersonalItem();
        nullPriceExistingTradePersonalItem.setItem(new Item("6"));
        nullPriceExistingTradePersonalItem.setIsOwned(false);
        nullPriceExistingTradePersonalItem.setTradeOperationType(TradeOperationType.BUY);
        UbiTrade ubiUbiTrade3 = new UbiTrade();
        ubiUbiTrade3.setCategory(TradeCategory.Buy);
        ubiUbiTrade3.setProposedPaymentPrice(null);
        nullPriceExistingTradePersonalItem.setTradeAlreadyExists(true);
        nullPriceExistingTradePersonalItem.setExistingTrade(ubiUbiTrade3);
        nullPriceExistingTradePersonalItem.setBuyBoundaryPrice(25);
        nullPriceExistingTradePersonalItem.getItem().setMonthMedianPrice(30);
        nullPriceExistingTradePersonalItem.setMinMedianPriceDifference(10);
        nullPriceExistingTradePersonalItem.setMinMedianPriceDifferencePercent(46);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(nullPriceExistingTradePersonalItem.getItem())).thenReturn(List.of());

        PersonalItem nullTimeExistingTradePersonalItem = new PersonalItem();
        nullTimeExistingTradePersonalItem.setItem(new Item("7"));
        nullTimeExistingTradePersonalItem.setIsOwned(false);
        nullTimeExistingTradePersonalItem.setTradeOperationType(TradeOperationType.BUY);
        UbiTrade ubiUbiTrade4 = new UbiTrade();
        ubiUbiTrade4.setCategory(TradeCategory.Buy);
        ubiUbiTrade4.setProposedPaymentPrice(10);
        nullTimeExistingTradePersonalItem.setTradeAlreadyExists(true);
        nullTimeExistingTradePersonalItem.setExistingTrade(ubiUbiTrade4);
        nullTimeExistingTradePersonalItem.setBuyBoundaryPrice(25);
        nullTimeExistingTradePersonalItem.getItem().setMonthMedianPrice(30);
        nullTimeExistingTradePersonalItem.setMinMedianPriceDifference(10);
        nullTimeExistingTradePersonalItem.setMinMedianPriceDifferencePercent(46);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(nullTimeExistingTradePersonalItem.getItem())).thenReturn(List.of());

        PersonalItem nullPriorityExistingTradePersonalItem = new PersonalItem();
        nullPriorityExistingTradePersonalItem.setItem(new Item("8"));
        nullPriorityExistingTradePersonalItem.setIsOwned(false);
        nullPriorityExistingTradePersonalItem.setTradeOperationType(TradeOperationType.BUY);
        UbiTrade ubiUbiTrade5 = new UbiTrade();
        ubiUbiTrade5.setCategory(TradeCategory.Buy);
        ubiUbiTrade5.setProposedPaymentPrice(10);
        nullPriorityExistingTradePersonalItem.setTradeAlreadyExists(true);
        nullPriorityExistingTradePersonalItem.setExistingTrade(ubiUbiTrade5);
        nullPriorityExistingTradePersonalItem.setBuyBoundaryPrice(25);
        nullPriorityExistingTradePersonalItem.getItem().setMonthMedianPrice(30);
        nullPriorityExistingTradePersonalItem.setMinMedianPriceDifference(10);
        nullPriorityExistingTradePersonalItem.setMinMedianPriceDifferencePercent(46);
        PotentialTradeStats ubiTradeStatsNullPriority = new PotentialTradeStats(10, 20);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(nullPriorityExistingTradePersonalItem.getItem())).thenReturn(List.of());


        PersonalItem noAlreadyExistingTradePersonalItem = new PersonalItem();
        noAlreadyExistingTradePersonalItem.setItem(new Item("5"));
        noAlreadyExistingTradePersonalItem.setIsOwned(false);
        noAlreadyExistingTradePersonalItem.setTradeAlreadyExists(false);
        noAlreadyExistingTradePersonalItem.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        noAlreadyExistingTradePersonalItem.setBuyBoundaryPrice(300);
        noAlreadyExistingTradePersonalItem.getItem().setMonthMedianPrice(250);
        noAlreadyExistingTradePersonalItem.setMinMedianPriceDifference(50);
        noAlreadyExistingTradePersonalItem.setMinMedianPriceDifferencePercent(19);
        PotentialTradeStats potentialTradeStatsMedianPriceDiff8 = new PotentialTradeStats(201, 23);
        PotentialTradeStats potentialTradeStats10 = new PotentialTradeStats(200, 29);
        PotentialTradeStats potentialTradeStats11 = new PotentialTradeStats(199, 32);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(noAlreadyExistingTradePersonalItem.getItem())).thenReturn(List.of(potentialTradeStatsMedianPriceDiff8, potentialTradeStats10, potentialTradeStats11));

        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", noAlreadyExistingTradePersonalItem.getItem(), potentialTradeStatsMedianPriceDiff8.getPrice(), potentialTradeStatsMedianPriceDiff8.getTime())).thenReturn(24L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", noAlreadyExistingTradePersonalItem.getItem(), potentialTradeStats10.getPrice(), potentialTradeStats10.getTime())).thenReturn(30L);
        when(itemTradePriorityByExpressionCalculator.calculateTradePriority("expression", noAlreadyExistingTradePersonalItem.getItem(), potentialTradeStats11.getPrice(), potentialTradeStats11.getTime())).thenReturn(33L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStatsMedianPriceDiff8 = new PrioritizedPotentialTradeStats(201, 23, 24L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats10 = new PrioritizedPotentialTradeStats(200, 29, 30L);
        PrioritizedPotentialTradeStats prioritizedPotentialTradeStats11 = new PrioritizedPotentialTradeStats(199, 32, 33L);

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getFilteredPotentialBuyTradesForUser("expression", List.of(ownedPersonalItem, wrongOperationTypePersonalItem, alreadyExistingTradeWithWrongCategoryPersonalItem, alreadyExistingTradeWithProperCategoryPersonalItem, nullPriceExistingTradePersonalItem, nullTimeExistingTradePersonalItem, nullPriorityExistingTradePersonalItem, noAlreadyExistingTradePersonalItem));

        verify(potentialTradeStatsService, times(0)).getPotentialBuyTradesStatsOfItem(ownedPersonalItem.getItem());
        verify(potentialTradeStatsService, times(0)).getPotentialBuyTradesStatsOfItem(wrongOperationTypePersonalItem.getItem());

        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, prioritizedPotentialTradeStats3)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, prioritizedPotentialTradeStats4)));

        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedPotentialTradeStats5)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedPotentialTradeStats6)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, prioritizedUbiTradeStatsProperCategory)));

        assertTrue(result.contains(new PotentialPersonalBuyTrade(noAlreadyExistingTradePersonalItem, prioritizedPotentialTradeStats10)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(noAlreadyExistingTradePersonalItem, prioritizedPotentialTradeStats11)));

        assertEquals(7, result.size());
    }
}