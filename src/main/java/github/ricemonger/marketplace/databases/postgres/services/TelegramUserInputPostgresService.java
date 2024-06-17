package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserInputPostgresService implements TelegramUserInputDatabaseService {

    private final TelegramUserInputPostgresRepository repository;

    @Override
    public void save(String chatId, InputState inputState, String value) {
        repository.save(new TelegramUserInputEntity(chatId, inputState, value));
    }

    @Override
    public void deleteAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        repository.deleteAllByChatId(chatId);
    }

    @Override
    public TelegramUserInput findById(String chatId, InputState inputState) throws TelegramUserInputDoesntExistException {
        return repository.findById(new TelegramUserInputEntityId(chatId, inputState)).orElseThrow(() -> new TelegramUserInputDoesntExistException("Input with chatId" + chatId + " and inputState " + inputState + " not found")).toTelegramUserInput();
    }

    @Override
    public Collection<TelegramUserInput> findAllByChatId(String chatId) {
        Collection<TelegramUserInputEntity> entities = repository.findAllByChatId(chatId);
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        } else {
            return repository.findAllByChatId(chatId).stream()
                    .map(TelegramUserInputEntity::toTelegramUserInput)
                    .toList();
        }
    }
}
