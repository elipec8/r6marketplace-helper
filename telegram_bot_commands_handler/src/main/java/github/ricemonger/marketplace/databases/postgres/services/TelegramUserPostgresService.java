package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserEntityMapper;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UserEntityMapper;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserPostgresService implements TelegramUserDatabaseService {

    private final TelegramUserPostgresRepository telegramUserRepository;

    private final TelegramUserEntityMapper telegramUserEntityMapper;

    private final ItemFilterPostgresEntity itemFilterPostgresRepository;

    private final UserEntityMapper userEntityMapper;

    private final UserPostgresRepository userRepository;

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
        return telegramUserRepository.findTelegramUserInputStateAndGroupByChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
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
        userRepository.updateItemShowFieldsSettingsByTelegramUserChatId(chatId, settings);
    }

    @Override
    @Transactional
    public void addUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
        userRepository.addItemShowAppliedFilterByTelegramUserChatId(chatId, itemFilterPostgresRepository.findByUserTelegramUserChatIdAndName(chatId, filterName).orElseThrow(() -> new ItemFilterDoesntExistException("Item filter with name " + filterName + " not found")));
    }

    @Override
    @Transactional
    public void removeUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
        userRepository.removeItemShowAppliedFilterByTelegramUserChatId(chatId, itemFilterPostgresRepository.findByUserTelegramUserChatIdAndName(chatId, filterName).orElseThrow(() -> new ItemFilterDoesntExistException("Item filter with name " + filterName + " not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllUserItemShowAppliedFiltersNames(String chatId) throws TelegramUserDoesntExistException {
        return userRepository.findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemShowSettings findUserItemShowSettings(String chatId) throws TelegramUserDoesntExistException {
        return userEntityMapper.mapItemShowSettings(userRepository.findItemShowSettingsByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found")));
    }

    @Override
    @Transactional
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        userRepository.updateNewManagersAreActiveFlagByTelegramUserChatId(chatId, flag);
    }

    @Override
    @Transactional
    public void setUserTradeManagersSettingsManagingEnabledFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        userRepository.updateTradeManagersManagingEnabledFlagByTelegramUserChatId(chatId, flag);
    }

    @Override
    @Transactional(readOnly = true)
    public TradeManagersSettings findUserTradeManagersSettings(String chatId) throws TelegramUserDoesntExistException {
        return userRepository.findTradeManagersSettingsByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
