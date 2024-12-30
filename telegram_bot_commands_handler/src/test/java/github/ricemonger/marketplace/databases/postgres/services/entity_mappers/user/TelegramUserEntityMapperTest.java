package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_group_and_state.InputStateAndGroupTelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.tg_user_input_group_and_state.UserIdEntity;
import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_item_filter_service.ItemFilterUserIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.*;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserEntityMapperTest {
    @Autowired
    private TelegramUserEntityMapper telegramUserEntityMapper;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;
    @MockBean
    private UserPostgresRepository userRepository;

    @Test
    public void createNewEntityForNewUser_should_create_and_return_new_entity_and_create_new_user() {
        UserEntity userEntity = new UserEntity(1L);
        doReturn(userEntity).when(userRepository).save(any(UserEntity.class));

        TelegramUserEntity entity = telegramUserEntityMapper.createNewEntityForNewUser("chatId");

        assertEquals("chatId", entity.getChatId());
        assertSame(userEntity, entity.getUser());
    }

    @Test
    public void createTelegramUser_should_return_telegram_user() {
        TelegramUserEntity entity = new TelegramUserEntity();
        entity.setUser(new UserEntity(1L));
        entity.setChatId("chatId");
        entity.setInputState(InputState.ITEM_FILTER_NAME);
        entity.setInputGroup(InputGroup.ITEMS_SHOW);
        entity.getUser().setPublicNotificationsEnabledFlag(true);
        entity.setItemShowMessagesLimit(1);
        entity.setItemShowFewInMessageFlag(true);
        entity.getUser().setItemShowNameFlag(false);
        entity.getUser().setItemShowItemTypeFlag(false);
        entity.getUser().setItemShowMaxBuyPrice(true);
        entity.getUser().setItemShowBuyOrdersCountFlag(false);
        entity.getUser().setItemShowMinSellPriceFlag(true);
        entity.getUser().setItemsShowSellOrdersCountFlag(false);
        entity.getUser().setItemShowPictureFlag(true);
        entity.getUser().setItemShowAppliedFilters(List.of(new ItemFilterUserIdEntity()));
        entity.getUser().setNewManagersAreActiveFlag(true);
        entity.getUser().setManagingEnabledFlag(false);

        TelegramUser expected = new TelegramUser();
        expected.setChatId("chatId");
        expected.setInputState(InputState.ITEM_FILTER_NAME);
        expected.setInputGroup(InputGroup.ITEMS_SHOW);
        expected.setPublicNotificationsEnabledFlag(true);
        expected.setItemShowMessagesLimit(1);
        expected.setItemShowFewInMessageFlag(true);
        expected.setItemShowNameFlag(false);
        expected.setItemShowItemTypeFlag(false);
        expected.setItemShowMaxBuyPrice(true);
        expected.setItemShowBuyOrdersCountFlag(false);
        expected.setItemShowMinSellPriceFlag(true);
        expected.setItemsShowSellOrdersCountFlag(false);
        expected.setItemShowPictureFlag(true);
        expected.setItemShowAppliedFilters(List.of(new ItemFilter()));
        expected.setNewManagersAreActiveFlag(true);
        expected.setManagingEnabledFlag(false);

        when(itemFilterEntityMapper.createDTO(any())).thenReturn(new ItemFilter());

        TelegramUser actual = telegramUserEntityMapper.createTelegramUser(entity);

        assertEquals(expected, actual);
    }

    @Test
    public void createItemShowSettings_should_return_item_show_settings() {
        TelegramUserEntity entity = new TelegramUserEntity();
        entity.setUser(new UserEntity());
        entity.getUser().setItemShowNameFlag(true);
        entity.getUser().setItemShowItemTypeFlag(false);
        entity.getUser().setItemShowMaxBuyPrice(true);
        entity.getUser().setItemShowBuyOrdersCountFlag(false);
        entity.getUser().setItemShowMinSellPriceFlag(true);
        entity.getUser().setItemsShowSellOrdersCountFlag(false);
        entity.getUser().setItemShowPictureFlag(true);
        entity.setItemShowMessagesLimit(1);
        entity.setItemShowFewInMessageFlag(true);
        entity.getUser().setItemShowAppliedFilters(List.of());

        ItemShowSettings expected = new ItemShowSettings();
        expected.setItemShowMessagesLimit(1);
        expected.setItemShowFewInMessageFlag(true);
        expected.setShownFieldsSettings(new ItemShownFieldsSettings(true, false, true, false, true, false, true));
        expected.setItemShowAppliedFilters(List.of());

        ItemShowSettings itemShowSettings = telegramUserEntityMapper.createItemShowSettings(entity);

        assertEquals(expected, itemShowSettings);
    }

    @Test
    public void createTradeManagersSettings_should_return_trade_managers_settings() {
        TelegramUserEntity entity = new TelegramUserEntity();
        entity.setUser(new UserEntity());
        entity.getUser().setNewManagersAreActiveFlag(true);
        entity.getUser().setManagingEnabledFlag(true);

        TradeManagersSettings tradeManagersSettings = telegramUserEntityMapper.createTradeManagersSettings(entity);

        assertTrue(tradeManagersSettings.getNewManagersAreActiveFlag());
        assertTrue(tradeManagersSettings.getManagingEnabledFlag());

        entity.getUser().setNewManagersAreActiveFlag(false);
        entity.getUser().setManagingEnabledFlag(false);

        tradeManagersSettings = telegramUserEntityMapper.createTradeManagersSettings(entity);

        assertFalse(tradeManagersSettings.getNewManagersAreActiveFlag());
        assertFalse(tradeManagersSettings.getManagingEnabledFlag());
    }

    @Test
    public void createInputStateAndGroupDTO_should_return_input_state_and_group_dto() {
        InputStateAndGroupTelegramUserEntity entity = new InputStateAndGroupTelegramUserEntity();
        entity.setUser(new UserIdEntity(1L));
        entity.setChatId("chatId");
        entity.setInputState(InputState.ITEM_FILTER_NAME);
        entity.setInputGroup(InputGroup.ITEMS_SHOW);

        TelegramUserInputStateAndGroup expected = new TelegramUserInputStateAndGroup();
        expected.setUserId(1L);
        expected.setChatId("chatId");
        expected.setInputState(InputState.ITEM_FILTER_NAME);
        expected.setInputGroup(InputGroup.ITEMS_SHOW);

        assertEquals(expected, telegramUserEntityMapper.createInputStateAndGroupDTO(entity));
    }
}