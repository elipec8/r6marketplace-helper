package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramUserEntityTest {

    //@Test
    public void toTelegramUser_should_properly_map_with_valid_fields() {
        ItemFilterEntity itemFilterEntity = new ItemFilterEntity();
        itemFilterEntity.setName("name");
        TradeByItemIdManagerEntity tradeByItemIdManagerEntity = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity.setItemId("1");
        TradeByFiltersManagerEntity tradeByFiltersManagerEntity = new TradeByFiltersManagerEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setPublicNotificationsEnabledFlag(true);
        userEntity.setItemShowNameFlag(true);
        userEntity.setItemShowItemTypeFlag(true);
        userEntity.setItemShowMaxBuyPrice(true);
        userEntity.setItemShowBuyOrdersCountFlag(true);
        userEntity.setItemShowMinSellPriceFlag(true);
        userEntity.setItemsShowSellOrdersCountFlag(true);
        userEntity.setItemShowPictureFlag(true);
        userEntity.setItemShowAppliedFilters(List.of(itemFilterEntity));
        userEntity.setActiveTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity));


        TelegramUserEntity entity = new TelegramUserEntity();
        entity.setChatId("chatId");
        entity.setInputState(InputState.ITEMS_SHOW_OFFSET);
        entity.setInputGroup(InputGroup.ITEMS_SHOW);
        entity.setItemShowMessagesLimit(1);
        entity.setItemShowFewInMessageFlag(true);
        entity.setUser(userEntity);

        TelegramUser expected = new TelegramUser();
        expected.setChatId("chatId");
        expected.setInputState(InputState.ITEMS_SHOW_OFFSET);
        expected.setInputGroup(InputGroup.ITEMS_SHOW);
        expected.setPublicNotificationsEnabledFlag(true);
        expected.setItemShowMessagesLimit(1);
        expected.setItemShowFewInMessageFlag(true);
        expected.setItemShowNameFlag(true);
        expected.setItemShowItemTypeFlag(true);
        expected.setItemShowMaxBuyPrice(true);
        expected.setItemShowBuyOrdersCountFlag(true);
        expected.setItemShowMinSellPriceFlag(true);
        expected.setItemsShowSellOrdersCountFlag(true);
        expected.setItemShowPictureFlag(true);
        expected.setItemShowAppliedFilters(List.of(itemFilterEntity.toItemFilter()));

        TelegramUser actual = entity.toTelegramUser();

        assertEquals(expected, actual);
    }
}