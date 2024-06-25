package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.abstractions.UbiAccountDatabaseService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationServerErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramLinkedUbiAccountServiceTest {

    @MockBean
    private UbiAccountDatabaseService ubiAccountDatabaseService;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private TelegramUbiAccountService telegramUbiAccountService;

    @Test
    public void findAllByLinkedTelegramUserChatId_should_return_all_users_by_chat_id() {
        String chatId = "chatId";

        List<UbiAccount> users = List.of(new UbiAccount(), new UbiAccount());

        when(ubiAccountDatabaseService.findAllByChatId(chatId)).thenReturn(users);

        List<UbiAccount> result = new ArrayList<>(telegramUbiAccountService.findByChatId(chatId));

        assertTrue(users.containsAll(result) && result.containsAll(users));
    }

    @Test
    public void deleteAllByLinkedTelegramUserChatId_should_delete_all_users_by_chat_id() {
        String chatId = "chatId";

        telegramUbiAccountService.deleteByChatId(chatId);

        verify(ubiAccountDatabaseService).deleteAllByChatId(chatId);
    }

    @Test
    public void deleteByLinkedTelegramUserChatIdAndEmail_should_delete_user_by_chat_id_and_email() {
        String chatId = "chatId";
        String email = "email";

        telegramUbiAccountService.deleteByChatId(chatId, email);

        verify(ubiAccountDatabaseService).deleteByChatId(chatId, email);
    }

    @Test
    public void authorizeAndSaveUser_should_authorize_and_save_user() {
        String chatId = "chatId";
        String email = "email";
        String password = "password";
        when(authorizationService.authorizeAndGetDTO(email, password)).thenReturn(new AuthorizationDTO());

        telegramUbiAccountService.authorizeAndSaveUser(chatId, email, password);

        verify(authorizationService).authorizeAndGetDTO(email, password);
        verify(ubiAccountDatabaseService).save(any());
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_authorization_client_error() {
        String chatId = "chatId";
        String email = "email";
        String password = "password";

        when(authorizationService.authorizeAndGetDTO(email, password)).thenThrow(new UbiUserAuthorizationClientErrorException());

        assertThrows(UbiUserAuthorizationClientErrorException.class, () -> telegramUbiAccountService.authorizeAndSaveUser(chatId, email, password));
    }

    @Test
    public void authorizeAndSaveUser_should_throw_if_authorization_server_error() {
        String chatId = "chatId";
        String email = "email";
        String password = "password";

        when(authorizationService.authorizeAndGetDTO(email, password)).thenThrow(new UbiUserAuthorizationServerErrorException());

        assertThrows(UbiUserAuthorizationServerErrorException.class, () -> telegramUbiAccountService.authorizeAndSaveUser(chatId, email, password));
    }

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedList_should_reauthorize_all_users_and_return_unauthorized() {

        UbiAccount user1 = new UbiAccount();
        user1.setEmail("email1");
        user1.setEncodedPassword("encodedPassword1");
        UbiAccount user2 = new UbiAccount();
        user2.setEmail("email2");
        user2.setEncodedPassword("encodedPassword2");

        List<UbiAccount> users = List.of(user1, user2);

        when(ubiAccountDatabaseService.findAll()).thenReturn(users);

        List<UbiAccount> unauthorizedUsers = List.of(users.get(0));

        when(authorizationService.authorizeAndGetDtoForEncodedPassword("email1", "encodedPassword1")).thenThrow(new UbiUserAuthorizationClientErrorException());
        when(authorizationService.authorizeAndGetDtoForEncodedPassword("email2", "encodedPassword2")).thenReturn(new AuthorizationDTO());

        List<UbiAccount> result = new ArrayList<>(telegramUbiAccountService.reauthorizeAllUbiUsersAndGetUnauthorizedList());

        assertTrue(unauthorizedUsers.containsAll(result) && result.containsAll(unauthorizedUsers));
    }
}