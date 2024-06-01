package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.TelegramUserInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TelegramUserPostgresMapperTest {

    private final TelegramUser TELEGRAM_USER = new TelegramUser("chatId", InputState.BASE, InputGroup.BASE, true);

    private final TelegramUserEntity TELEGRAM_USER_ENTITY = new TelegramUserEntity("chatId", InputState.BASE, InputGroup.BASE, true);

    private final TelegramUserInput TELEGRAM_USER_INPUT = new TelegramUserInput("chatId", InputState.BASE,  "value");

    private final TelegramUserInputEntity TELEGRAM_USER_INPUT_ENTITY = new TelegramUserInputEntity("chatId", InputState.BASE, "value");

    @SpyBean
    private TelegramUserPostgresMapper mapper;

    @Test
    public void mapTelegramUserEntity_should_throw_if_null(){
        Executable executable = () -> mapper.mapTelegramUserEntity(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapTelegramUserEntity_should_not_throw_if_empty(){
        Executable executable = () -> mapper.mapTelegramUserEntity(new TelegramUser());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapTelegramUserEntity_should_map_telegram_user_entity(){
        assertTrue(telegramUserEntitiesAreEqual(TELEGRAM_USER_ENTITY, mapper.mapTelegramUserEntity(TELEGRAM_USER)));
    }

    @Test
    public void mapTelegramUsers_should_throw_if_null(){
        Executable executable = () -> mapper.mapTelegramUsers(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapTelegramUsers_should_return_empty_list_if_empty(){
        assertEquals(0, mapper.mapTelegramUsers(new ArrayList<>()).size());
    }

    @Test
    public void mapTelegramUsers_should_return_list_of_telegram_users_and_map_each_user(){
        assertEquals(TELEGRAM_USER, new ArrayList<>(mapper.mapTelegramUsers(List.of(TELEGRAM_USER_ENTITY))).get(0));

        verify(mapper).mapTelegramUser(TELEGRAM_USER_ENTITY);
    }

    @Test
    public void mapTelegramUser_should_throw_if_null(){
        Executable executable = () -> mapper.mapTelegramUser(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapTelegramUser_should_not_throw_if_empty(){
        Executable executable = () -> mapper.mapTelegramUser(new TelegramUserEntity());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapTelegramUser_should_map_telegram_user(){
        assertTrue(telegramUsersAreEqual(TELEGRAM_USER, mapper.mapTelegramUser(TELEGRAM_USER_ENTITY)));
    }

    @Test
    public void mapTelegramUserInputEntity_should_throw_if_null(){
        Executable executable = () -> mapper.mapTelegramUserInputEntity(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapTelegramUserInputEntity_should_not_throw_if_empty(){
        Executable executable = () -> mapper.mapTelegramUserInputEntity(new TelegramUserInput());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapTelegramUserInputEntity_should_map_telegram_user_input_entity(){
        assertTrue(telegramUserInputEntitiesAreEqual(TELEGRAM_USER_INPUT_ENTITY, mapper.mapTelegramUserInputEntity(TELEGRAM_USER_INPUT)));
    }

    @Test
    public void mapTelegramUserInput_should_throw_if_null(){
        Executable executable = () -> mapper.mapTelegramUserInput(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapTelegramUserInput_should_not_throw_if_empty(){
        Executable executable = () -> mapper.mapTelegramUserInput(new TelegramUserInputEntity());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapTelegramUserInput_should_map_telegram_user_input(){
        assertTrue(telegramUserInputsAreEqual(TELEGRAM_USER_INPUT, mapper.mapTelegramUserInput(TELEGRAM_USER_INPUT_ENTITY)));
    }

    private boolean telegramUserEntitiesAreEqual(TelegramUserEntity entity1, TelegramUserEntity entity2) {
        return entity1.getChatId().equals(entity2.getChatId()) &&
               entity1.getInputState().equals(entity2.getInputState()) &&
               entity1.getInputGroup().equals(entity2.getInputGroup()) &&
               entity1.isPublicNotificationsEnabledFlag() == entity2.isPublicNotificationsEnabledFlag();
    }

    private boolean telegramUsersAreEqual(TelegramUser user1, TelegramUser user2) {
        return user1.getChatId().equals(user2.getChatId()) &&
                user1.getInputState().equals(user2.getInputState()) &&
                user1.getInputGroup().equals(user2.getInputGroup()) &&
                user1.isPublicNotificationsEnabledFlag() == user2.isPublicNotificationsEnabledFlag();
    }

    private boolean telegramUserInputEntitiesAreEqual(TelegramUserInputEntity entity1, TelegramUserInputEntity entity2) {
        return entity1.getChatId().equals(entity2.getChatId()) &&
               entity1.getInputState().equals(entity2.getInputState()) &&
               entity1.getValue().equals(entity2.getValue());
    }

    private boolean telegramUserInputsAreEqual(TelegramUserInput input1, TelegramUserInput input2) {
        return input1.getChatId().equals(input2.getChatId()) &&
                input1.getInputState().equals(input2.getInputState()) &&
                input1.getValue().equals(input2.getValue());
    }
}