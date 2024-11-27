package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.ItemShowSettings;
import github.ricemonger.utils.DTOs.ItemShownFieldsSettings;
import github.ricemonger.utils.DTOs.TelegramUser;
import github.ricemonger.utils.DTOs.TradeManagersSettings;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TelegramUserEntityTest {
    @Test
    public void toTelegramUser_should_properly_map_with_all_default_fields() {
        UserEntity user = new UserEntity();
        user.setPublicNotificationsEnabledFlag(true);
        user.setItemShowNameFlag(true);
        user.setItemShowItemTypeFlag(true);
        user.setItemShowMaxBuyPrice(true);
        user.setItemShowBuyOrdersCountFlag(true);
        user.setItemShowMinSellPriceFlag(true);
        user.setItemsShowSellOrdersCountFlag(true);
        user.setItemShowPictureFlag(true);
        user.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user.setNewManagersAreActiveFlag(true);
        user.setManagingEnabledFlag(true);

        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);
        entity.setInputState(InputState.BASE);
        entity.setInputGroup(InputGroup.BASE);
        entity.setItemShowMessagesLimit(50);
        entity.setItemShowFewInMessageFlag(false);

        TelegramUser expected = new TelegramUser();
        expected.setChatId("chatId");
        expected.setInputState(InputState.BASE);
        expected.setInputGroup(InputGroup.BASE);
        expected.setPublicNotificationsEnabledFlag(true);
        expected.setItemShowMessagesLimit(50);
        expected.setItemShowFewInMessageFlag(false);
        expected.setItemShowNameFlag(true);
        expected.setItemShowItemTypeFlag(true);
        expected.setItemShowMaxBuyPrice(true);
        expected.setItemShowBuyOrdersCountFlag(true);
        expected.setItemShowMinSellPriceFlag(true);
        expected.setItemsShowSellOrdersCountFlag(true);
        expected.setItemShowPictureFlag(true);
        expected.setItemShowAppliedFilters(List.of(new ItemFilterEntity().toItemFilter()));
        expected.setNewManagersAreActiveFlag(true);
        expected.setManagingEnabledFlag(true);

        TelegramUser actual = entity.toTelegramUser();

        assertEquals(expected, actual);
    }

    @Test
    public void toTelegramUser_should_properly_map_with_altered_fields() {
        ItemFilterEntity filter = new ItemFilterEntity();

        UserEntity user = new UserEntity();
        user.setPublicNotificationsEnabledFlag(false);
        user.setItemShowNameFlag(false);
        user.setItemShowItemTypeFlag(false);
        user.setItemShowMaxBuyPrice(false);
        user.setItemShowBuyOrdersCountFlag(false);
        user.setItemShowMinSellPriceFlag(false);
        user.setItemsShowSellOrdersCountFlag(false);
        user.setItemShowPictureFlag(false);
        user.setItemShowAppliedFilters(List.of(filter));
        user.setNewManagersAreActiveFlag(false);
        user.setManagingEnabledFlag(false);

        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);
        entity.setInputState(InputState.ITEM_FILTER_IS_OWNED);
        entity.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        entity.setItemShowMessagesLimit(25);
        entity.setItemShowFewInMessageFlag(true);

        TelegramUser expected = new TelegramUser();
        expected.setChatId("chatId");
        expected.setInputState(InputState.ITEM_FILTER_IS_OWNED);
        expected.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        expected.setPublicNotificationsEnabledFlag(false);
        expected.setItemShowMessagesLimit(25);
        expected.setItemShowFewInMessageFlag(true);
        expected.setItemShowNameFlag(false);
        expected.setItemShowItemTypeFlag(false);
        expected.setItemShowMaxBuyPrice(false);
        expected.setItemShowBuyOrdersCountFlag(false);
        expected.setItemShowMinSellPriceFlag(false);
        expected.setItemsShowSellOrdersCountFlag(false);
        expected.setItemShowPictureFlag(false);
        expected.setItemShowAppliedFilters(List.of(filter.toItemFilter()));
        expected.setNewManagersAreActiveFlag(false);
        expected.setManagingEnabledFlag(false);

        TelegramUser actual = entity.toTelegramUser();

        assertEquals(expected, actual);
    }

    @Test
    public void setShowItemFieldsSettings_should_update_false_fields() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        entity.setShowItemFieldsSettings(settings);

        assertFalse(user.getItemShowNameFlag());
        assertFalse(user.getItemShowItemTypeFlag());
        assertFalse(user.getItemShowMaxBuyPrice());
        assertFalse(user.getItemShowBuyOrdersCountFlag());
        assertFalse(user.getItemShowMinSellPriceFlag());
        assertFalse(user.getItemsShowSellOrdersCountFlag());
        assertFalse(user.getItemShowPictureFlag());
    }

    @Test
    public void setShowItemFieldsSettings_should_update_true_fields() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(true);
        settings.setItemShowItemTypeFlag(true);
        settings.setItemShowMaxBuyPrice(true);
        settings.setItemShowBuyOrdersCountFlag(true);
        settings.setItemShowMinSellPriceFlag(true);
        settings.setItemsShowSellOrdersCountFlag(true);
        settings.setItemShowPictureFlag(true);

        entity.setShowItemFieldsSettings(settings);

        assertTrue(user.getItemShowNameFlag());
        assertTrue(user.getItemShowItemTypeFlag());
        assertTrue(user.getItemShowMaxBuyPrice());
        assertTrue(user.getItemShowBuyOrdersCountFlag());
        assertTrue(user.getItemShowMinSellPriceFlag());
        assertTrue(user.getItemsShowSellOrdersCountFlag());
        assertTrue(user.getItemShowPictureFlag());
    }

    @Test
    public void toItemShowSettings_should_properly_map_with_default_fields() {
        UserEntity user = new UserEntity();
        user.setItemShowNameFlag(true);
        user.setItemShowItemTypeFlag(true);
        user.setItemShowMaxBuyPrice(true);
        user.setItemShowBuyOrdersCountFlag(true);
        user.setItemShowMinSellPriceFlag(true);
        user.setItemsShowSellOrdersCountFlag(true);
        user.setItemShowPictureFlag(true);
        user.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));

        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);
        entity.setItemShowMessagesLimit(50);
        entity.setItemShowFewInMessageFlag(false);

        ItemShowSettings expected = new ItemShowSettings();
        expected.setItemShowMessagesLimit(50);
        expected.setItemShowFewInMessageFlag(false);
        expected.setItemShowNameFlag(true);
        expected.setItemShowItemTypeFlag(true);
        expected.setItemShowMaxBuyPrice(true);
        expected.setItemShowBuyOrdersCountFlag(true);
        expected.setItemShowMinSellPriceFlag(true);
        expected.setItemsShowSellOrdersCountFlag(true);
        expected.setItemShowPictureFlag(true);
        expected.setItemShowAppliedFilters(List.of(new ItemFilterEntity().toItemFilter()));

        ItemShowSettings actual = entity.toItemShowSettings();

        assertEquals(expected, actual);
    }

    @Test
    public void toItemShowSettings_should_properly_map_with_altered_fields() {
        ItemFilterEntity filter = new ItemFilterEntity();

        UserEntity user = new UserEntity();
        user.setItemShowNameFlag(false);
        user.setItemShowItemTypeFlag(false);
        user.setItemShowMaxBuyPrice(false);
        user.setItemShowBuyOrdersCountFlag(false);
        user.setItemShowMinSellPriceFlag(false);
        user.setItemsShowSellOrdersCountFlag(false);
        user.setItemShowPictureFlag(false);
        user.setItemShowAppliedFilters(List.of(filter));

        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);
        entity.setItemShowMessagesLimit(25);
        entity.setItemShowFewInMessageFlag(true);

        ItemShowSettings expected = new ItemShowSettings();
        expected.setItemShowMessagesLimit(25);
        expected.setItemShowFewInMessageFlag(true);
        expected.setItemShowNameFlag(false);
        expected.setItemShowItemTypeFlag(false);
        expected.setItemShowMaxBuyPrice(false);
        expected.setItemShowBuyOrdersCountFlag(false);
        expected.setItemShowMinSellPriceFlag(false);
        expected.setItemsShowSellOrdersCountFlag(false);
        expected.setItemShowPictureFlag(false);
        expected.setItemShowAppliedFilters(List.of(filter.toItemFilter()));

        ItemShowSettings actual = entity.toItemShowSettings();

        assertEquals(expected, actual);
    }

    @Test
    public void setItemShowAppliedFilters_should_update_user_filters() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        List<ItemFilterEntity> filters = List.of(new ItemFilterEntity());

        entity.setItemShowAppliedFilters(filters);

        assertEquals(filters, user.getItemShowAppliedFilters());
    }

    @Test
    public void toTradeManagersSettings_should_properly_map_with_default_fields() {
        UserEntity user = new UserEntity();
        user.setNewManagersAreActiveFlag(true);
        user.setManagingEnabledFlag(true);

        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        TradeManagersSettings expected = new TradeManagersSettings();
        expected.setNewManagersAreActiveFlag(true);
        expected.setManagingEnabledFlag(true);

        TradeManagersSettings actual = entity.toTradeManagersSettings();

        assertEquals(expected, actual);
    }

    @Test
    public void toTradeManagersSettings_should_properly_map_with_altered_fields() {
        UserEntity user = new UserEntity();
        user.setNewManagersAreActiveFlag(false);
        user.setManagingEnabledFlag(false);

        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        TradeManagersSettings expected = new TradeManagersSettings();
        expected.setNewManagersAreActiveFlag(false);
        expected.setManagingEnabledFlag(false);

        TradeManagersSettings actual = entity.toTradeManagersSettings();

        assertEquals(expected, actual);
    }

    @Test
    public void setNewManagersAreActiveFlag_should_update_true_flag() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        entity.setNewManagersAreActiveFlag(true);

        assertTrue(user.getNewManagersAreActiveFlag());
    }

    @Test
    public void setNewManagersAreActiveFlag_should_update_false_flag() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        entity.setNewManagersAreActiveFlag(false);

        assertFalse(user.getNewManagersAreActiveFlag());
    }

    @Test
    public void setManagingEnabledFlag_should_update_true_flag() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        entity.setManagingEnabledFlag(true);

        assertTrue(user.getManagingEnabledFlag());
    }

    @Test
    public void setManagingEnabledFlag_should_update_false_flag() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        entity.setManagingEnabledFlag(false);

        assertFalse(user.getManagingEnabledFlag());
    }

    @Test
    public void setFields_should_update_default_fields() {
        UserEntity user = new UserEntity();
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("newChatId");
        telegramUser.setInputState(InputState.BASE);
        telegramUser.setInputGroup(InputGroup.BASE);
        telegramUser.setPublicNotificationsEnabledFlag(true);
        telegramUser.setItemShowMessagesLimit(50);
        telegramUser.setItemShowFewInMessageFlag(false);
        telegramUser.setItemShowNameFlag(true);
        telegramUser.setItemShowItemTypeFlag(true);
        telegramUser.setItemShowMaxBuyPrice(true);
        telegramUser.setItemShowBuyOrdersCountFlag(true);
        telegramUser.setItemShowMinSellPriceFlag(true);
        telegramUser.setItemsShowSellOrdersCountFlag(true);
        telegramUser.setItemShowPictureFlag(true);
        telegramUser.setItemShowAppliedFilters(List.of(new ItemFilterEntity().toItemFilter()));
        telegramUser.setNewManagersAreActiveFlag(true);
        telegramUser.setManagingEnabledFlag(true);

        entity.setFields(telegramUser);

        assertEquals("newChatId", entity.getChatId());
        assertEquals(InputState.BASE, entity.getInputState());
        assertEquals(InputGroup.BASE, entity.getInputGroup());
        assertTrue(user.getPublicNotificationsEnabledFlag());
        assertEquals(50, entity.getItemShowMessagesLimit());
        assertFalse(entity.getItemShowFewInMessageFlag());
        assertTrue(user.getItemShowNameFlag());
        assertTrue(user.getItemShowItemTypeFlag());
        assertTrue(user.getItemShowMaxBuyPrice());
        assertTrue(user.getItemShowBuyOrdersCountFlag());
        assertTrue(user.getItemShowMinSellPriceFlag());
        assertTrue(user.getItemsShowSellOrdersCountFlag());
        assertTrue(user.getItemShowPictureFlag());
        assertEquals(user.getItemShowAppliedFilters().stream().map(ItemFilterEntity::toItemFilter).toList(), telegramUser.getItemShowAppliedFilters());
        assertTrue(user.getNewManagersAreActiveFlag());
        assertTrue(user.getManagingEnabledFlag());
    }

    @Test
    public void setFields_should_update_altered_fields() {
        UserEntity user = new UserEntity();
        ItemFilterEntity filter = new ItemFilterEntity();
        List<ItemFilterEntity> filters = new ArrayList<>();
        filters.add(filter);
        user.setItemShowAppliedFilters(filters);
        TelegramUserEntity entity = new TelegramUserEntity("chatId", user);

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("newChatId");
        telegramUser.setInputState(InputState.ITEM_FILTER_IS_OWNED);
        telegramUser.setInputGroup(InputGroup.ITEMS_SHOW);
        telegramUser.setPublicNotificationsEnabledFlag(false);
        telegramUser.setItemShowMessagesLimit(25);
        telegramUser.setItemShowFewInMessageFlag(true);
        telegramUser.setItemShowNameFlag(false);
        telegramUser.setItemShowItemTypeFlag(false);
        telegramUser.setItemShowMaxBuyPrice(false);
        telegramUser.setItemShowBuyOrdersCountFlag(false);
        telegramUser.setItemShowMinSellPriceFlag(false);
        telegramUser.setItemsShowSellOrdersCountFlag(false);
        telegramUser.setItemShowPictureFlag(false);
        telegramUser.setItemShowAppliedFilters(List.of());
        telegramUser.setNewManagersAreActiveFlag(false);
        telegramUser.setManagingEnabledFlag(false);

        entity.setFields(telegramUser);

        assertEquals("newChatId", entity.getChatId());
        assertEquals(InputState.ITEM_FILTER_IS_OWNED, entity.getInputState());
        assertEquals(InputGroup.ITEMS_SHOW, entity.getInputGroup());
        assertFalse(user.getPublicNotificationsEnabledFlag());
        assertEquals(25, entity.getItemShowMessagesLimit());
        assertTrue(entity.getItemShowFewInMessageFlag());
        assertFalse(user.getItemShowNameFlag());
        assertFalse(user.getItemShowItemTypeFlag());
        assertFalse(user.getItemShowMaxBuyPrice());
        assertFalse(user.getItemShowBuyOrdersCountFlag());
        assertFalse(user.getItemShowMinSellPriceFlag());
        assertFalse(user.getItemsShowSellOrdersCountFlag());
        assertFalse(user.getItemShowPictureFlag());
        assertEquals(user.getItemShowAppliedFilters(), telegramUser.getItemShowAppliedFilters().stream().map(ItemFilterEntity::new).toList());
        assertFalse(user.getNewManagersAreActiveFlag());
        assertFalse(user.getManagingEnabledFlag());
    }
}