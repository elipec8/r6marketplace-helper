package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        user1.setItemFilters(List.of(new ItemFilterEntity()));
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
        user1.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
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

        ItemFilterEntity itemFilterEntity1 = new ItemFilterEntity();
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

        ItemFilterEntity itemFilterEntity2 = new ItemFilterEntity();
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
        TradeByFiltersManagerEntity tradeByFiltersManagerEntity = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity.setUser(new UserEntity(1L));
        tradeByFiltersManagerEntity.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity.setUser(new UserEntity(1L));
        tradeByItemIdManagerEntity.setItem(new ItemEntity("itemId1"));

        ItemFilterEntity itemFilterEntity = new ItemFilterEntity();
        itemFilterEntity.setUser(new UserEntity(1L));
        itemFilterEntity.setName("name");

        TelegramUserEntity telegramUser = new TelegramUserEntity("chatId", 1L);

        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity(1L, "email", "ubiProfileId");

        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(telegramUser);
        user1.setUbiAccountEntry(ubiAccountEntryEntity);
        user1.setItemFilters(List.of(itemFilterEntity));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity));
        user1.setPublicNotificationsEnabledFlag(true);
        user1.setPrivateNotificationsEnabledFlag(true);
        user1.setItemShowNameFlag(true);
        user1.setItemShowItemTypeFlag(true);
        user1.setItemShowMaxBuyPrice(true);
        user1.setItemShowBuyOrdersCountFlag(true);
        user1.setItemShowMinSellPriceFlag(true);
        user1.setItemsShowSellOrdersCountFlag(true);
        user1.setItemShowPictureFlag(true);
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(true);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setTelegramUser(telegramUser);
        user2.setUbiAccountEntry(ubiAccountEntryEntity);
        user2.setItemFilters(List.of(itemFilterEntity));
        user2.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity));
        user2.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity));
        user2.setPublicNotificationsEnabledFlag(true);
        user2.setPrivateNotificationsEnabledFlag(true);
        user2.setItemShowNameFlag(true);
        user2.setItemShowItemTypeFlag(true);
        user2.setItemShowMaxBuyPrice(true);
        user2.setItemShowBuyOrdersCountFlag(true);
        user2.setItemShowMinSellPriceFlag(true);
        user2.setItemsShowSellOrdersCountFlag(true);
        user2.setItemShowPictureFlag(true);
        user2.setItemShowAppliedFilters(List.of(itemFilterEntity));
        user2.setNewManagersAreActiveFlag(true);
        user2.setManagingEnabledFlag(true);

        user1.setId(2L);
        assertFalse(user1.isFullyEqual(user2));
        user1.setId(1L);
        user1.setTelegramUser(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTelegramUser(telegramUser);
        user1.setUbiAccountEntry(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setUbiAccountEntry(ubiAccountEntryEntity);
        user1.setItemFilters(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setItemFilters(List.of(itemFilterEntity));
        user1.setTradeByFiltersManagers(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity));
        user1.setTradeByItemIdManagers(null);
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity));
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
        user1.setItemShowAppliedFilters(List.of(itemFilterEntity));
        user1.setNewManagersAreActiveFlag(false);
        assertFalse(user1.isFullyEqual(user2));
        user1.setNewManagersAreActiveFlag(true);
        user1.setManagingEnabledFlag(false);
        assertFalse(user1.isFullyEqual(user2));
    }
}