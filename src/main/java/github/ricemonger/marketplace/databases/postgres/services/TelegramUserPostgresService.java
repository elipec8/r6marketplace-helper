package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.mappers.TelegramUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserDatabaseService;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUserPostgresService implements TelegramUserDatabaseService {

    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    private final TelegramUserInputPostgresRepository telegramUserInputPostgresRepository;

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

    @Override
    public void saveInput(TelegramUserInput telegramUserInput) throws TelegramUserDoesntExistException {
        getTelegramUserByIdOrThrow(telegramUserInput.getChatId());

        telegramUserInputPostgresRepository.save(mapper.mapTelegramUserInputEntity(telegramUserInput));
    }

    @Override
    public void saveInput(String chatId, InputState inputState, String value) throws TelegramUserDoesntExistException {
        getTelegramUserByIdOrThrow(chatId);

        telegramUserInputPostgresRepository.save(mapper.mapTelegramUserInputEntity(new TelegramUserInput(chatId, inputState, value)));
    }

    @Override
    public void deleteAllInputsByChatId(String chatId) throws TelegramUserDoesntExistException{
        getTelegramUserByIdOrThrow(chatId);

        telegramUserInputPostgresRepository.deleteAllByChatId(chatId);
    }

    @Override
    public TelegramUserInput findInputById(String chatId, InputState inputState) throws TelegramUserDoesntExistException,TelegramUserInputDoesntExistException {
        getTelegramUserByIdOrThrow(chatId);

        try {
            return mapper.mapTelegramUserInput(telegramUserInputPostgresRepository.findById(new TelegramUserInputEntityId(chatId, inputState)).orElseThrow());
        } catch (NoSuchElementException e) {
            log.error("Input with chatId {} and inputState {} not found", chatId, inputState);
            throw new TelegramUserInputDoesntExistException("Input with chatId" + chatId + " and inputState " + inputState + " not found");
        }
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
