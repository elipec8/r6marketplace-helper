package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.custom.settings.service.SettingsMapper;
import github.ricemonger.marketplace.databases.postgres.custom.settings.service.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.custom.settings.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.custom.settings.entities.UserEntity;
import github.ricemonger.marketplace.databases.postgres.custom.settings.service.UserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserEntityMapper;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserPostgresService implements TelegramUserDatabaseService {

    private final TelegramUserPostgresRepository telegramUserRepository;

    private final UserPostgresRepository userSettingsRepository;

    private final github.ricemonger.marketplace.databases.postgres.custom.tg_user_input_group_and_state.TelegramUserPostgresRepository inputStateAndGroupTelegramUserRepository;

    private final TelegramUserEntityMapper telegramUserEntityMapper;

    private final SettingsMapper settingsMapper;

    @Override
    @Transactional
    public void register(String chatId) throws TelegramUserAlreadyExistsException {
        if (telegramUserRepository.existsById(chatId)) {
            throw new TelegramUserAlreadyExistsException("Telegram user with chatId " + chatId + " already exists");
        } else {
            telegramUserRepository.save(telegramUserEntityMapper.createNewEntityForNewUser(chatId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRegistered(String chatId) {
        return telegramUserRepository.existsById(chatId);
    }

    @Override
    @Transactional
    public void setUserInputGroup(String chatId, InputGroup inputGroup) {
        telegramUserRepository.updateInputGroup(chatId, inputGroup);
    }

    @Override
    @Transactional
    public void setUserInputState(String chatId, InputState inputState) {
        telegramUserRepository.updateInputState(chatId, inputState);
    }

    @Override
    @Transactional
    public void setUserInputStateAndGroup(String chatId, InputState inputState, InputGroup inputGroup) {
        telegramUserRepository.updateInputStateAndGroup(chatId, inputState, inputGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public TelegramUserInputStateAndGroup findUserInputStateAndGroupById(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createInputStateAndGroupDTO(inputStateAndGroupTelegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found")));
    }

    @Override
    @Transactional
    public void setUserItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        telegramUserRepository.updateItemShowFewItemsInMessageFlag(chatId, flag);
    }

    @Override
    @Transactional
    public void setUserItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException {
        telegramUserRepository.updateItemShowMessagesLimit(chatId, limit);
    }

    @Override
    @Transactional
    public void setUserItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException {
        userSettingsRepository.updateItemShowFieldsSettingsByTelegramUserChatId(chatId, settings);
    }

    @Override
    @Transactional
    public void addUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
        UserEntity userSettings = userSettingsRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
        userSettings.getItemShowAppliedFilters().add(new ItemFilterEntity(userSettings, filterName));
        userSettingsRepository.save(userSettings);
    }

    @Override
    @Transactional
    public void removeUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
        UserEntity userSettings = userSettingsRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
        userSettings.getItemShowAppliedFilters().removeIf(itemFilterIdEntity -> itemFilterIdEntity.getName().equals(filterName));
        userSettingsRepository.save(userSettings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllUserItemShowAppliedFiltersNames(String chatId) throws TelegramUserDoesntExistException {
        return userSettingsRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found")).getItemShowAppliedFilters().stream().map(ItemFilterEntity::getName).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ItemShowSettings findUserItemShowSettings(String chatId) throws TelegramUserDoesntExistException {
        return settingsMapper.createItemShowSettingsDTO(userSettingsRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found")));
    }

    @Override
    @Transactional
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        userSettingsRepository.updateNewManagersAreActiveFlagByTelegramUserChatId(chatId, flag);
    }

    @Override
    @Transactional
    public void setUserTradeManagersSettingsManagingEnabledFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        userSettingsRepository.updateTradeManagersManagingEnabledFlagByTelegramUserChatId(chatId, flag);
    }

    @Override
    @Transactional(readOnly = true)
    public TradeManagersSettings findUserTradeManagersSettings(String chatId) throws TelegramUserDoesntExistException {
        return settingsMapper.createTradeManagersSettingsDTO(userSettingsRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found")));
    }
}
