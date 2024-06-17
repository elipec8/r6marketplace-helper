package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;
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

    @Override
    public void save(TelegramUser telegramUser) {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity(telegramUser);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void setItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException{
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUserEntity.setItemShowFewInMessageFlag(flag);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void setItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException{
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);
        telegramUserEntity.setItemShowMessagesLimit(limit);
        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException{
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUserEntity.setShowItemFieldsSettings(settings);

        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void addItemShowAppliedFilter(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException{
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);

        ItemFilterEntity filterEntity = new ItemFilterEntity(filter);

        if(telegramUserEntity.getItemShowAppliedFilters() == null){
            telegramUserEntity.setItemShowAppliedFilters(new ArrayList<>());
        }

        telegramUserEntity.getItemShowAppliedFilters().add(filterEntity);

        telegramUserPostgresRepository.save(telegramUserEntity);
    }

    @Override
    @Transactional
    public void removeItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException{
        TelegramUserEntity telegramUserEntity = getTelegramUserEntityByIdOrThrow(chatId);

        if(telegramUserEntity.getItemShowAppliedFilters() == null){
            telegramUserEntity.setItemShowAppliedFilters(new ArrayList<>());
        }

        for (int i = 0; i < telegramUserEntity.getItemShowAppliedFilters().size(); i++) {
            if (telegramUserEntity.getItemShowAppliedFilters().get(i).getName().equals(filterName)) {
                telegramUserEntity.getItemShowAppliedFilters().remove(i);
                break;
            }
        }

        telegramUserPostgresRepository.save(telegramUserEntity);
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
