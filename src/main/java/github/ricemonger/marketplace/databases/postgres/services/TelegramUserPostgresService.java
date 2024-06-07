package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.mappers.TelegramUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
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

    private TelegramUser getTelegramUserByIdOrThrow(String chatId) {
        try {
            return mapper.mapTelegramUser(telegramUserPostgresRepository.findById(chatId).orElseThrow());
        } catch (NoSuchElementException e) {
            log.error("User with chatId {} not found", chatId);
            throw new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found");
        }
    }
}
