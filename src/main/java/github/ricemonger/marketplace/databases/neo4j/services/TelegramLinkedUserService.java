package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramInputValuesNode;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserNode;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramInputValuesNodeRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramLinkedUserNodeRepository;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramLinkedUserService {

    private final TelegramLinkedUserNodeRepository telegramLinkedUserNodeRepository;

    private final TelegramInputValuesNodeRepository telegramInputValuesNodeRepository;

    private final UbiUserService ubiUserService;

    public boolean isTelegramUserRegistered(Long chatId) {
        return telegramLinkedUserNodeRepository.existsById(String.valueOf(chatId));
    }

    public void registerTelegramUser(Long chatId) throws TelegramUserAlreadyExistsException {
        if (telegramLinkedUserNodeRepository.existsById(String.valueOf(chatId))) {
            throw new TelegramUserAlreadyExistsException();
        } else {
            telegramLinkedUserNodeRepository.save(new TelegramLinkedUserNode(String.valueOf(chatId)));
        }
    }

    public void setUserNextInputState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);
        telegramLinkedUserNode.setInputState(inputState);
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode);
    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) throws TelegramUserDoesntExistException {

        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);
        telegramLinkedUserNode.setInputGroup(inputGroup);
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode);

    }

    public InputState getUserInputState(Long chatId) throws TelegramUserDoesntExistException {

        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);

        return telegramLinkedUserNode.getInputState();

    }

    public InputGroup getUserInputGroup(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);

        return telegramLinkedUserNode.getInputGroup();
    }

    public String getUserInputByStateOrNull(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        return getInputValueByState(chatId, inputState);
    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) throws TelegramUserDoesntExistException {

        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);
        List<TelegramInputValuesNode> inputValues = telegramLinkedUserNode.getInputValues();

        for (TelegramInputValuesNode value : inputValues) {
            if (value.getInputState().equals(inputState)) {
                value.setValue(userInput);
                log.info("Input value updated, instead of adding new one. For user-{} and state-{}", chatId, inputState);
                return;
            }
        }

        inputValues.add(new TelegramInputValuesNode(inputState, userInput));

        telegramLinkedUserNode.setInputValues(inputValues);
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode);
    }

    public void clearUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        telegramInputValuesNodeRepository.deleteAllByOwnerChatId(String.valueOf(chatId));
    }

    public void addCredentials(Long chatId, String email, String password) throws TelegramUserDoesntExistException {
        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);

        ubiUserService.createAndAuthorizeOrThrowForTelegramUser(telegramLinkedUserNode, email, password);
    }

    public void removeCredentialsByUserInputs(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        String emailToRemove = getInputValueByState(chatId, InputState.CREDENTIALS_FULL_OR_EMAIL);

        ubiUserService.deleteByLinkedTelegramUserChatIdAndEmail(String.valueOf(chatId), emailToRemove);
    }

    public void removeAllCredentials(Long chatId) throws TelegramUserDoesntExistException {
        getTelegramUserOrThrow(chatId);

        ubiUserService.deleteAllByLinkedTelegramUserChatId(String.valueOf(chatId));
    }

    public List<String> getCredentialsEmailsList(Long chatId) throws TelegramUserDoesntExistException {
        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);

        List<UbiUserNode> credentialsList = telegramLinkedUserNode.getLinkedUbisoftAccounts();

        return credentialsList.stream()
                .map(UbiUserNode::getEmail)
                .toList();
    }

    public List<String> getAllChatIdsForNotifiableUsers() {
        return telegramLinkedUserNodeRepository.findAll().stream()
                .filter(TelegramLinkedUserNode::isPublicNotificationsEnabledFlag)
                .map(TelegramLinkedUserNode::getChatId)
                .toList();
    }

    public TelegramLinkedUserNode getTelegramUser(Long chatId) throws TelegramUserDoesntExistException {
        return getTelegramUserOrThrow(chatId);
    }

    private String getInputValueByState(Long chatId, InputState inputState) throws TelegramUserDoesntExistException {
        TelegramLinkedUserNode telegramLinkedUserNode = getTelegramUserOrThrow(chatId);

        List<TelegramInputValuesNode> inputValues = telegramLinkedUserNode.getInputValues();

        for (TelegramInputValuesNode value : inputValues) {
            if (value.getInputState().equals(inputState)) {
                return value.getValue();
            }
        }

        return null;
    }

    private TelegramLinkedUserNode getTelegramUserOrThrow(Long chatId) {
        try {
            return telegramLinkedUserNodeRepository.findById(String.valueOf(chatId)).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new TelegramUserDoesntExistException(e);
        }
    }
}
