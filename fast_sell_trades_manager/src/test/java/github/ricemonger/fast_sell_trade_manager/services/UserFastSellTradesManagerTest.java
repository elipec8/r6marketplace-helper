package github.ricemonger.fast_sell_trade_manager.services;

import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellCommand;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementCommandsFactory;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;
import github.ricemonger.utils.DTOs.personal.FastUserUbiStats;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserFastSellTradesManagerTest {
    @SpyBean
    private UserFastSellTradesManager userFastSellTradesManager;
    @MockBean
    private PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService graphQlClientService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private TradeManagementCommandsFactory tradeManagementCommandsFactory;
    @MockBean
    private TradeManagementCommandsExecutor fastTradeManagementCommandExecutor;

    @Test
    public void createAndExecuteCommandsToKeepOneSellSlotUnused_should_fetch_info_and_execute_commands() {
        List itemCurrentPrices = Mockito.mock(List.class);
        List sellTrades = Mockito.mock(List.class);

        AuthorizationDTO authorizationDTO = Mockito.mock(AuthorizationDTO.class);

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(user.toAuthorizationDTO()).thenReturn(authorizationDTO);
        when(user.getUbiProfileId()).thenReturn("profileId");

        when(commonValuesService.getFastTradeOwnedItemsLimit()).thenReturn(120);

        FastUserUbiStats ubiStats = new FastUserUbiStats();
        ubiStats.setItemsCurrentPrices(itemCurrentPrices);
        ubiStats.setCurrentSellOrders(sellTrades);

        when(graphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(same(authorizationDTO), eq(120))).thenReturn(ubiStats);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        List medianPricesAndRarity = Mockito.mock(List.class);

        FastSellCommand command1 = Mockito.mock(FastSellCommand.class);
        FastSellCommand command2 = Mockito.mock(FastSellCommand.class);

        List commands = List.of(command1, command2);
        when(tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(same(user), same(sellTrades), same(itemCurrentPrices), same(medianPricesAndRarity), eq(3), eq(4))).thenReturn(commands);

        userFastSellTradesManager.createAndExecuteCommandsToKeepOneSellSlotUnused(user, medianPricesAndRarity, 3, 4);

        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command1));
        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command2));
    }
}