package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.repositories.UserRepository;
import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import github.ricemonger.utils.exceptions.AesPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AesPasswordEncoder AESPasswordEncoder;

    public boolean isTelegramUserRegistered(Long chatId) {
        return false;
    }

    public void registerTelegramUser(Long chatId) {

    }

    public void setUserNextInput(Long chatId, InputState inputState) {

    }

    public void setUserNextInputGroup(Long chatId, InputGroup inputGroup) {

    }

    public InputState getUserInputStateOrNull(Long chatId) {
        return null;
    }

    public InputGroup getUserInputGroupOrNull(Long chatId) {
        return null;
    }

    public String getUserInputByState(Long chatId, InputState inputState) {
        return null;
    }

    public void addCredentials(Long chatId, String email, String password) {

    }

    public void saveUserInput(Long chatId, InputState inputState, String userInput) {

    }

    public void clearUserInputs(Long chatId) {

    }

    public void removeCredentialsByUserInputs(Long chatId) {

    }

    public void removeAllCredentials(Long chatId) {

    }

    public List<String> getCredentialsEmailsList(Long chatId) {
        return null;
    }
}
