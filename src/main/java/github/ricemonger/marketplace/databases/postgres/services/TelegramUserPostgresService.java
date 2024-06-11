package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.mappers.ItemShowSettingsMapper;
import github.ricemonger.marketplace.databases.postgres.mappers.TelegramUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserPostgresService implements TelegramUserDatabaseService {

    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    private final TelegramUserPostgresMapper mapper;

    private final ItemShowSettingsMapper itemShowSettingsMapper;

    @Override
    public void saveUser(TelegramUser telegramUser) {
        TelegramUserEntity telegramUserEntity = mapper.mapTelegramUserEntity(telegramUser);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    public boolean userExistsById(String chatId) {
        return telegramUserPostgresRepository.existsById(chatId);
    }

    @Override
    public TelegramUser findUserById(String chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserByIdOrThrow(chatId);
    }

    @Override
    public Collection<TelegramUser> findAllUsers() {
        return mapper.mapTelegramUsers(telegramUserPostgresRepository.findAll());
    }

    @Override
    public ItemShowSettings findUserSettingsById(String chatId) {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        return itemShowSettingsMapper.mapItemShowSettings(telegramUserEntity);
    }

    @Override
    public void setItemShowFewItemsInMessageFlag(String chatId, boolean flag) {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUserEntity.setItemShowFewInMessageFlag(flag);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    public void setItemShowMessagesLimit(String chatId, Integer limit) {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUserEntity.setItemShowMessagesLimit(limit);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    public void setItemShowSettings(String chatId, ItemShownFieldsSettings settings) {
        getTelegramUserByIdOrThrow(chatId);

        telegramUserPostgresRepository.save(itemShowSettingsMapper.mapTelegramUserEntity(chatId, settings));
    }

    @Override
    public void addItemShowAppliedFilter(String chatId, String filterName) {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUserEntity.getItemShowAppliedFilters().add(new ItemFilterEntity(chatId, filterName));

        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    public void removeItemShowAppliedFilter(String chatId, String filterName) {
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);

        for (int i = 0; i < telegramUserEntity.getItemShowAppliedFilters().size(); i++) {
            if (telegramUserEntity.getItemShowAppliedFilters().get(i).getName().equals(filterName)) {
                telegramUserEntity.getItemShowAppliedFilters().remove(i);
                break;
            }
        }

        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    private TelegramUser getTelegramUserByIdOrThrow(String chatId) {
        return mapper.mapTelegramUser(getTelegramUserEntityByIdOrThrow(chatId));
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        try {
            return telegramUserPostgresRepository.findById(chatId).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("User with chatId {} not found", chatId);
            throw new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found");
        }
    }
}
