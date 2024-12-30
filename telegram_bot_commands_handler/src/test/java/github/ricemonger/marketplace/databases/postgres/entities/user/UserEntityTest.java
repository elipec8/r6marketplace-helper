package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_item_filter_service.ItemFilterUserIdEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserEntityTest {

    @Test
    public void isEqual_should_return_true_if_same() {
        UserEntity user = new UserEntity();
        assertTrue(user.isEqual(user));
    }

    @Test
    public void isEqual_should_return_true_if_equal_ids() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(new TelegramUserEntity("chatId", user1));
        user1.setUbiAccountEntry(new UbiAccountEntryEntity(1L, "email", "ubiProfileId"));
        user1.setItemFilters(List.of(new ItemFilterUserIdEntity()));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user1.setPublicNotificationsEnabledFlag(false);
        user1.setPrivateNotificationsEnabledFlag(false);
        user1.setItemShowNameFlag(false);
        user1.setItemShowItemTypeFlag(false);
        user1.setItemShowMaxBuyPrice(false);
        user1.setItemShowBuyOrdersCountFlag(false);
        user1.setItemShowMinSellPriceFlag(false);
        user1.setItemsShowSellOrdersCountFlag(false);
        user1.setItemShowPictureFlag(false);
        user1.setItemShowAppliedFilters(List.of(new ItemFilterUserIdEntity()));
        user1.setNewManagersAreActiveFlag(false);
        user1.setManagingEnabledFlag(false);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);

        assertTrue(user1.isEqual(user2));
    }

    @Test
    public void isEqual_should_return_false_if_null() {
        UserEntity user = new UserEntity();
        assertFalse(user.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_if_different_ids() {
        UserEntity user1 = new UserEntity(1L);

        UserEntity user2 = new UserEntity(2L);

        assertFalse(user1.isEqual(user2));
    }


    @Test
    public void isFullyEqual_should_return_true_if_same() {
        UserEntity user = new UserEntity();
        assertTrue(user.isFullyEqual(user));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity1 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity1.setUser(new UserEntity(1L));
        tradeByFiltersManagerEntity1.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity1 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity1.setUser(new UserEntity(1L));
        tradeByItemIdManagerEntity1.setItem(new ItemEntity("itemId1"));

        ItemFilterUserIdEntity itemFilterEntity1 = new ItemFilterUserIdEntity();
        itemFilterEntity1.setUser(new UserEntity(1L));
        itemFilterEntity1.setName("name");

        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(new TelegramUserEntity("chatId", 1L));
        user1.setUbiAccountEntry(new UbiAccountEntryEntity(1L, "email", "ubiProfileId"));
        user1.setItemFilters(List.of(itemFilterEntity1));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));
        user1.setPublicNotificationsEnabledFlag(true);
        user1.setPrivateNotificationsEnabledFlag(true);
        user1.setItemShowNameFlag(true);
        user1.setItemShowItemTypeFlag(true);
        user1.setItemShowMaxBuyPrice(true);
        user1.setItemShowBuyOrdersCountFlag(true);
        user1.setItemShowMinSellPriceFlag(true);
        user1.setItemsShowSellOrdersCountFlag(true);
        user1.setItemShowPictureFlag(true);
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity1));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(true);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity2 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity2.setUser(new UserEntity(1L));
        tradeByFiltersManagerEntity2.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity2 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity2.setUser(new UserEntity(1L));
        tradeByItemIdManagerEntity2.setItem(new ItemEntity("itemId1"));

        ItemFilterUserIdEntity itemFilterEntity2 = new ItemFilterUserIdEntity();
        itemFilterEntity2.setUser(new UserEntity(1L));
        itemFilterEntity2.setName("name");

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setTelegramUser(new TelegramUserEntity("chatId", 1L));
        user2.setUbiAccountEntry(new UbiAccountEntryEntity(1L, "email", "ubiProfileId"));
        user2.setItemFilters(List.of(itemFilterEntity2));
        user2.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity2));
        user2.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity2));
        user2.setPublicNotificationsEnabledFlag(true);
        user2.setPrivateNotificationsEnabledFlag(true);
        user2.setItemShowNameFlag(true);
        user2.setItemShowItemTypeFlag(true);
        user2.setItemShowMaxBuyPrice(true);
        user2.setItemShowBuyOrdersCountFlag(true);
        user2.setItemShowMinSellPriceFlag(true);
        user2.setItemsShowSellOrdersCountFlag(true);
        user2.setItemShowPictureFlag(true);
        user2.setItemShowAppliedFilters(List.of(itemFilterEntity2));
        user2.setNewManagersAreActiveFlag(true);
        user2.setManagingEnabledFlag(true);

        assertTrue(user1.isFullyEqual(user2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        TradeByFiltersManagerEntity tradeByFiltersManagerEntity1 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity1.setUser(new UserEntity(1L));
        tradeByFiltersManagerEntity1.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity1 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity1.setUser(new UserEntity(1L));
        tradeByItemIdManagerEntity1.setItem(new ItemEntity("itemId1"));

        ItemFilterUserIdEntity itemFilterEntity1 = new ItemFilterUserIdEntity();
        itemFilterEntity1.setUser(new UserEntity(1L));
        itemFilterEntity1.setName("name");

        TelegramUserEntity telegramUser1 = new TelegramUserEntity("chatId", 1L);

        UbiAccountEntryEntity ubiAccountEntryEntity1 = new UbiAccountEntryEntity(1L, "email", "ubiProfileId");


        TradeByFiltersManagerEntity tradeByFiltersManagerEntity2 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity2.setUser(new UserEntity(2L));
        tradeByFiltersManagerEntity2.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity2 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity2.setUser(new UserEntity(2L));
        tradeByItemIdManagerEntity2.setItem(new ItemEntity("itemId1"));

        ItemFilterUserIdEntity itemFilterEntity2 = new ItemFilterUserIdEntity();
        itemFilterEntity2.setUser(new UserEntity(2L));
        itemFilterEntity2.setName("name");

        TelegramUserEntity telegramUser2 = new TelegramUserEntity("chatId", 2L);

        UbiAccountEntryEntity ubiAccountEntryEntity2 = new UbiAccountEntryEntity(2L, "email", "ubiProfileId");


        TradeByFiltersManagerEntity tradeByFiltersManagerEntity3 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity3.setUser(new UserEntity(1L));
        tradeByFiltersManagerEntity3.setName("name1");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity3 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity3.setUser(new UserEntity(1L));
        tradeByItemIdManagerEntity3.setItem(new ItemEntity("itemId2"));

        ItemFilterUserIdEntity itemFilterEntity3 = new ItemFilterUserIdEntity();
        itemFilterEntity3.setUser(new UserEntity(1L));
        itemFilterEntity3.setName("name2");

        TelegramUserEntity telegramUser3 = new TelegramUserEntity("chatId2", 1L);

        UbiAccountEntryEntity ubiAccountEntryEntity3 = new UbiAccountEntryEntity(1L, "email2", "ubiProfileId");

        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(telegramUser1);
        user1.setUbiAccountEntry(ubiAccountEntryEntity1);
        user1.setItemFilters(List.of(itemFilterEntity1));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));
        user1.setPublicNotificationsEnabledFlag(true);
        user1.setPrivateNotificationsEnabledFlag(true);
        user1.setItemShowNameFlag(true);
        user1.setItemShowItemTypeFlag(true);
        user1.setItemShowMaxBuyPrice(true);
        user1.setItemShowBuyOrdersCountFlag(true);
        user1.setItemShowMinSellPriceFlag(true);
        user1.setItemsShowSellOrdersCountFlag(true);
        user1.setItemShowPictureFlag(true);
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity1));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(true);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setTelegramUser(telegramUser1);
        user2.setUbiAccountEntry(ubiAccountEntryEntity1);
        user2.setItemFilters(List.of(itemFilterEntity1));
        user2.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user2.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));
        user2.setPublicNotificationsEnabledFlag(true);
        user2.setPrivateNotificationsEnabledFlag(true);
        user2.setItemShowNameFlag(true);
        user2.setItemShowItemTypeFlag(true);
        user2.setItemShowMaxBuyPrice(true);
        user2.setItemShowBuyOrdersCountFlag(true);
        user2.setItemShowMinSellPriceFlag(true);
        user2.setItemsShowSellOrdersCountFlag(true);
        user2.setItemShowPictureFlag(true);
        user2.setItemShowAppliedFilters(List.of(itemFilterEntity1));
        user2.setNewManagersAreActiveFlag(true);
        user2.setManagingEnabledFlag(true);

        user1.setId(2L);
        assertFalse(user1.isFullyEqual(user2));
        user1.setId(1L);
        user1.setTelegramUser(telegramUser2);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTelegramUser(telegramUser3);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTelegramUser(telegramUser1);
        user1.setUbiAccountEntry(ubiAccountEntryEntity2);
        assertFalse(user1.isFullyEqual(user2));
        user1.setUbiAccountEntry(ubiAccountEntryEntity3);
        assertFalse(user1.isFullyEqual(user2));
        user1.setUbiAccountEntry(ubiAccountEntryEntity1);
        user1.setItemFilters(List.of(itemFilterEntity2));
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of(itemFilterEntity3));
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of(itemFilterEntity1));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity2));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity3));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity2));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity3));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));
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
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity2));
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity3));
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowAppliedFilters(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity1));
        user1.setNewManagersAreActiveFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(false);
        assertFalse(user1.isFullyEqual(user2));
    }
}