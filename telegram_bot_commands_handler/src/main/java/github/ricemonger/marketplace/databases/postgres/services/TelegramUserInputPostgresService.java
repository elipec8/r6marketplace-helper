package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserInputEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
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

    private final TelegramUserInputEntityMapper telegramUserInputEntityMapper;

    @Override
    @Transactional
    public void save(String chatId, InputState inputState, String value) throws TelegramUserDoesntExistException {
        telegramUserInputRepository.save(telegramUserInputEntityMapper.createEntity(new TelegramUserInput(chatId, inputState, value)));
    }

    @Override
    @Transactional
    public void deleteAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        telegramUserInputRepository.deleteAllByTelegramUserChatId(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public TelegramUserInput findById(String chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        return telegramUserInputRepository.findById(new TelegramUserInputEntityId(chatId, inputState))
                .map(telegramUserInputEntityMapper::createDTO)
                .orElseThrow(() -> new TelegramUserInputDoesntExistException("Telegram user input with chatId " + chatId + " and inputState " + inputState + " doesn't exist"));
    }

    @Override
    @Transactional(readOnly = true)
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
}
