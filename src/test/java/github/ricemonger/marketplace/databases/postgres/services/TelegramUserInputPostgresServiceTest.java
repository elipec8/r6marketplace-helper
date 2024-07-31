package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelegramUserInputPostgresServiceTest {
    private final static String CHAT_ID = "1";
    private final static String ANOTHER_CHAT_ID = "2";

    @Autowired
    private TelegramUserInputPostgresService inputService;
    @Autowired
    private TelegramUserInputPostgresRepository inputRepository;
    @Autowired
    private TelegramUserPostgresRepository telegramUserRepository;
    @Autowired
    private UserPostgresRepository userRepository;

    @BeforeEach
    public void setUp() {
        inputRepository.deleteAll();
        telegramUserRepository.deleteAll();
        userRepository.deleteAll();

        userRepository.save(new UserEntity());
        TelegramUserEntity tgUser = new TelegramUserEntity();
        tgUser.setChatId(CHAT_ID);
        tgUser.setUser(userRepository.findAll().get(0));
        telegramUserRepository.save(tgUser);
    }

    @Test
    public void save_should_create_new_input_if_user_exists_and_input_does_not_exist() {
        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        String value = "value";

        inputService.save(CHAT_ID, inputState, value);

        assertEquals(1, inputRepository.count());

        TelegramUserInputEntity input = inputRepository.findAll().get(0);

        assertEquals(input.getTelegramUser().getChatId(), CHAT_ID);
        assertEquals(input.getInputState(), inputState);
        assertEquals(input.getValue(), value);
    }

    @Test
    public void save_should_create_new_input_if_user_exists_and_input_with_same_inputState_and_user_does_not_exist() {
        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        InputState inputState2 = InputState.CREDENTIALS_FULL_OR_EMAIL;
        String value = "value";

        inputService.save(CHAT_ID, inputState, value);
        inputService.save(CHAT_ID, inputState2, value);

        assertEquals(2, inputRepository.count());
    }

    @Test
    public void save_should_update_existing_input_if_user_exists_and_input_with_same_inputState_and_user_already_exists() {
        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        String value = "value";
        String value2 = "value2";

        inputService.save(CHAT_ID, inputState, value);
        inputService.save(CHAT_ID, inputState, value2);

        assertEquals(1, inputRepository.count());

        TelegramUserInputEntity input = inputRepository.findAll().get(0);

        assertEquals(input.getTelegramUser().getChatId(), CHAT_ID);
        assertEquals(input.getInputState(), inputState);
        assertEquals(input.getValue(), value2);
    }

    @Test
    public void save_should_throw_if_user_does_not_exist() {
        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        String value = "value";
        Executable executable = () -> inputService.save(ANOTHER_CHAT_ID, inputState, value);

        assertThrows(TelegramUserDoesntExistException.class, executable);
    }

    @Test
    public void deleteAllByChatId_should_delete_all_inputs_for_telegram_user() {
        inputService.save(CHAT_ID, InputState.CREDENTIALS_FULL_OR_EMAIL, "value");
        inputService.save(CHAT_ID, InputState.ACTIVE_CREDENTIALS, "value");
        inputService.save(CHAT_ID, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, "value");

        inputService.deleteAllByChatId(CHAT_ID);

        assertEquals(0, inputRepository.count());
    }

    @Test
    public void deleteAllByChatId_should_ignore_other_user_inputs() {
        userRepository.save(new UserEntity());
        TelegramUserEntity tgUser = new TelegramUserEntity();
        tgUser.setChatId(ANOTHER_CHAT_ID);

        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            if (user.getTelegramUser() == null) {
                tgUser.setUser(user);
                telegramUserRepository.save(tgUser);
                break;
            }
        }

        inputService.save(CHAT_ID, InputState.CREDENTIALS_FULL_OR_EMAIL, "value");
        inputService.save(CHAT_ID, InputState.ACTIVE_CREDENTIALS, "value");
        inputService.save(CHAT_ID, InputState.TRADES_EDIT_ONE_ITEM_ITEM_ID, "value");

        inputService.deleteAllByChatId(ANOTHER_CHAT_ID);

        assertEquals(3, inputRepository.count());
    }

    @Test
    public void deleteAllByChatId_should_throw_if_user_does_not_exist() {
        Executable executable = () -> inputService.deleteAllByChatId(ANOTHER_CHAT_ID);

        assertThrows(TelegramUserDoesntExistException.class, executable);
    }

    @Test
    public void findById_should_return_right_input_if_it_exists() {
        userRepository.save(new UserEntity());
        TelegramUserEntity tgUser = new TelegramUserEntity();
        tgUser.setChatId(ANOTHER_CHAT_ID);

        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            if (user.getTelegramUser() == null) {
                tgUser.setUser(user);
                telegramUserRepository.save(tgUser);
                break;
            }
        }

        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        InputState inputState2 = InputState.CREDENTIALS_FULL_OR_EMAIL;
        String value = "value";
        String value2 = "value2";
        String value3 = "value3";
        String value4 = "value4";

        inputService.save(CHAT_ID, inputState, value);
        inputService.save(CHAT_ID, inputState2, value2);
        inputService.save(ANOTHER_CHAT_ID, inputState, value3);
        inputService.save(ANOTHER_CHAT_ID, inputState2, value4);

        assertEquals(value, inputService.findById(CHAT_ID, inputState).getValue());
        assertEquals(value2, inputService.findById(CHAT_ID, inputState2).getValue());
    }

    @Test
    public void findById_should_throw_if_user_does_not_exist() {
        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        Executable executable = () -> inputService.findById(ANOTHER_CHAT_ID, inputState);

        assertThrows(TelegramUserDoesntExistException.class, executable);
    }

    @Test
    public void findById_should_throw_if_input_does_not_exist() {
        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        Executable executable = () -> inputService.findById(CHAT_ID, inputState);

        assertThrows(TelegramUserInputDoesntExistException.class, executable);
    }

    @Test
    public void findAllByChatId_should_return_all_inputs_for_user() {
        userRepository.save(new UserEntity());
        TelegramUserEntity tgUser = new TelegramUserEntity();
        tgUser.setChatId(ANOTHER_CHAT_ID);

        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            if (user.getTelegramUser() == null) {
                tgUser.setUser(user);
                telegramUserRepository.save(tgUser);
                break;
            }
        }

        InputState inputState = InputState.ACTIVE_CREDENTIALS;
        InputState inputState2 = InputState.CREDENTIALS_FULL_OR_EMAIL;
        String value = "value";
        String value2 = "value2";
        String value3 = "value3";
        String value4 = "value4";

        inputService.save(CHAT_ID, inputState, value);
        inputService.save(CHAT_ID, inputState2, value2);

        List<TelegramUserInput> expectedFirstUserInputs = new ArrayList<>();
        expectedFirstUserInputs.add(new TelegramUserInput(CHAT_ID, inputState, value));
        expectedFirstUserInputs.add(new TelegramUserInput(CHAT_ID, inputState2, value2));

        inputService.save(ANOTHER_CHAT_ID, inputState, value3);
        inputService.save(ANOTHER_CHAT_ID, inputState2, value4);

        List<TelegramUserInput> expectedSecondUserInputs = new ArrayList<>();
        expectedSecondUserInputs.add(new TelegramUserInput(ANOTHER_CHAT_ID, inputState, value3));
        expectedSecondUserInputs.add(new TelegramUserInput(ANOTHER_CHAT_ID, inputState2, value4));

        List<TelegramUserInput> firstUserResult = inputService.findAllByChatId(CHAT_ID);
        List<TelegramUserInput> secondUserResult = inputService.findAllByChatId(ANOTHER_CHAT_ID);

        assertEquals(2, firstUserResult.size());
        assertTrue(firstUserResult.containsAll(expectedFirstUserInputs) && expectedFirstUserInputs.containsAll(firstUserResult));

        assertEquals(2, secondUserResult.size());
        assertTrue(secondUserResult.containsAll(expectedSecondUserInputs) && expectedSecondUserInputs.containsAll(secondUserResult));
    }

    @Test
    public void findAllByChatId_should_return_empty_list_if_no_inputs_for_user() {
        assertEquals(0, inputService.findAllByChatId(CHAT_ID).size());
    }

    @Test
    public void findAllByChatId_should_throw_if_user_does_not_exist() {
        Executable executable = () -> inputService.findAllByChatId(ANOTHER_CHAT_ID);

        assertThrows(TelegramUserDoesntExistException.class, executable);
    }
}