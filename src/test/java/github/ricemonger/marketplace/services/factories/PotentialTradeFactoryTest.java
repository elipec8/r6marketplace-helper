package github.ricemonger.marketplace.services.factories;

import github.ricemonger.marketplace.services.PotentialTradeStatsService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.PersonalItem;
import github.ricemonger.utils.DTOs.personal.PotentialPersonalBuyTrade;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class PotentialTradeFactoryTest {
    @SpyBean
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private PotentialTradeStatsService potentialTradeStatsService;

    @Test
    public void getFilteredPotentialBuyTradesForUser_should_filter_personal_items_and_create_potential_buy_trades() {
        PersonalItem ownedPersonalItem = new PersonalItem();
        ownedPersonalItem.setItem(new Item("1"));
        ownedPersonalItem.setIsOwned(true);
        ownedPersonalItem.setTradeOperationType(TradeOperationType.BUY);
        PotentialTradeStats potentialTradeStats1 = new PotentialTradeStats(1, 2, 3L);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(ownedPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats1));

        PersonalItem wrongOperationTypePersonalItem = new PersonalItem();
        wrongOperationTypePersonalItem.setItem(new Item("2"));
        wrongOperationTypePersonalItem.setIsOwned(false);
        wrongOperationTypePersonalItem.setTradeOperationType(TradeOperationType.SELL);
        PotentialTradeStats potentialTradeStats2 = new PotentialTradeStats(4, 5, 6L);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(wrongOperationTypePersonalItem.getItem())).thenReturn(List.of(potentialTradeStats2));

        PersonalItem alreadyExistingTradeWithWrongCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithWrongCategoryPersonalItem.setItem(new Item("3"));
        alreadyExistingTradeWithWrongCategoryPersonalItem.setIsOwned(false);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        UbiTrade ubiTrade1 = new UbiTrade();
        ubiTrade1.setCategory(TradeCategory.Sell);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setTradeAlreadyExists(true);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setExistingTrade(ubiTrade1);
        alreadyExistingTradeWithWrongCategoryPersonalItem.setBuyBoundaryPrice(25);
        PotentialTradeStats potentialTradeStats3 = new PotentialTradeStats(25, 8, 9L);
        PotentialTradeStats potentialTradeStats4 = new PotentialTradeStats(24, 11, 12L);
        PotentialTradeStats potentialTradeStatsBoundaryPrice7 = new PotentialTradeStats(26, 20, 21L);
        PotentialTradeStats ubiTradeStatsWrongCategory = new PotentialTradeStats(30, 20, 100L);
        when(potentialTradeStatsService.calculatePotentialBuyTradeStatsForExistingTrade(same(alreadyExistingTradeWithWrongCategoryPersonalItem.getItem()),same(alreadyExistingTradeWithWrongCategoryPersonalItem.getExistingTrade()))).thenReturn(ubiTradeStatsWrongCategory);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(alreadyExistingTradeWithWrongCategoryPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats3, potentialTradeStats4,potentialTradeStatsBoundaryPrice7));

        PersonalItem alreadyExistingTradeWithProperCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithProperCategoryPersonalItem.setItem(new Item("4"));
        alreadyExistingTradeWithProperCategoryPersonalItem.setIsOwned(false);
        alreadyExistingTradeWithProperCategoryPersonalItem.setTradeOperationType(TradeOperationType.BUY);
        UbiTrade ubiTrade2 = new UbiTrade();
        ubiTrade2.setCategory(TradeCategory.Buy);
        alreadyExistingTradeWithProperCategoryPersonalItem.setTradeAlreadyExists(true);
        alreadyExistingTradeWithProperCategoryPersonalItem.setExistingTrade(ubiTrade2);
        alreadyExistingTradeWithProperCategoryPersonalItem.setBuyBoundaryPrice(25);
        alreadyExistingTradeWithProperCategoryPersonalItem.getItem().setMonthMedianPrice(30);
        alreadyExistingTradeWithProperCategoryPersonalItem.setMinMedianPriceDifference(10);
        alreadyExistingTradeWithProperCategoryPersonalItem.setMinMedianPriceDifferencePercent(46);
        PotentialTradeStats potentialTradeStats5 = new PotentialTradeStats(13, 14, 15L);
        PotentialTradeStats potentialTradeStats6 = new PotentialTradeStats(16, 17, 18L);
        PotentialTradeStats potentialTradeStatsMedianPriceDiffPercent9 = new PotentialTradeStats(17, 26, 27L);
        PotentialTradeStats ubiTradeStatsProperCategory = new PotentialTradeStats(30, 20, 100L);
        when(potentialTradeStatsService.calculatePotentialBuyTradeStatsForExistingTrade(same(alreadyExistingTradeWithProperCategoryPersonalItem.getItem()),same(alreadyExistingTradeWithProperCategoryPersonalItem.getExistingTrade()))).thenReturn(ubiTradeStatsProperCategory);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(alreadyExistingTradeWithProperCategoryPersonalItem.getItem())).thenReturn(List.of(potentialTradeStats5, potentialTradeStats6, potentialTradeStatsMedianPriceDiffPercent9 ));

        PersonalItem noAlreadyExistingTradePersonalItem = new PersonalItem();
        noAlreadyExistingTradePersonalItem.setItem(new Item("5"));
        noAlreadyExistingTradePersonalItem.setIsOwned(false);
        noAlreadyExistingTradePersonalItem.setTradeAlreadyExists(false);
        noAlreadyExistingTradePersonalItem.setTradeOperationType(TradeOperationType.BUY_AND_SELL);
        noAlreadyExistingTradePersonalItem.setBuyBoundaryPrice(300);
        noAlreadyExistingTradePersonalItem.getItem().setMonthMedianPrice(250);
        noAlreadyExistingTradePersonalItem.setMinMedianPriceDifference(50);
        noAlreadyExistingTradePersonalItem.setMinMedianPriceDifferencePercent(19);
        PotentialTradeStats potentialTradeStatsMedianPriceDiff8 = new PotentialTradeStats(201, 23, 24L);
        PotentialTradeStats potentialTradeStats10 = new PotentialTradeStats(200, 29, 30L);
        PotentialTradeStats potentialTradeStats11 = new PotentialTradeStats(199, 32, 33L);
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(noAlreadyExistingTradePersonalItem.getItem())).thenReturn(List.of(potentialTradeStatsMedianPriceDiff8, potentialTradeStats10, potentialTradeStats11));

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getFilteredPotentialBuyTradesForUser(
                List.of(ownedPersonalItem, wrongOperationTypePersonalItem, alreadyExistingTradeWithWrongCategoryPersonalItem, alreadyExistingTradeWithProperCategoryPersonalItem, noAlreadyExistingTradePersonalItem));

        verify(potentialTradeStatsService,times(0)).getPotentialBuyTradesStatsOfItem(ownedPersonalItem.getItem());
        verify(potentialTradeStatsService,times(0)).getPotentialBuyTradesStatsOfItem(wrongOperationTypePersonalItem.getItem());

        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, potentialTradeStats3)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, potentialTradeStats4)));
        assertFalse(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, potentialTradeStatsBoundaryPrice7)));
        assertFalse(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithWrongCategoryPersonalItem, ubiTradeStatsWrongCategory)));

        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, potentialTradeStats5)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, potentialTradeStats6)));
        assertFalse(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, potentialTradeStatsMedianPriceDiffPercent9)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(alreadyExistingTradeWithProperCategoryPersonalItem, ubiTradeStatsProperCategory)));

        assertFalse(result.contains(new PotentialPersonalBuyTrade(noAlreadyExistingTradePersonalItem, potentialTradeStatsMedianPriceDiff8)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(noAlreadyExistingTradePersonalItem, potentialTradeStats10)));
        assertTrue(result.contains(new PotentialPersonalBuyTrade(noAlreadyExistingTradePersonalItem, potentialTradeStats11)));

        assertEquals(7, result.size());
    }
}