package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service.TelegramUserIdInputEntity;
import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service.TelegramUserIdInputEntityId;
import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service.TelegramUserIdInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserInputEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.marketplace.services.abstractions.TelegramUserInputDatabaseService;
import github.ricemonger.utils.enums.InputState;
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

    private final TelegramUserIdInputPostgresRepository telegramUserInputRepository;

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
        return telegramUserInputEntityMapper.createDTO(telegramUserInputRepository.findById(new TelegramUserIdInputEntityId(chatId, inputState))
                .orElseThrow(() -> new TelegramUserInputDoesntExistException("Input with chatId " + chatId + " and inputState " + inputState + " " + "not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelegramUserInput> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        Collection<TelegramUserIdInputEntity> entities = telegramUserInputRepository.findAllByTelegramUserChatId(chatId);

        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        } else {
            return entities.stream()
                    .map(telegramUserInputEntityMapper::createDTO)
                    .toList();
        }
    }
}
