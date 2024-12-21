package github.ricemonger.marketplace.services.factories;

import github.ricemonger.marketplace.services.PotentialTradeStatsService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.PersonalItem;
import github.ricemonger.utils.DTOs.personal.PotentialPersonalBuyTrade;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        PersonalItem wrongOperationTypePersonalItem = new PersonalItem();
        wrongOperationTypePersonalItem.setItem(new Item("2"));

        PersonalItem alreadyExistingTradeWithWrongCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithWrongCategoryPersonalItem.setItem(new Item("3"));

        PersonalItem alreadyExistingTradeWithProperCategoryPersonalItem = new PersonalItem();
        alreadyExistingTradeWithProperCategoryPersonalItem.setItem(new Item("4"));

        PersonalItem noAlreadyExistingTradePersonalItem = new PersonalItem();
        noAlreadyExistingTradePersonalItem.setItem(new Item("5"));

        List<PotentialTradeStats> alreadyExistingTradeWithWrongCategoryPotentialTradeStats = List.of(new PotentialTradeStats());
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(wrongOperationTypePersonalItem.getItem())).thenReturn(alreadyExistingTradeWithWrongCategoryPotentialTradeStats);

        List<PotentialTradeStats> alreadyExistingTradeWithProperCategoryPotentialTradeStats = List.of(new PotentialTradeStats());
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(alreadyExistingTradeWithProperCategoryPersonalItem.getItem())).thenReturn(alreadyExistingTradeWithProperCategoryPotentialTradeStats);

        List<PotentialTradeStats> noAlreadyExistingTradePotentialTradeStats = List.of(new PotentialTradeStats());
        when(potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(noAlreadyExistingTradePersonalItem.getItem())).thenReturn(noAlreadyExistingTradePotentialTradeStats);

        List<PotentialPersonalBuyTrade> result = potentialTradeFactory.getFilteredPotentialBuyTradesForUser(
                List.of(ownedPersonalItem, wrongOperationTypePersonalItem, alreadyExistingTradeWithWrongCategoryPersonalItem, alreadyExistingTradeWithProperCategoryPersonalItem, noAlreadyExistingTradePersonalItem));

        verify(potentialTradeStatsService).getPotentialBuyTradesStatsOfItem(ownedPersonalItem.getItem());
        verify(potentialTradeStatsService).getPotentialBuyTradesStatsOfItem(wrongOperationTypePersonalItem.getItem());
    }

}