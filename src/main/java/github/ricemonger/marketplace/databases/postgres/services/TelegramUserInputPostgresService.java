package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserInputPostgresService implements TelegramUserInputDatabaseService {

    private final TelegramUserInputPostgresRepository inputRepository;

    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    @Override
    @Transactional
    public void save(String chatId, InputState inputState, String value) {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        TelegramUserInputEntity input = inputRepository.findById(new TelegramUserInputEntityId(user, inputState)).orElse(new TelegramUserInputEntity(user, inputState));
        input.setValue(value);
        inputRepository.save(input);
    }

    @Override
    @Transactional
    public void deleteAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        user.getTelegramUserInputs().clear();

        telegramUserPostgresRepository.save(user);
    }

    @Override
    public TelegramUserInput findById(String chatId, InputState inputState) throws TelegramUserInputDoesntExistException {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        return inputRepository.findById(new TelegramUserInputEntityId(user, inputState)).orElseThrow(() -> new TelegramUserInputDoesntExistException(
                "Input with chatId" + chatId + " and inputState " + inputState + " not found")).toTelegramUserInput();
    }

    @Override
    public Collection<TelegramUserInput> findAllByChatId(String chatId) {
        Collection<TelegramUserInputEntity> entities = inputRepository.findAllByTelegramUserChatId(chatId);
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        } else {
            return inputRepository.findAllByTelegramUserChatId(chatId).stream()
                    .map(TelegramUserInputEntity::toTelegramUserInput)
                    .toList();
        }
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserPostgresRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
