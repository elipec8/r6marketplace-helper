package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.InputStateAndGroupTelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.ItemFilterEntityMapper;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.utils.DTOs.personal.*;
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

    private final InputStateAndGroupTelegramUserPostgresRepository inputStateAndGroupTelegramUserRepository;

    private final TelegramUserEntityMapper telegramUserEntityMapper;

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    @Override
    public void create(String chatId) throws TelegramUserAlreadyExistsException {
        if (telegramUserRepository.existsById(chatId)) {
            throw new TelegramUserAlreadyExistsException("Telegram user with chatId " + chatId + " already exists");
        } else {
            telegramUserRepository.save(telegramUserEntityMapper.createNewEntityForNewUser(chatId));
        }
    }

    @Override
    @Transactional
    public void update(TelegramUser updatedTelegramUser) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(updatedTelegramUser.getChatId());
        telegramUser.setFields(updatedTelegramUser, itemFilterEntityMapper);
        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUser.setItemShowFewInMessageFlag(flag);
        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUserEntity.setItemShowMessagesLimit(limit);
        telegramUserRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.setShowItemFieldsSettings(settings);

        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void addItemShowAppliedFilter(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        ItemFilterEntity filterEntity = itemFilterEntityMapper.createEntityForUser(telegramUser.getUser(), filter);

        if (telegramUser.getItemShowAppliedFilters() == null) {
            telegramUser.setItemShowAppliedFilters(new ArrayList<>());
        }

        telegramUser.getItemShowAppliedFilters().add(filterEntity);

        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void removeItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException {
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
    @Transactional
    public void setTradeManagersSettingsNewManagersAreActiveFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.setNewManagersAreActiveFlag_(flag);

        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setTradeManagersSettingsManagingEnabledFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.setManagingEnabledFlag_(flag);

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public boolean existsById(String chatId) {
        return telegramUserRepository.existsById(chatId);
    }

    @Override
    public TelegramUser findUserById(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createTelegramUser(getTelegramUserEntityByIdOrThrow(chatId));
    }

    @Override
    public TelegramUserInputStateAndGroup findUserInputStateAndGroupById(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createInputStateAndGroupDTO(inputStateAndGroupTelegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found")));
    }

    @Override
    public ItemShowSettings findUserItemShowSettingsById(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createItemShowSettings(getTelegramUserEntityByIdOrThrow(chatId));
    }

    @Override
    public TradeManagersSettings findUserTradeManagersSettingsById(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserEntityMapper.createTradeManagersSettings(getTelegramUserEntityByIdOrThrow(chatId));
    }

    @Override
    public List<TelegramUser> findAllUsers() {
        return telegramUserRepository.findAll().stream().map(telegramUserEntityMapper::createTelegramUser).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
