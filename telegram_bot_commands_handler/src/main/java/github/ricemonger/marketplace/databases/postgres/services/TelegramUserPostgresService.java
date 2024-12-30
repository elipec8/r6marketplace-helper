package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_group_and_state.InputStateAndGroupTelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserPostgresService implements TelegramUserDatabaseService {

    private final TelegramUserPostgresRepository telegramUserRepository;

    private final

    private final InputStateAndGroupTelegramUserPostgresRepository inputStateAndGroupTelegramUserRepository;

    private final TelegramUserEntityMapper telegramUserEntityMapper;

    @Override
    public void register(String chatId) throws TelegramUserAlreadyExistsException {
        if (telegramUserRepository.existsById(chatId)) {
            throw new TelegramUserAlreadyExistsException("Telegram user with chatId " + chatId + " already exists");
        } else {
            telegramUserRepository.save(telegramUserEntityMapper.createNewEntityForNewUser(chatId));
        }
    }

    @Override
    public boolean isRegistered(String chatId) {
        return telegramUserRepository.existsById(chatId);
    }

    @Override
    public void setUserInputGroup(String chatId, InputGroup inputGroup) {
        telegramUserRepository.updateInputGroup(chatId, inputGroup);
    }

    @Override
    public void setUserInputState(String chatId, InputState inputState) {
        telegramUserRepository.updateInputState(chatId, inputState);
    }

    @Override
    public void setUserInputStateAndGroup(String chatId, InputState inputState, InputGroup inputGroup) {
        telegramUserRepository.updateInputStateAndGroup(chatId, inputState, inputGroup);
    }

    @Override
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
        telegramUserRepository.updateItemShowFieldsSettings(chatId, settings);
    }

    @Override
    public void addUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
        telegramUserRepository.addUserItemShowAppliedFilter(chatId, filterName);
    }

    @Override
    @Transactional
    public void removeUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        if (telegramUser.getItemShowAppliedFilters() == null) {
            telegramUser.setItemShowAppliedFilters(new ArrayList<>());
        }

        for (int i = 0; i < telegramUser.getItemShowAppliedFilters().size(); i++) {
            if (telegramUser.getItemShowAppliedFilters().get(i).getName().equals(filterName)) {
                telegramUser.getItemShowAppliedFilters().remove(i);
                break;
            }
        }

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<String> findAllUserItemShowAppliedFiltersNames(String chatId) throws TelegramUserDoesntExistException {

    }

    @Override
    public ItemShowSettings findUserItemShowSettings(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createItemShowSettings(getTelegramUserEntityByIdOrThrow(chatId));
    }


    @Override
    @Transactional
    public void setUserTradeManagersSettingsNewManagersAreActiveFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.setNewManagersAreActiveFlag_(flag);

        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setUserTradeManagersSettingsManagingEnabledFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.setManagingEnabledFlag_(flag);

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public TradeManagersSettings findUserTradeManagersSettings(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createTradeManagersSettings(getTelegramUserEntityByIdOrThrow(chatId));
    }
}
