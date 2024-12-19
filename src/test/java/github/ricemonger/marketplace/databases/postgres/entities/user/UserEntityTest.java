package github.ricemonger.marketplace.databases.postgres.entities.user;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserEntityTest {
    @Test
    public void isFullyEqual_should_return_true_if_same() {
        UserEntity user = new UserEntity();
        assertTrue(user.isFullyEqual(user));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(new TelegramUserEntity());
        user1.setUbiAccountAuthorizationEntry(new UbiAccountEntryEntity());
        user1.setItemFilters(List.of(new ItemFilterEntity()));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user1.setPublicNotificationsEnabledFlag(true);
        user1.setPrivateNotificationsEnabledFlag(true);
        user1.setItemShowNameFlag(true);
        user1.setItemShowItemTypeFlag(true);
        user1.setItemShowMaxBuyPrice(true);
        user1.setItemShowBuyOrdersCountFlag(true);
        user1.setItemShowMinSellPriceFlag(true);
        user1.setItemsShowSellOrdersCountFlag(true);
        user1.setItemShowPictureFlag(true);
        user1.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(true);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setTelegramUser(new TelegramUserEntity());
        user2.setUbiAccountAuthorizationEntry(new UbiAccountEntryEntity());
        user2.setItemFilters(List.of(new ItemFilterEntity()));
        user2.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user2.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user2.setPublicNotificationsEnabledFlag(true);
        user2.setPrivateNotificationsEnabledFlag(true);
        user2.setItemShowNameFlag(true);
        user2.setItemShowItemTypeFlag(true);
        user2.setItemShowMaxBuyPrice(true);
        user2.setItemShowBuyOrdersCountFlag(true);
        user2.setItemShowMinSellPriceFlag(true);
        user2.setItemsShowSellOrdersCountFlag(true);
        user2.setItemShowPictureFlag(true);
        user2.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user2.setNewManagersAreActiveFlag(true);
        user2.setManagingEnabledFlag(true);

        assertTrue(user1.isFullyEqual(user2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(new TelegramUserEntity());
        user1.setUbiAccountAuthorizationEntry(new UbiAccountEntryEntity());
        user1.setItemFilters(List.of(new ItemFilterEntity()));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user1.setPublicNotificationsEnabledFlag(true);
        user1.setPrivateNotificationsEnabledFlag(true);
        user1.setItemShowNameFlag(true);
        user1.setItemShowItemTypeFlag(true);
        user1.setItemShowMaxBuyPrice(true);
        user1.setItemShowBuyOrdersCountFlag(true);
        user1.setItemShowMinSellPriceFlag(true);
        user1.setItemsShowSellOrdersCountFlag(true);
        user1.setItemShowPictureFlag(true);
        user1.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(true);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setTelegramUser(new TelegramUserEntity());
        user2.setUbiAccountAuthorizationEntry(new UbiAccountEntryEntity());
        user2.setItemFilters(List.of(new ItemFilterEntity()));
        user2.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user2.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user2.setPublicNotificationsEnabledFlag(true);
        user2.setPrivateNotificationsEnabledFlag(true);
        user2.setItemShowNameFlag(true);
        user2.setItemShowItemTypeFlag(true);
        user2.setItemShowMaxBuyPrice(true);
        user2.setItemShowBuyOrdersCountFlag(true);
        user2.setItemShowMinSellPriceFlag(true);
        user2.setItemsShowSellOrdersCountFlag(true);
        user2.setItemShowPictureFlag(true);
        user2.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user2.setNewManagersAreActiveFlag(true);
        user2.setManagingEnabledFlag(true);

        user1.setId(2L);
        assertFalse(user1.isFullyEqual(user2));
        user1.setId(1L);
        user1.setTelegramUser(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTelegramUser(new TelegramUserEntity("chatId",user1));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTelegramUser(new TelegramUserEntity());
        user1.setUbiAccountAuthorizationEntry(null);
        assertFalse(user1.isFullyEqual(user2));
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAuthTicket("ticket");
        user1.setUbiAccountAuthorizationEntry(ubiAccountEntryEntity);
        assertFalse(user1.isFullyEqual(user2));
        user1.setUbiAccountAuthorizationEntry(new UbiAccountEntryEntity());
        user1.setItemFilters(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of(new ItemFilterEntity()));
        user1.setTradeByFiltersManagers(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user1.setPublicNotificationsEnabledFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setPublicNotificationsEnabledFlag(true);
        user1.setPrivateNotificationsEnabledFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setPrivateNotificationsEnabledFlag(true);
        user1.setItemShowNameFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowNameFlag(true);
        user1.setItemShowItemTypeFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowItemTypeFlag(true);
        user1.setItemShowMaxBuyPrice(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowMaxBuyPrice(true);
        user1.setItemShowBuyOrdersCountFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowBuyOrdersCountFlag(true);
        user1.setItemShowMinSellPriceFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowMinSellPriceFlag(true);
        user1.setItemsShowSellOrdersCountFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemsShowSellOrdersCountFlag(true);
        user1.setItemShowPictureFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowPictureFlag(true);
        user1.setItemShowAppliedFilters(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowAppliedFilters(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user1.setNewManagersAreActiveFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(false);
        assertFalse(user1.isFullyEqual(user2));
    }
}