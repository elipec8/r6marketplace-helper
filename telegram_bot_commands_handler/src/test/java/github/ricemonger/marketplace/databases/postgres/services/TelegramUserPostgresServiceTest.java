package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.custom.tg_user_input_group_and_state.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.custom.item_filters.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.custom.tg_user_input_group_and_state.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.ItemFilterEntityMapper;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserEntityMapper;
import github.ricemonger.marketplace.services.DTOs.*;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TelegramUserPostgresServiceTest {
    @Autowired
    private TelegramUserPostgresService telegramUserService;
    @MockBean
    private TelegramUserPostgresRepository telegramUserRepository;
    @MockBean
    private TelegramUserPostgresRepository inputStateAndGroupTelegramUserRepository;
    @MockBean
    private TelegramUserEntityMapper telegramUserEntityMapper;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;

    @Test
    public void create_should_register_new_telegram_user_if_doesnt_exist() {
        String chatId = "123";
        TelegramUserEntity entity = new TelegramUserEntity();
        when(telegramUserRepository.existsById(same(chatId))).thenReturn(false);
        when(telegramUserEntityMapper.createNewEntityForNewUser(same(chatId))).thenReturn(entity);

        telegramUserService.register(chatId);

        verify(telegramUserRepository).save(same(entity));
    }

    @Test
    public void register_should_throw_if_telegram_user_already_exists() {
        String chatId = "123";
        when(telegramUserRepository.existsById(same(chatId))).thenReturn(true);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramUserService.register(chatId));
    }

    @Test
    public void update_should_set_fields_and_save_telegram_user_from_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("chatId");
        telegramUserService.update(telegramUser);

        verify(telegramUserEntity).setFields(same(telegramUser), same(itemFilterEntityMapper));

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void update_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.update(new TelegramUser()));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_set_User_flag_and_save_telegram_user_from_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        telegramUserService.setUserItemShowFewItemsInMessageFlag("chatId", true);

        verify(telegramUserEntity).setItemShowFewInMessageFlag(true);

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void setUserItemShowFewItemsInMessageFlag_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserItemShowFewItemsInMessageFlag("chatId", true));
    }

    @Test
    public void setItemShowMessagesLimit_should_set_User_limit_and_save_telegram_user_from_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        telegramUserService.setUserItemShowMessagesLimit("chatId", 5);

        verify(telegramUserEntity).setItemShowMessagesLimit(5);

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void setUserItemShowMessagesLimit_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserItemShowMessagesLimit("chatId", 5));
    }

    @Test
    public void setItemShowFieldsSettings_should_set_User_settings_and_save_telegram_user_from_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();

        telegramUserService.setUserItemShowFieldsSettings("chatId", settings);

        verify(telegramUserEntity).setShowItemFieldsSettings(same(settings));

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void setUserItemShowFieldsSettings_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserItemShowFieldsSettings("chatId", new ItemShownFieldsSettings()));
    }

    @Test
    public void addItemShowAppliedFilter_should_add_filter_to_user_itemShowAppliedFilters_list_and_save_to_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));
        when(telegramUserEntity.getUser()).thenReturn(userEntity);

        List filtersMock = Mockito.mock(List.class);

        when(telegramUserEntity.getItemShowAppliedFilters()).thenReturn(filtersMock);

        ItemFilter filter = new ItemFilter();

        ItemFilterEntity filterEntity = new ItemFilterEntity();
        when(itemFilterEntityMapper.createEntityForUser(same(userEntity), same(filter))).thenReturn(filterEntity);

        telegramUserService.addItemShowAppliedFilter("chatId", filter);

        verify(filtersMock).add(filterEntity);

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void addItemShowAppliedFilter_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addItemShowAppliedFilter("chatId", new ItemFilter()));
    }

    @Test
    public void removeItemShowAppliedFilter_should_remove_filter_from_user_User_itemShowAppliedFilters_list_and_save_to_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        List filtersMock = Mockito.mock(List.class);
        when(telegramUserEntity.getItemShowAppliedFilters()).thenReturn(filtersMock);

        ItemFilterEntity filterEntity = new ItemFilterEntity();
        filterEntity.setName("filterName");

        when(filtersMock.size()).thenReturn(1);
        when(filtersMock.get(anyInt())).thenReturn(filterEntity);

        telegramUserService.removeUserItemShowAppliedFilter("chatId", "filterName");

        verify(filtersMock).remove(anyInt());

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void removeUserItemShowAppliedFilter_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.removeUserItemShowAppliedFilter("chatId", "filterName"));
    }

    @Test
    public void setTradeManagersSettingsNewManagersAreActiveFlag_should_set_User_flag_and_save_telegram_user_from_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        telegramUserService.setUserTradeManagersSettingsNewManagersAreActiveFlag("chatId", true);

        verify(telegramUserEntity).setNewManagersAreActiveFlag_(true);

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserTradeManagersSettingsNewManagersAreActiveFlag("chatId", true));
    }

    @Test
    public void get_should_return_telegram_user_from_db() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        telegramUserService.setUserTradeManagersSettingsManagingEnabledFlag("chatId", true);

        verify(telegramUserEntity).setManagingEnabledFlag_(true);

        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void setUserTradeManagersSettingsManagingEnabledFlag_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.setUserTradeManagersSettingsManagingEnabledFlag("chatId", true));
    }

    @Test
    public void existsById_should_return_true_if_user_exists() {
        when(telegramUserRepository.existsById("chatId")).thenReturn(true);

        assertTrue(telegramUserService.isRegistered("chatId"));
    }

    @Test
    public void isRegistered_should_return_true_if_user_doesnt_exist() {
        when(telegramUserRepository.existsById("chatId")).thenReturn(false);

        assertFalse(telegramUserService.isRegistered("chatId"));
    }

    @Test
    public void findUserById_should_return_telegram_user() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        TelegramUser telegramUser = new TelegramUser();
        when(telegramUserEntityMapper.createTelegramUser(same(telegramUserEntity))).thenReturn(telegramUser);

        assertSame(telegramUser, telegramUserService.findUserById("chatId"));
    }

    @Test
    public void findUserById_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.findUserById("chatId"));
    }

    @Test
    public void findUserInputStateAndGroupById_should_return_mapped_repository_result() {
        TelegramUserEntity entity = Mockito.mock(TelegramUserEntity.class);

        when(inputStateAndGroupTelegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(entity));

        TelegramUserInputStateAndGroup dto = Mockito.mock(TelegramUserInputStateAndGroup.class);

        when(telegramUserEntityMapper.createInputStateAndGroupDTO(same(entity))).thenReturn(dto);

        assertSame(dto, telegramUserService.findUserInputStateAndGroupById("chatId"));
    }

    @Test
    public void get_entity() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        ItemShowSettings itemShowSettings = new ItemShowSettings();
        when(telegramUserEntityMapper.createItemShowSettings(same(telegramUserEntity))).thenReturn(itemShowSettings);

        assertSame(itemShowSettings, telegramUserService.findUserItemShowSettings("chatId"));
    }

    @Test
    public void get_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.findUserItemShowSettings("chatId"));
    }

    @Test
    public void findUserTradeManagersSettingsById_should_return_telegram_user_entity() {
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        when(telegramUserRepository.findById("chatId")).thenReturn(java.util.Optional.of(telegramUserEntity));

        TradeManagersSettings tradeManagersSettings = new TradeManagersSettings();
        when(telegramUserEntityMapper.createTradeManagersSettings(same(telegramUserEntity))).thenReturn(tradeManagersSettings);

        assertSame(tradeManagersSettings, telegramUserService.findUserTradeManagersSettings("chatId"));
    }

    @Test
    public void findUserTradeManagersSettingsById_should_throw_if_user_doesnt_exist() {
        when(telegramUserRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.findUserTradeManagersSettings("chatId"));
    }
}