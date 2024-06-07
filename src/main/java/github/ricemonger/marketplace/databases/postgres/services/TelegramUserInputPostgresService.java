package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.mappers.TelegramUserInputPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserInputPostgresService implements TelegramUserInputDatabaseService {

    private final TelegramUserInputPostgresRepository repository;

    private final TelegramUserInputPostgresMapper mapper;

    @Override
    public void saveInput(TelegramUserInput telegramUserInput){
        repository.save(mapper.mapTelegramUserInputEntity(telegramUserInput));
    }

    @Override
    public void saveInput(String chatId, InputState inputState, String value){
        repository.save(mapper.mapTelegramUserInputEntity(new TelegramUserInput(chatId, inputState, value)));
    }

    @Override
    public void deleteAllInputsByChatId(String chatId) throws TelegramUserDoesntExistException{
        repository.deleteAllByChatId(chatId);
    }

    @Override
    public TelegramUserInput findInputById(String chatId, InputState inputState) throws TelegramUserInputDoesntExistException {
        try {
            return mapper.mapTelegramUserInput(repository.findById(new TelegramUserInputEntityId(chatId, inputState)).orElseThrow());
        } catch (NoSuchElementException e) {
            log.error("Input with chatId {} and inputState {} not found", chatId, inputState);
            throw new TelegramUserInputDoesntExistException("Input with chatId" + chatId + " and inputState " + inputState + " not found");
        }
    }

    @Override
    public Collection<TelegramUserInput> findAllInputsByChatId(String chatId){
        return repository.findAllByChatId(chatId).stream()
                .map(mapper::mapTelegramUserInput)
                .toList();
    }
}
