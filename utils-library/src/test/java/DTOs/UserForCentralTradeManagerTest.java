package DTOs;

import github.ricemonger.utils.DTOs.personal.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserForCentralTradeManagerTest {

    @Test
    public void constructor_should_set_corresponding_fields() {
        ManageableUser manageableUser = new ManageableUser();
        manageableUser.setId(1L);
        manageableUser.setUbiSessionId("session");
        manageableUser.setUbiSpaceId("space");
        manageableUser.setUbiAuthTicket("auth");
        manageableUser.setUbiRememberDeviceTicket("device");
        manageableUser.setUbiRememberMeTicket("me");
        manageableUser.setChatId("chat");
        manageableUser.setPrivateNotificationsEnabledFlag(true);
        manageableUser.setTradeByFiltersManagers(List.of(new TradeByFiltersManager()));
        manageableUser.setTradeByItemIdManagers(List.of(new TradeByItemIdManager()));
        UbiAccountStats ubiAccount = new UbiAccountStats();

        UserForCentralTradeManager user = new UserForCentralTradeManager(manageableUser, ubiAccount);

        assertEquals(1L, user.getId());
        assertEquals("session", user.getUbiSessionId());
        assertEquals("space", user.getUbiSpaceId());
        assertEquals("auth", user.getUbiAuthTicket());
        assertEquals("device", user.getUbiRememberDeviceTicket());
        assertEquals("me", user.getUbiRememberMeTicket());
        assertEquals("chat", user.getChatId());
        assertTrue(user.getPrivateNotificationsEnabledFlag());
        assertEquals(1, user.getTradeByFiltersManagers().size());
        assertEquals(new TradeByFiltersManager(), user.getTradeByFiltersManagers().get(0));
        assertEquals(1, user.getTradeByItemIdManagers().size());
        assertEquals(new TradeByItemIdManager(), user.getTradeByItemIdManagers().get(0));
    }

    @Test
    public void getUbiProfileId_should_return_ubi_profile_id() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setUbiProfileId("profile");

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals("profile", user.getUbiProfileId());
    }

    @Test
    public void getUbiProfileId_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getUbiProfileId());
    }

    @Test
    public void getSoldIn24h_should_return_sold_in_24h() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setSoldIn24h(10);

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(10, user.getSoldIn24h());
    }

    @Test
    public void getSoldIn24h_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getSoldIn24h());
    }

    @Test
    public void getBoughtIn24h_should_return_bought_in_24h() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setBoughtIn24h(10);

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(10, user.getBoughtIn24h());
    }

    @Test
    public void getBoughtIn24h_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getBoughtIn24h());
    }

    @Test
    public void getCreditAmount_should_return_credit_amount() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setCreditAmount(10);

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(10, user.getCreditAmount());
    }

    @Test
    public void getCreditAmount_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getCreditAmount());
    }

    @Test
    public void getOwnedItemsIds_should_return_owned_items_ids() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setOwnedItemsIds(List.of("1", "2"));

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(List.of("1", "2"), user.getOwnedItemsIds());
    }

    @Test
    public void getOwnedItemsIds_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getOwnedItemsIds());
    }

    @Test
    public void getResaleLocks_should_return_resale_items() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setResaleLocks(List.of(new ItemResaleLockWithUbiAccount()));

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(1, user.getResaleLocks().size());
        assertEquals(new ItemResaleLockWithUbiAccount(), user.getResaleLocks().get(0));
    }

    @Test
    public void getResaleLocks_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getResaleLocks());
    }

    @Test
    public void getCurrentSellTrades_should_return_current_sell_trades() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setCurrentSellTrades(List.of(new UbiTrade()));

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(1, user.getCurrentSellTrades().size());
        assertEquals(new UbiTrade(), user.getCurrentSellTrades().get(0));
    }

    @Test
    public void getCurrentSellTrades_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getCurrentSellTrades());
    }

    @Test
    public void getCurrentBuyTrades_should_return_current_buy_trades() {
        UbiAccountStats ubiAccount = new UbiAccountStats();
        ubiAccount.setCurrentBuyTrades(List.of(new UbiTrade()));

        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(ubiAccount);

        assertEquals(1, user.getCurrentBuyTrades().size());
        assertEquals(new UbiTrade(), user.getCurrentBuyTrades().get(0));
    }

    @Test
    public void getCurrentBuyTrades_should_return_null_when_ubi_account_stats_is_null() {
        UserForCentralTradeManager user = new UserForCentralTradeManager();
        user.setUbiAccountStats(null);

        assertNull(user.getCurrentBuyTrades());
    }
}