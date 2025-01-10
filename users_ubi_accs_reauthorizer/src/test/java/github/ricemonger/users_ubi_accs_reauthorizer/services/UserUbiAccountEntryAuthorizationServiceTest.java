package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.users_ubi_accs_reauthorizer.authorization.AuthorizationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.abstractions.UbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class UserUbiAccountEntryAuthorizationServiceTest {
    @Autowired
    private UserUbiAccountEntryAuthorizationService telegramUserUbiAccountEntryAuthorizationService;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private UbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedList_should_reauthorize_all_users_and_return_unauthorized() {
        UserUbiAccountCredentials byRememberDeviceAuth1 = new UserUbiAccountCredentials();
        byRememberDeviceAuth1.setUserId(1L);
        byRememberDeviceAuth1.setTicket("ticket");
        byRememberDeviceAuth1.setEmail("email");
        byRememberDeviceAuth1.setEncodedPassword("encodedPassword");
        byRememberDeviceAuth1.setRememberDeviceTicket("rememberDeviceTicket");

        UserUbiAccountCredentials byRememberDeviceAuth2 = new UserUbiAccountCredentials();
        byRememberDeviceAuth2.setUserId(2L);
        byRememberDeviceAuth2.setTicket("ticket");
        byRememberDeviceAuth2.setEmail("email");
        byRememberDeviceAuth2.setEncodedPassword("encodedPassword");
        byRememberDeviceAuth2.setRememberDeviceTicket("rememberDeviceTicket");

        UserUbiAccountCredentials byRememberDeviceAuth3 = new UserUbiAccountCredentials();
        byRememberDeviceAuth3.setUserId(3L);
        byRememberDeviceAuth3.setTicket("ticket");
        byRememberDeviceAuth3.setEmail("email");
        byRememberDeviceAuth3.setEncodedPassword("encodedPassword");
        byRememberDeviceAuth3.setRememberDeviceTicket("rememberDeviceTicket");

        UserUbiAccountCredentials clientErrorEntry = new UserUbiAccountCredentials();
        clientErrorEntry.setUserId(4L);
        clientErrorEntry.setTicket("ticket");
        clientErrorEntry.setEmail("email1");
        clientErrorEntry.setEncodedPassword("encodedPassword1");
        clientErrorEntry.setRememberDeviceTicket("rememberDeviceTicket1");

        UserUbiAccountCredentials serverErrorEntry = new UserUbiAccountCredentials();
        serverErrorEntry.setUserId(5L);
        serverErrorEntry.setTicket("ticket");
        serverErrorEntry.setEmail("email2");
        serverErrorEntry.setEncodedPassword("encodedPassword2");
        serverErrorEntry.setRememberDeviceTicket("rememberDeviceTicket2");

        UserUbiAccountCredentials invalidRememberDeviceTicketAndAuthTicketEntry = new UserUbiAccountCredentials();
        invalidRememberDeviceTicketAndAuthTicketEntry.setUserId(6L);
        invalidRememberDeviceTicketAndAuthTicketEntry.setTicket("ticket");
        invalidRememberDeviceTicketAndAuthTicketEntry.setEmail("email3");
        invalidRememberDeviceTicketAndAuthTicketEntry.setEncodedPassword("encodedPassword3");
        invalidRememberDeviceTicketAndAuthTicketEntry.setRememberDeviceTicket("rememberDeviceTicket3");

        UserUbiAccountCredentials byTicketEntryAuth1 = new UserUbiAccountCredentials();
        byTicketEntryAuth1.setUserId(7L);
        byTicketEntryAuth1.setTicket("byTicketEntry1");
        byTicketEntryAuth1.setEmail("email7");
        byTicketEntryAuth1.setEncodedPassword("encodedPassword7");
        byTicketEntryAuth1.setRememberDeviceTicket("rememberDeviceTicket7");

        UserUbiAccountCredentials byTicketEntryAuth2 = new UserUbiAccountCredentials();
        byTicketEntryAuth2.setUserId(8L);
        byTicketEntryAuth2.setTicket("byTicketEntry2");
        byTicketEntryAuth2.setEmail("email7");
        byTicketEntryAuth2.setEncodedPassword("encodedPassword7");
        byTicketEntryAuth2.setRememberDeviceTicket("rememberDeviceTicket7");

        UserUbiAccountCredentials byTicketEntryClientError = new UserUbiAccountCredentials();
        byTicketEntryClientError.setUserId(9L);
        byTicketEntryClientError.setTicket("byTicketEntryClientError1");
        byTicketEntryClientError.setEmail("email7");
        byTicketEntryClientError.setEncodedPassword("encodedPassword7");
        byTicketEntryClientError.setRememberDeviceTicket("rememberDeviceTicket7");

        UserUbiAccountCredentials byTicketEntryServerError = new UserUbiAccountCredentials();
        byTicketEntryServerError.setUserId(10L);
        byTicketEntryServerError.setTicket("byTicketEntryServerError");
        byTicketEntryServerError.setEmail("email7");
        byTicketEntryServerError.setEncodedPassword("encodedPassword7");
        byTicketEntryServerError.setRememberDeviceTicket("rememberDeviceTicket7");

        List<UserUbiAccountCredentials> byRememberDevice = new ArrayList<>();

        byRememberDevice.add(byRememberDeviceAuth1);
        byRememberDevice.add(byRememberDeviceAuth2);
        byRememberDevice.add(byRememberDeviceAuth3);

        List<UserUbiAccountCredentials> byTicketAuth = new ArrayList<>();

        byTicketAuth.add(byTicketEntryAuth1);
        byTicketAuth.add(byTicketEntryAuth2);

        AuthorizationDTO validAuthDTO = new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

        AuthorizationDTO invalidAuthDTO = new AuthorizationDTO("ticket", null, "spaceId", "sessionId", "rememberDeviceTicket", "rememberMeTicket");

        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byRememberDeviceAuth1.getEmail(), byRememberDeviceAuth1.getEncodedPassword(), byRememberDeviceAuth1.getRememberDeviceTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byRememberDeviceAuth2.getEmail(), byRememberDeviceAuth2.getEncodedPassword(), byRememberDeviceAuth2.getRememberDeviceTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byRememberDeviceAuth3.getEmail(), byRememberDeviceAuth3.getEncodedPassword(), byRememberDeviceAuth3.getRememberDeviceTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(clientErrorEntry.getEmail(), clientErrorEntry.getEncodedPassword(), clientErrorEntry.getRememberDeviceTicket())).thenThrow(new UbiUserAuthorizationClientErrorException("error"));
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(serverErrorEntry.getEmail(), serverErrorEntry.getEncodedPassword(), serverErrorEntry.getRememberDeviceTicket())).thenThrow(new UbiUserAuthorizationServerErrorException("error"));
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(invalidRememberDeviceTicketAndAuthTicketEntry.getEmail(), invalidRememberDeviceTicketAndAuthTicketEntry.getEncodedPassword(), invalidRememberDeviceTicketAndAuthTicketEntry.getRememberDeviceTicket())).thenReturn(invalidAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byTicketEntryAuth1.getEmail(), byTicketEntryAuth1.getEncodedPassword(), byTicketEntryAuth1.getRememberDeviceTicket())).thenReturn(invalidAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byTicketEntryAuth2.getEmail(), byTicketEntryAuth2.getEncodedPassword(), byTicketEntryAuth2.getRememberDeviceTicket())).thenReturn(invalidAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byTicketEntryClientError.getEmail(), byTicketEntryClientError.getEncodedPassword(), byTicketEntryClientError.getRememberDeviceTicket())).thenReturn(invalidAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(byTicketEntryServerError.getEmail(), byTicketEntryServerError.getEncodedPassword(), byTicketEntryServerError.getRememberDeviceTicket())).thenReturn(invalidAuthDTO);

        when(authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(any())).thenReturn(invalidAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(byTicketEntryAuth1.getTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(byTicketEntryAuth2.getTicket())).thenReturn(validAuthDTO);
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(byTicketEntryClientError.getTicket())).thenThrow(new UbiUserAuthorizationClientErrorException("error"));
        when(authorizationService.reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(byTicketEntryServerError.getTicket())).thenThrow(new UbiUserAuthorizationServerErrorException("error"));

        List<UserUbiAccountCredentials> unAuthUsers = new ArrayList<>();
        unAuthUsers.add(clientErrorEntry);
        unAuthUsers.add(serverErrorEntry);
        unAuthUsers.add(invalidRememberDeviceTicketAndAuthTicketEntry);
        unAuthUsers.add(byTicketEntryClientError);
        unAuthUsers.add(byTicketEntryServerError);

        List<UserUbiAccountCredentials> allUsers = new ArrayList<>();
        allUsers.addAll(byRememberDevice);
        allUsers.addAll(byTicketAuth);
        allUsers.addAll(unAuthUsers);

        when(ubiAccountEntryDatabaseService.findAllUsersUbiAccountCredentials()).thenReturn(allUsers);

        List<UserUnauthorizedUbiAccount> result = telegramUserUbiAccountEntryAuthorizationService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertEquals(result.size(), unAuthUsers.size());
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(clientErrorEntry.getUserId()) && u.getEmail().equals(clientErrorEntry.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(serverErrorEntry.getUserId()) && u.getEmail().equals(serverErrorEntry.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(invalidRememberDeviceTicketAndAuthTicketEntry.getUserId()) && u.getEmail().equals(invalidRememberDeviceTicketAndAuthTicketEntry.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(byTicketEntryClientError.getUserId()) && u.getEmail().equals(byTicketEntryClientError.getEmail())));
        assertTrue(result.stream().anyMatch(u -> u.getId().equals(byTicketEntryServerError.getUserId()) && u.getEmail().equals(byTicketEntryServerError.getEmail())));

        verify(ubiAccountEntryDatabaseService).findAllUsersUbiAccountCredentials();
        verify(ubiAccountEntryDatabaseService).unlinkUbiAccountStatsForUnauthorizedUsers(argThat(res -> res.size() == unAuthUsers.size() && res.stream().allMatch(r -> unAuthUsers.stream().anyMatch(un -> un.getUserId().equals(r.getId()) && un.getEmail().equals(r.getEmail())))));

        verify(authorizationService, times(10)).reauthorizeAndGet2FaAuthorizedDtoForEncodedPasswordWithRememberDeviceTicket(any(), any(), any());
        verify(authorizationService, times(7)).reauthorizeAndGet2FaAuthorizedDTOWithAuthorizeTicket(any());

        verify(ubiAccountEntryDatabaseService).updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(byRememberDeviceAuth1.getUserId(), byRememberDeviceAuth1.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(byRememberDeviceAuth2.getUserId(), byRememberDeviceAuth2.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(byRememberDeviceAuth3.getUserId(), byRememberDeviceAuth3.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(byTicketEntryAuth1.getUserId(), byTicketEntryAuth1.getEmail(), validAuthDTO);
        verify(ubiAccountEntryDatabaseService).updateCredentialsAndLinkUbiAccountStatsForAuthorizedUser(byTicketEntryAuth2.getUserId(), byTicketEntryAuth2.getEmail(), validAuthDTO);
    }
}
