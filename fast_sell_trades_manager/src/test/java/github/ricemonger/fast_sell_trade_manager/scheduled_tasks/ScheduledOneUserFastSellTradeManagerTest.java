package github.ricemonger.fast_sell_trade_manager.scheduled_tasks;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.UbiAccountEntryService;
import github.ricemonger.fast_sell_trade_manager.services.UserFastSellTradesManager;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
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
    private UserFastSellTradesManager userFastSellTradesManager;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private UbiAccountEntryService ubiAccountEntryService;

    @Test
    public void createFastSellTrades_should_sumbitAsyncCreateCommandsTask() {
        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = Mockito.mock(List.class);

        AuthorizationDTO authorizationDTO = Mockito.mock(AuthorizationDTO.class);

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(user.toAuthorizationDTO()).thenReturn(authorizationDTO);
        when(user.getUbiProfileId()).thenReturn("profileId");

        when(commonValuesService.getFastSellManagedUserId()).thenReturn(33L);
        when(commonValuesService.getFastSellManagedUserEmail()).thenReturn("email");

        when(ubiAccountEntryService.getFastSellManagedUserById(33L, "email")).thenReturn(user);

        ConfigTrades configTrades = spy(new ConfigTrades());
        configTrades.setSellLimit(3);
        configTrades.setSellSlots(4);

        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        scheduledOneUserFastSellTradeManager.fetchManagedUserAuthorizationFromDb();

        when(ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity("profileId")).thenReturn(itemsMedianPriceAndRarity);

        scheduledOneUserFastSellTradeManager.fetchItemMedianPriceAndConfigTradesFromDb();

        scheduledOneUserFastSellTradeManager.createFastSellCommands();

        verify(userFastSellTradesManager).submitAsyncCreateCommandsTask(eq(user), same(itemsMedianPriceAndRarity), eq(3), eq(4));
    }

    @Test
    public void executeFastSellCommands_should_executeFastSellCommands() {
        scheduledOneUserFastSellTradeManager.executeFastSellCommands();

        verify(userFastSellTradesManager).executeFastSellCommands();
    }

    @Test
    public void keepUnusedOneSellSlotForManagedUser_should_createAndExecuteCommandsToKeepOneSellSlotUnused() {
        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = Mockito.mock(List.class);

        AuthorizationDTO authorizationDTO = Mockito.mock(AuthorizationDTO.class);

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(user.toAuthorizationDTO()).thenReturn(authorizationDTO);
        when(user.getUbiProfileId()).thenReturn("profileId");

        when(commonValuesService.getFastSellManagedUserId()).thenReturn(33L);
        when(commonValuesService.getFastSellManagedUserEmail()).thenReturn("email");

        when(ubiAccountEntryService.getFastSellManagedUserById(33L, "email")).thenReturn(user);

        ConfigTrades configTrades = spy(new ConfigTrades());
        configTrades.setSellLimit(3);
        configTrades.setSellSlots(4);

        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        scheduledOneUserFastSellTradeManager.fetchManagedUserAuthorizationFromDb();

        when(ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity("profileId")).thenReturn(itemsMedianPriceAndRarity);

        scheduledOneUserFastSellTradeManager.fetchItemMedianPriceAndConfigTradesFromDb();

        scheduledOneUserFastSellTradeManager.keepUnusedOneSellSlotForManagedUser();

        verify(userFastSellTradesManager).createAndExecuteCommandsToKeepOneSellSlotUnused(eq(user), same(itemsMedianPriceAndRarity), eq(3), eq(4));
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
        configTrades.setSellLimit(3);
        configTrades.setSellSlots(4);

        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        List medianPricesAndRarity = Mockito.mock(List.class);

        when(ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity("profileId")).thenReturn(medianPricesAndRarity);

        scheduledOneUserFastSellTradeManager.fetchItemMedianPriceAndConfigTradesFromDb();

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