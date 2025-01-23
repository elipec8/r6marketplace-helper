package github.ricemonger.fast_sell_trade_manager.scheduled_tasks;

import github.ricemonger.fast_sell_trade_manager.services.CommonValuesService;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.fast_sell_trade_manager.services.UbiAccountService;
import github.ricemonger.fast_sell_trade_manager.services.UserFastSellTradesManager;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
    private UbiAccountService ubiAccountService;

    @Test
    public void submitCreateCommandsTaskByFetchedUserStats_should_submitAsyncCreateCommandsTask_with_fields_values() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = Mockito.mock(List.class);
        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        int sellLimit = 3;
        int sellSlots = 4;

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "itemsMedianPriceAndRarity", itemsMedianPriceAndRarity);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "managedUser", user);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellLimit", sellLimit);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellSlots", sellSlots);

        scheduledOneUserFastSellTradeManager.submitCreateCommandsTaskByFetchedUserStats();

        verify(userFastSellTradesManager).submitCreateCommandsTaskByFetchedUserStats(same(user), same(itemsMedianPriceAndRarity), eq(3), eq(4));
    }

    @Test
    public void submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices_should_submitAsyncCreateCommandsTask_with_fields_values_and_cycle_through_users() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = Mockito.mock(List.class);
        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        int sellLimit = 3;
        int sellSlots = 4;

        List<AuthorizationDTO> fetchUsersAuthorizationDTOs = new ArrayList<>();
        AuthorizationDTO authorizationDTO1 = Mockito.mock(AuthorizationDTO.class);
        AuthorizationDTO authorizationDTO2 = Mockito.mock(AuthorizationDTO.class);
        fetchUsersAuthorizationDTOs.add(authorizationDTO1);
        fetchUsersAuthorizationDTOs.add(authorizationDTO2);

        int currentFetchUserIndex = 0;

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "itemsMedianPriceAndRarity", itemsMedianPriceAndRarity);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "managedUser", user);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellLimit", sellLimit);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellSlots", sellSlots);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "fetchUsersAuthorizationDTOs", fetchUsersAuthorizationDTOs);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "currentFetchUserIndex", currentFetchUserIndex);

        scheduledOneUserFastSellTradeManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices();

        verify(userFastSellTradesManager).submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(same(user), same(authorizationDTO1), same(itemsMedianPriceAndRarity), eq(3), eq(4));
        assertEquals(1, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "currentFetchUserIndex"));

        scheduledOneUserFastSellTradeManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices();
        verify(userFastSellTradesManager).submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(same(user), same(authorizationDTO2), same(itemsMedianPriceAndRarity), eq(3), eq(4));
        assertEquals(2, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "currentFetchUserIndex"));

        scheduledOneUserFastSellTradeManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices();
        verify(userFastSellTradesManager, times(2)).submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(same(user), same(authorizationDTO1), same(itemsMedianPriceAndRarity), eq(3), eq(4));
        assertEquals(1, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "currentFetchUserIndex"));
    }

    @Test
    public void submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices_should_do_nothing_if_users_empty() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = Mockito.mock(List.class);
        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        int sellLimit = 3;
        int sellSlots = 4;

        List<AuthorizationDTO> fetchUsersAuthorizationDTOs = new ArrayList<>();

        int currentFetchUserIndex = 0;

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "itemsMedianPriceAndRarity", itemsMedianPriceAndRarity);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "managedUser", user);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellLimit", sellLimit);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellSlots", sellSlots);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "fetchUsersAuthorizationDTOs", fetchUsersAuthorizationDTOs);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "currentFetchUserIndex", currentFetchUserIndex);

        scheduledOneUserFastSellTradeManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices();

        verify(userFastSellTradesManager, never()).submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(any(), any(), any(), anyInt(), anyInt());
    }

    @Test
    public void executeFastSellCommands_should_executeFastSellCommands() {
        scheduledOneUserFastSellTradeManager.executeFastSellCommands();

        verify(userFastSellTradesManager).executeFastSellCommands();
    }

    @Test
    public void keepUnusedOneSellSlotForManagedUser_should_createAndExecuteCommandsToKeepOneSellSlotUnused_with_fields_values() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = Mockito.mock(List.class);
        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        int sellLimit = 3;
        int sellSlots = 4;

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "itemsMedianPriceAndRarity", itemsMedianPriceAndRarity);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "managedUser", user);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellLimit", sellLimit);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellSlots", sellSlots);

        scheduledOneUserFastSellTradeManager.keepUnusedOneSellSlotForManagedUser();

        verify(userFastSellTradesManager).createAndExecuteCommandsToKeepOneSellSlotUnused(same(user), same(itemsMedianPriceAndRarity), eq(sellLimit), eq(sellSlots));
    }

    @Test
    public void fetchItemMedianPriceFromDb_should_save_fields_values_from_db() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setSellLimit(3);
        configTrades.setSellSlots(4);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        List<ItemMedianPriceAndRarity> itemsMedianPriceAndRarity = mock(List.class);

        FastSellManagedUser user = new FastSellManagedUser();
        user.setUbiProfileId("ubiProfileId");

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "managedUser", user);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "itemsMedianPriceAndRarity", new ArrayList<>());
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellLimit", 0);
        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "sellSlots", 0);

        when(ubiAccountService.getOwnedItemsMedianPriceAndRarity("ubiProfileId")).thenReturn(itemsMedianPriceAndRarity);

        scheduledOneUserFastSellTradeManager.fetchItemMedianPriceAndConfigTradesFromDb();
        assertEquals(3, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "sellLimit"));
        assertEquals(4, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "sellSlots"));
        assertSame(itemsMedianPriceAndRarity, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "itemsMedianPriceAndRarity"));
    }

    @Test
    public void fetchManagedUserAuthorizationFromDb_should_save_fields_values_from_db() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        FastSellManagedUser oldUser = mock(FastSellManagedUser.class);
        when(commonValuesService.getFastSellManagedUserId()).thenReturn(33L);
        when(commonValuesService.getFastSellManagedUserEmail()).thenReturn("email");

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "managedUser", oldUser);

        FastSellManagedUser newUser = new FastSellManagedUser();

        when(ubiAccountService.getFastSellManagedUserById(33L, "email")).thenReturn(newUser);

        scheduledOneUserFastSellTradeManager.fetchManagedUserAuthorizationFromDb();
        assertSame(newUser, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "managedUser"));
    }

    @Test
    public void fetchFetchUsersAuthorizationFromDb_should_get_all_fetch_users_authorizationDTOs() {
        scheduledOneUserFastSellTradeManager = new ScheduledOneUserFastSellTradeManager(userFastSellTradesManager, commonValuesService, ubiAccountService);

        List oldList = mock(List.class);

        ReflectionTestUtils.setField(scheduledOneUserFastSellTradeManager, "fetchUsersAuthorizationDTOs", oldList);

        List newList = new ArrayList();
        when(ubiAccountService.getAllFetchAccountsAuthorizationDTOs()).thenReturn(newList);

        scheduledOneUserFastSellTradeManager.fetchFetchUsersAuthorizationFromDb();

        assertSame(newList, ReflectionTestUtils.getField(scheduledOneUserFastSellTradeManager, "fetchUsersAuthorizationDTOs"));
    }
}