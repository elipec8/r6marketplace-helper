package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserInputPostgresService implements TelegramUserInputDatabaseService {

    private final TelegramUserInputPostgresRepository telegramUserInputRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    @Override
    @Transactional
    public void save(String chatId, InputState inputState, String value) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        TelegramUserInputEntity input = telegramUserInputRepository.findById(new TelegramUserInputEntityId(telegramUser, inputState)).orElse(new TelegramUserInputEntity(telegramUser, inputState));
        input.setValue(value);
        telegramUserInputRepository.save(input);
    }

    @Override
    @Transactional
    public void deleteAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.getTelegramUserInputs().clear();

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public TelegramUserInput findById(String chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return telegramUserInputRepository.findById(new TelegramUserInputEntityId(telegramUser, inputState)).orElseThrow(() -> new TelegramUserInputDoesntExistException(
                "Input with chatId" + chatId + " and inputState " + inputState + " not found")).toTelegramUserInput();
    }

    @Override
    public List<TelegramUserInput> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        Collection<TelegramUserInputEntity> entities = telegramUser.getTelegramUserInputs();

        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        } else {
            return entities.stream()
                    .map(TelegramUserInputEntity::toTelegramUserInput)
                    .toList();
        }
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
