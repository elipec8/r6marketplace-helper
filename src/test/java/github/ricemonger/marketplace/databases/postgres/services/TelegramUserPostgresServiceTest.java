package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.ItemShownFieldsSettings;
import github.ricemonger.utils.DTOs.TelegramUser;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelegramUserPostgresServiceTest {
    private final static String CHAT_ID = "1";
    private final static String ANOTHER_CHAT_ID = "2";

    @Autowired
    private TelegramUserPostgresService telegramUserService;
    @Autowired
    private ItemFilterPostgresRepository itemFilterRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        telegramUserRepository.deleteAll();
        userRepository.deleteAll();
        itemFilterRepository.deleteAll();
    }

    private TelegramUserEntity createTelegramUser(String chatId) {
        UserEntity user = userRepository.save(new UserEntity());
        return telegramUserRepository.save(new TelegramUserEntity(chatId, user));
    }

    @Test
    public void create_should_create_new_user_with_default_settings() {
        telegramUserService.create(CHAT_ID);

        assertEquals(1, userRepository.count());

        TelegramUserEntity telegramUser = telegramUserRepository.findById(CHAT_ID).get();

        TelegramUser expected = new TelegramUser();
        expected.setChatId(CHAT_ID);

        expected.setInputState(InputState.BASE);
        expected.setInputGroup(InputGroup.BASE);

        expected.setItemShowMessagesLimit(50);
        expected.setItemShowFewInMessageFlag(false);

        expected.setPublicNotificationsEnabledFlag(true);
        expected.setItemShowNameFlag(true);
        expected.setItemShowItemTypeFlag(true);
        expected.setItemShowMaxBuyPrice(true);
        expected.setItemShowBuyOrdersCountFlag(true);
        expected.setItemShowMinSellPriceFlag(true);
        expected.setItemsShowSellOrdersCountFlag(true);
        expected.setItemShowPictureFlag(true);
        expected.setItemShowAppliedFilters(new ArrayList<>());
        expected.setManagingEnabledFlag(true);
        expected.setNewManagersAreActiveFlag(true);

        assertEquals(telegramUser.toTelegramUser(), expected);
    }

    @Test
    public void create_should_throw_if_telegram_user_already_exists() {
        createTelegramUser(CHAT_ID);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramUserService.create(CHAT_ID));
    }

    @Test
    @Transactional
    public void applied_filters_clear_should_not_remove_item_filter_from_db() {
        UserEntity user = userRepository.save(new UserEntity());
        TelegramUserEntity telegramUser = telegramUserRepository.save(new TelegramUserEntity(CHAT_ID, user));

        ItemFilter filter = new ItemFilter();
        filter.setName("filter1");

        ItemFilterEntity filterEntity = itemFilterRepository.save(new ItemFilterEntity(user, filter));

        telegramUser.getItemShowAppliedFilters().add(filterEntity);
        telegramUserRepository.save(telegramUser);

        telegramUser.getItemShowAppliedFilters().clear();
        telegramUserRepository.save(telegramUser);

        assertEquals(1, itemFilterRepository.count());
        assertEquals(0, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());
    }

    @Test
    public void update_should_update_fields_of_existing_telegram_user() {
        createTelegramUser(CHAT_ID);

        TelegramUser updated = new TelegramUser();
        updated.setChatId(CHAT_ID);

        updated.setInputState(InputState.UBI_ACCOUNT_ENTRY_PASSWORD);
        updated.setInputGroup(InputGroup.UBI_ACCOUNT_ENTRY_LINK);

        updated.setItemShowMessagesLimit(5);
        updated.setItemShowFewInMessageFlag(true);

        updated.setPublicNotificationsEnabledFlag(true);
        updated.setItemShowNameFlag(false);
        updated.setItemShowItemTypeFlag(false);
        updated.setItemShowMaxBuyPrice(false);
        updated.setItemShowBuyOrdersCountFlag(false);
        updated.setItemShowMinSellPriceFlag(false);
        updated.setItemsShowSellOrdersCountFlag(false);
        updated.setItemShowPictureFlag(false);
        updated.setItemShowAppliedFilters(new ArrayList<>());
        updated.setManagingEnabledFlag(false);
        updated.setNewManagersAreActiveFlag(false);

        telegramUserService.update(updated);

        assertEquals(1, telegramUserRepository.count());
        assertEquals(updated, telegramUserRepository.findById(CHAT_ID).get().toTelegramUser());
    }

    @Test
    public void update_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.update(new TelegramUser(1L)));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_update_flag() {
        createTelegramUser(CHAT_ID);

        telegramUserService.setItemShowFewItemsInMessageFlag(CHAT_ID, false);
        assertEquals(false, telegramUserRepository.findById(CHAT_ID).get().getItemShowFewInMessageFlag());

        telegramUserService.setItemShowFewItemsInMessageFlag(CHAT_ID, true);
        assertEquals(true, telegramUserRepository.findById(CHAT_ID).get().getItemShowFewInMessageFlag());
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowFewItemsInMessageFlag(CHAT_ID, false));
    }

    @Test
    public void setItemShowMessagesLimit_should_update_limit() {
        createTelegramUser(CHAT_ID);

        telegramUserService.setItemShowMessagesLimit(CHAT_ID, 5);
        assertEquals(5, telegramUserRepository.findById(CHAT_ID).get().getItemShowMessagesLimit());

        telegramUserService.setItemShowMessagesLimit(CHAT_ID, 10);
        assertEquals(10, telegramUserRepository.findById(CHAT_ID).get().getItemShowMessagesLimit());
    }

    @Test
    public void setItemShowMessagesLimit_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowMessagesLimit(CHAT_ID, 5));
    }

    @Test
    public void setItemShowFieldsSettings_should_update_settings() {
        createTelegramUser(CHAT_ID);

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        telegramUserService.setItemShowFieldsSettings(CHAT_ID, settings);
        assertEquals(settings, telegramUserRepository.findById(CHAT_ID).get().toItemShowSettings().getShownFieldsSettings());

        settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(true);
        settings.setItemShowItemTypeFlag(true);
        settings.setItemShowMaxBuyPrice(true);
        settings.setItemShowBuyOrdersCountFlag(true);
        settings.setItemShowMinSellPriceFlag(true);
        settings.setItemsShowSellOrdersCountFlag(true);
        settings.setItemShowPictureFlag(true);

        telegramUserService.setItemShowFieldsSettings(CHAT_ID, settings);
        assertEquals(settings, telegramUserRepository.findById(CHAT_ID).get().toItemShowSettings().getShownFieldsSettings());
    }

    @Test
    public void setItemShowFieldsSettings_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setItemShowFieldsSettings(CHAT_ID, new ItemShownFieldsSettings()));
    }

    @Test
    public void addItemShowAppliedFilter_should_add_filter() {
        createTelegramUser(CHAT_ID);

        ItemFilter filter = new ItemFilter();
        filter.setName("filter1");

        telegramUserService.addItemShowAppliedFilter(CHAT_ID, filter);
        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());

        filter = new ItemFilter();
        filter.setName("filter2");

        telegramUserService.addItemShowAppliedFilter(CHAT_ID, filter);
        assertEquals(2, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());
    }

    @Test
    public void addItemShowAppliedFilter_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addItemShowAppliedFilter(CHAT_ID, new ItemFilter()));
    }

    @Test
    public void removeItemShowAppliedFilter_should_remove_filter_and_remove_from_db_if_orphaned() {
        createTelegramUser(CHAT_ID);

        ItemFilter filter = new ItemFilter();
        filter.setName("filter1");

        telegramUserService.addItemShowAppliedFilter(CHAT_ID, filter);
        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());
        assertEquals(1, itemFilterRepository.count());

        telegramUserService.removeItemShowAppliedFilter(CHAT_ID, "filter1");
        assertEquals(0, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());
        assertEquals(0, itemFilterRepository.count());
    }

    @Test
    public void removeItemShowAppliedFilter_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeItemShowAppliedFilter(CHAT_ID, "filter1"));
    }

    @Test
    public void removeItemShowAppliedFilter_should_not_remove_filter_if_it_doesnt_exist() {
        createTelegramUser(CHAT_ID);

        ItemFilter filter = new ItemFilter();
        filter.setName("filter1");

        telegramUserService.addItemShowAppliedFilter(CHAT_ID, filter);
        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());

        telegramUserService.removeItemShowAppliedFilter(CHAT_ID, "filter2");
        assertEquals(1, telegramUserRepository.findById(CHAT_ID).get().getItemShowAppliedFilters().size());
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_update_flag() {
        createTelegramUser(CHAT_ID);

        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(CHAT_ID, false);
        assertFalse(telegramUserRepository.findById(CHAT_ID).get().toTradeManagersSettings().isNewManagersAreActiveFlag());

        telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(CHAT_ID, true);
        assertTrue(telegramUserRepository.findById(CHAT_ID).get().toTradeManagersSettings().isNewManagersAreActiveFlag());
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setTradeManagersSettingsNewManagersAreActiveFlag(CHAT_ID, false));
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_update_flag() {
        createTelegramUser(CHAT_ID);

        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(CHAT_ID, false);
        assertFalse(telegramUserRepository.findById(CHAT_ID).get().toTradeManagersSettings().isManagingEnabledFlag());

        telegramUserService.setTradeManagersSettingsManagingEnabledFlag(CHAT_ID, true);
        assertTrue(telegramUserRepository.findById(CHAT_ID).get().toTradeManagersSettings().isManagingEnabledFlag());
    }

    @Test
    public void setTradeManagersSettingsManagingEnabledFlag_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setTradeManagersSettingsManagingEnabledFlag(CHAT_ID, false));
    }

    @Test
    public void existsById_should_return_true_if_telegram_user_exists() {
        createTelegramUser(CHAT_ID);

        assertTrue(telegramUserService.existsById(CHAT_ID));
    }

    @Test
    public void existsById_should_return_false_if_telegram_user_doesnt_exist() {
        assertFalse(telegramUserService.existsById(CHAT_ID));
    }

    @Test
    public void findUserById_should_return_telegram_user() {
        createTelegramUser(CHAT_ID);

        assertEquals(telegramUserRepository.findById(CHAT_ID).get().toTelegramUser(), telegramUserService.findUserById(CHAT_ID));
    }

    @Test
    public void findUserById_should_throw_if_telegram_user_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.findUserById(CHAT_ID));
    }

    @Test
    public void findUserItemShowSettingsById_should_return_item_show_ItemShow_settings() {
        createTelegramUser(CHAT_ID);

        assertEquals(telegramUserRepository.findById(CHAT_ID).get().toItemShowSettings(), telegramUserService.findUserItemShowSettingsById(CHAT_ID));
    }

    @Test
    public void findUserItemShowSettingsById_should_throw_if_telegram_user_ItemShow_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.findUserItemShowSettingsById(CHAT_ID));
    }

    @Test
    public void findUserTradeManagersSettingsById_should_return_user_trade_managers_settings() {
        createTelegramUser(CHAT_ID);

        assertEquals(telegramUserRepository.findById(CHAT_ID).get().toTradeManagersSettings(), telegramUserService.findUserTradeManagersSettingsById(CHAT_ID));
    }

    @Test
    public void findUserTradeManagersSettingsById_should_throw_if_telegram_user_ItemShow_doesnt_exist() {
        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.findUserTradeManagersSettingsById(CHAT_ID));
    }

    @Test
    public void finAllUsers_should_return_all_telegram_users() {
        createTelegramUser(CHAT_ID);
        createTelegramUser(ANOTHER_CHAT_ID);

        assertEquals(2, telegramUserService.findAllUsers().size());
    }

    @Test
    public void finAllUsers_should_return_empty_list_if_no_telegram_users() {
        assertEquals(0, telegramUserService.findAllUsers().size());
    }
}