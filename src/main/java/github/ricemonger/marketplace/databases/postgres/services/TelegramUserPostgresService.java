package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserPostgresService implements TelegramUserDatabaseService {

    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    private final UserPostgresRepository userPostgresRepository;

    @Override
    public void create(String chatId) throws TelegramUserAlreadyExistsException {
        UserEntity user = userPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUser = new TelegramUserEntity(chatId, user);

        if (telegramUserPostgresRepository.existsById(chatId)) {
            throw new TelegramUserAlreadyExistsException("Telegram user with chatId " + chatId + " already exists");
        } else {
            telegramUserPostgresRepository.save(telegramUser);
        }
    }

    @Override
    @Transactional
    public void update(TelegramUser updatedTelegramUser) throws TelegramUserDoesntExistException{
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(updatedTelegramUser.getChatId());
        telegramUser.setFields(updatedTelegramUser);
        telegramUserPostgresRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUser.setItemShowFewInMessageFlag(flag);
        telegramUserPostgresRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void setItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUserEntity.setItemShowMessagesLimit(limit);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.setShowItemFieldsSettings(settings);

        telegramUserPostgresRepository.save(telegramUser);
    }

    @Override
    @Transactional
    public void addItemShowAppliedFilter(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        ItemFilterEntity filterEntity = new ItemFilterEntity(telegramUser.getUser(),filter);

        if (telegramUser.getItemShowAppliedFilters() == null) {
            telegramUser.setItemShowAppliedFilters(new ArrayList<>());
        }

        telegramUser.getItemShowAppliedFilters().add(filterEntity);

        telegramUserPostgresRepository.save(telegramUser);
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

        telegramUserPostgresRepository.save(telegramUser);
    }

    @Override
    public boolean existsById(String chatId) {
        return telegramUserPostgresRepository.existsById(chatId);
    }

    @Override
    public TelegramUser findUserById(String chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserEntityByIdOrThrow(chatId).toTelegramUser();
    }

    @Override
    public ItemShowSettings findUserSettingsById(String chatId) {
        return getTelegramUserEntityByIdOrThrow(chatId).toItemShowSettings();
    }

    @Override
    public Collection<TelegramUser> findAllUsers() {
        return telegramUserPostgresRepository.findAll().stream().map(TelegramUserEntity::toTelegramUser).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserPostgresRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
