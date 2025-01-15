package github.ricemonger.fast_sell_trade_manager.scheduled_tasks;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastTradeManagerCommand;
import github.ricemonger.fast_sell_trade_manager.services.TradeManagementCommandsExecutor;
import github.ricemonger.fast_sell_trade_manager.services.UbiAccountEntryService;
import github.ricemonger.fast_sell_trade_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementCommandsFactory;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.FastUserUbiStats;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduledOneUserFastSellTradeManagerTest {
    @Autowired
    private ScheduledOneUserFastSellTradeManager scheduledOneUserFastSellTradeManager;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private UbiAccountEntryService ubiAccountEntryService;
    @MockBean
    private TradeManagementCommandsFactory tradeManagementCommandsFactory;
    @MockBean
    private TradeManagementCommandsExecutor fastTradeManagementCommandExecutor;
    @MockBean
    private PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService graphQlClientService;
    @MockBean
    private PotentialTradeFactory potentialTradeFactory;

    @Test
    public void manageOneUserFastSellTrades_should_fetch_info_and_execute_commands() {
        List itemCurrentPrices = Mockito.mock(List.class);
        List sellTrades = Mockito.mock(List.class);

        AuthorizationDTO authorizationDTO = Mockito.mock(AuthorizationDTO.class);

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(user.toAuthorizationDTO()).thenReturn(authorizationDTO);
        when(user.getUbiProfileId()).thenReturn("profileId");

        when(commonValuesService.getFastSellManagedUserId()).thenReturn(33L);
        when(commonValuesService.getFastSellManagedUserEmail()).thenReturn("email");

        when(ubiAccountEntryService.getFastSellManagedUserById(33L, "email")).thenReturn(user);

        scheduledOneUserFastSellTradeManager.fetchManagedUserAuthorizationFromDb();

        when(commonValuesService.getFastTradeOwnedItemsLimit()).thenReturn(120);

        FastUserUbiStats ubiStats = new FastUserUbiStats();
        ubiStats.setItemsCurrentPrices(itemCurrentPrices);
        ubiStats.setCurrentSellOrders(sellTrades);

        when(graphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(same(authorizationDTO), eq(120))).thenReturn(ubiStats);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setSellSlots(3);
        configTrades.setSellLimit(4);

        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        List medianPricesAndRarity = Mockito.mock(List.class);

        when(ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity("profileId")).thenReturn(medianPricesAndRarity);

        scheduledOneUserFastSellTradeManager.fetchItemMedianPriceFromDb();

        List potentialTrades = Mockito.mock(List.class);

        when(potentialTradeFactory.createPotentialTradesForUser(same(itemCurrentPrices), same(medianPricesAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);


        FastTradeManagerCommand command1 = Mockito.mock(FastTradeManagerCommand.class);
        FastTradeManagerCommand command2 = Mockito.mock(FastTradeManagerCommand.class);

        List commands = List.of(command1, command2);
        when(tradeManagementCommandsFactory.createFastSellTradeManagerCommandsForUser(same(user), same(sellTrades), same(potentialTrades), same(medianPricesAndRarity), eq(4), eq(3))).thenReturn(commands);

        scheduledOneUserFastSellTradeManager.manageOneUserFastSellTrades();

        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command1));
        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command2));
    }

    @Test
    public void fetchItemMedianPriceFromDb_should_get_sell_slots_and_sell_limit_and_median_prices_and_rarity() {
        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(user.getUbiProfileId()).thenReturn("profileId");

        when(commonValuesService.getFastSellManagedUserId()).thenReturn(33L);
        when(commonValuesService.getFastSellManagedUserEmail()).thenReturn("email");

        when(ubiAccountEntryService.getFastSellManagedUserById(33L, "email")).thenReturn(user);

        scheduledOneUserFastSellTradeManager.fetchManagedUserAuthorizationFromDb();

        ConfigTrades configTrades = spy(new ConfigTrades());
        configTrades.setSellSlots(3);
        configTrades.setSellLimit(4);

        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        List medianPricesAndRarity = Mockito.mock(List.class);

        when(ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity("profileId")).thenReturn(medianPricesAndRarity);

        scheduledOneUserFastSellTradeManager.fetchItemMedianPriceFromDb();

        Mockito.verify(commonValuesService).getConfigTrades();
        verify(configTrades).getSellSlots();
        verify(configTrades).getSellLimit();
        Mockito.verify(ubiAccountEntryService).getOwnedItemsMedianPriceAndRarity("profileId");
    }

    @Test
    public void fetchManagedUserAuthorizationFromDb_should_get_user_by_id_and_email() {
        when(commonValuesService.getFastSellManagedUserId()).thenReturn(33L);
        when(commonValuesService.getFastSellManagedUserEmail()).thenReturn("email");

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(ubiAccountEntryService.getFastSellManagedUserById(33L, "email")).thenReturn(user);

        scheduledOneUserFastSellTradeManager.fetchManagedUserAuthorizationFromDb();

        Mockito.verify(ubiAccountEntryService).getFastSellManagedUserById(33L, "email");
    }
}