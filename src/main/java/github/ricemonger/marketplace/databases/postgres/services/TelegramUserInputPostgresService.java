package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserInputEntityMapper;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.personal.TelegramUserInput;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserInputPostgresService implements TelegramUserInputDatabaseService {

    private final TelegramUserInputPostgresRepository telegramUserInputRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    private final TelegramUserInputEntityMapper telegramUserInputEntityMapper;

    @Override
    @Transactional
    public void save(String chatId, InputState inputState, String value) throws TelegramUserDoesntExistException {
        telegramUserInputRepository.save(telegramUserInputEntityMapper.createEntity(new TelegramUserInput(chatId, inputState, value)));
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
        return telegramUserInputEntityMapper.createDTO(telegramUserInputRepository.findById(new TelegramUserInputEntityId(chatId, inputState))
                .orElseThrow(() -> new TelegramUserInputDoesntExistException("Input with chatId " + chatId + " and inputState " + inputState + " " + "not found")));
    }

    @Override
    public List<TelegramUserInput> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        Collection<TelegramUserInputEntity> entities = telegramUserInputRepository.findAllByTelegramUserChatId(chatId);

        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        } else {
            return entities.stream()
                    .map(telegramUserInputEntityMapper::createDTO)
                    .toList();
        }
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
