package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.users_ubi_accs_reauthorizer.authorization.AuthorizationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserToNotify;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserUbiAccountEntryServiceTest {
    @Autowired
    private UserUbiAccountEntryService telegramUserUbiAccountEntryService;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private UbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedList_should_reauthorize_all_users_and_return_unauthorized() {
        UserUbiCredentials authorizedEntry1 = new UserUbiCredentials();
        authorizedEntry1.setUserId(1L);
        authorizedEntry1.setEmail("email");
        authorizedEntry1.setEncodedPassword("encodedPassword");
        authorizedEntry1.setRememberDeviceTicket("rememberDeviceTicket");

        UserUbiCredentials authorizedEntry2 = new UserUbiCredentials();
        authorizedEntry2.setUserId(2L);
        authorizedEntry2.setEmail("email");
        authorizedEntry2.setEncodedPassword("encodedPassword");
        authorizedEntry2.setRememberDeviceTicket("rememberDeviceTicket");

        UserUbiCredentials authorizedEntry3 = new UserUbiCredentials();
        authorizedEntry3.setUserId(3L);
        authorizedEntry3.setEmail("email");
        authorizedEntry3.setEncodedPassword("encodedPassword");
        authorizedEntry3.setRememberDeviceTicket("rememberDeviceTicket");

        UserUbiCredentials clientErrorEntry = new UserUbiCredentials();
        clientErrorEntry.setUserId(4L);
        clientErrorEntry.setEmail("email1");
        clientErrorEntry.setEncodedPassword("encodedPassword1");
        clientErrorEntry.setRememberDeviceTicket("rememberDeviceTicket1");

        UserUbiCredentials serverErrorEntry = new UserUbiCredentials();
        serverErrorEntry.setUserId(5L);
        serverErrorEntry.setEmail("email2");
        serverErrorEntry.setEncodedPassword("encodedPassword2");
        serverErrorEntry.setRememberDeviceTicket("rememberDeviceTicket2");

        UserUbiCredentials invalidRememberDeviceTicketEntry = new UserUbiCredentials();
        invalidRememberDeviceTicketEntry.setUserId(6L);
        invalidRememberDeviceTicketEntry.setEmail("email3");
        invalidRememberDeviceTicketEntry.setEncodedPassword("encodedPassword3");
        invalidRememberDeviceTicketEntry.setRememberDeviceTicket("rememberDeviceTicket3");

        UserUbiCredentials databaseException1 = new UserUbiCredentials();
        databaseException1.setUserId(7L);
        databaseException1.setEmail("email7");
        databaseException1.setEncodedPassword("encodedPassword7");
        databaseException1.setRememberDeviceTicket("rememberDeviceTicket7");

        UserUbiCredentials databaseException2 = new UserUbiCredentials();
        databaseException2.setUserId(8L);
        databaseException2.setEmail("email7");
        databaseException2.setEncodedPassword("encodedPassword7");
        databaseException2.setRememberDeviceTicket("rememberDeviceTicket7");

        List<UserUbiCredentials> authUsers = new ArrayList<>();

        authUsers.add(authorizedEntry1);
        authUsers.add(authorizedEntry2);
        authUsers.add(authorizedEntry3);

        List<UserUbiCredentials> authUsersWithDatabaseExceptions = new ArrayList<>();

        authUsersWithDatabaseExceptions.add(databaseException1);
        authUsersWithDatabaseExceptions.add(databaseException2);

        AuthorizationDTO validAuthDTO = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

        AuthorizationDTO invalidAuthDTO = new AuthorizationDTO("ticket", null, "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(authorizedEntry1.getEmail(),
                authorizedEntry1.getEncodedPassword(), authorizedEntry1.getRememberDeviceTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(databaseException1.getEmail(),
                databaseException1.getEncodedPassword(), databaseException1.getRememberDeviceTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(databaseException2.getEmail(),
                databaseException2.getEncodedPassword(), databaseException2.getRememberDeviceTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(invalidRememberDeviceTicketEntry.getEmail(),
                invalidRememberDeviceTicketEntry.getEncodedPassword(), invalidRememberDeviceTicketEntry.getRememberDeviceTicket())).thenReturn(invalidAuthDTO);

        doThrow(TelegramUserDoesntExistException.class).when(ubiAccountEntryDatabaseService).saveAuthorizationInfo(eq(7L), eq(databaseException1.getEmail()), eq(validAuthDTO));
        doThrow(TelegramUserDoesntExistException.class).when(ubiAccountEntryDatabaseService).saveAuthorizationInfo(eq(8L), eq(databaseException2.getEmail()), eq(validAuthDTO));

        List<UserUbiCredentials> unAuthUsers = new ArrayList<>();
        unAuthUsers.add(clientErrorEntry);
        unAuthUsers.add(serverErrorEntry);
        unAuthUsers.add(invalidRememberDeviceTicketEntry);

        doThrow(UbiUserAuthorizationClientErrorException.class).when(authorizationService).reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(clientErrorEntry.getEmail(), clientErrorEntry.getEncodedPassword(), clientErrorEntry.getRememberDeviceTicket());
        doThrow(UbiUserAuthorizationServerErrorException.class).when(authorizationService).reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(serverErrorEntry.getEmail(), serverErrorEntry.getEncodedPassword(), serverErrorEntry.getRememberDeviceTicket());

        List<UserUbiCredentials> allUsers = new ArrayList<>();
        allUsers.addAll(authUsers);
        allUsers.addAll(authUsersWithDatabaseExceptions);
        allUsers.addAll(unAuthUsers);

        when(ubiAccountEntryDatabaseService.findAllUsersUbiCredentials()).thenReturn(allUsers);

        List<UserToNotify> result = telegramUserUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertEquals(result.size(), unAuthUsers.size());
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(clientErrorEntry.getUserId()) && u.getEmail().equals(clientErrorEntry.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(serverErrorEntry.getUserId()) && u.getEmail().equals(serverErrorEntry.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(invalidRememberDeviceTicketEntry.getUserId()) && u.getEmail().equals(invalidRememberDeviceTicketEntry.getEmail())));

        verify(ubiAccountEntryDatabaseService).findAllUsersUbiCredentials();

        verify(authorizationService, times(8)).reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(any(), any(), any());

        verify(ubiAccountEntryDatabaseService).saveAuthorizationInfo(authorizedEntry1.getUserId(), authorizedEntry1.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).saveAuthorizationInfo(authorizedEntry2.getUserId(), authorizedEntry2.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).saveAuthorizationInfo(authorizedEntry3.getUserId(), authorizedEntry3.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).saveAuthorizationInfo(databaseException1.getUserId(), databaseException1.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).saveAuthorizationInfo(databaseException2.getUserId(), databaseException2.getEmail(), validAuthDTO);
    }
}
