package github.ricemonger.fetching_acounts_manager.scheduled_tasks;

import github.ricemonger.fetching_acounts_manager.authorization.AuthorizationService;
import github.ricemonger.fetching_acounts_manager.services.AuthorizedUbiUsersService;
import github.ricemonger.fetching_acounts_manager.services.CredentialsJsonDeserializer;
import github.ricemonger.fetching_acounts_manager.services.DTOs.UbiUserCredentials;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduledFetchingAccountsReauthorizationTest {
    @Autowired
    private ScheduledFetchingAccountsReauthorization scheduledFetchingAccountsReauthorization;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private CredentialsJsonDeserializer credentialsJsonDeserializer;
    @MockBean
    private AuthorizedUbiUsersService authorizedUbiUsersService;

    @Test
    public void reauthorizeFetchingUsersAndSave_authorize_and_save_users_using_credentials_from_json() {
        UbiUserCredentials credentials1 = new UbiUserCredentials();
        credentials1.setEmail("email1");
        credentials1.setPassword("password1");
        UbiUserCredentials credentials2 = new UbiUserCredentials();
        credentials2.setEmail("email2");
        credentials2.setPassword("password2");
        UbiUserCredentials credentials3 = new UbiUserCredentials();
        credentials3.setEmail("email3");
        credentials3.setPassword("password3");

        when(credentialsJsonDeserializer.getCredentials()).thenReturn(List.of(credentials1, credentials2, credentials3));

        AuthorizationDTO authorizationDTO1 = mock(AuthorizationDTO.class);
        AuthorizationDTO authorizationDTO2 = mock(AuthorizationDTO.class);

        when(authorizationService.authorizeAndGetBaseAuthorizedDTO(credentials1.getEmail(), credentials1.getPassword())).thenReturn(authorizationDTO1);
        when(authorizationService.authorizeAndGetBaseAuthorizedDTO(credentials2.getEmail(), credentials2.getPassword())).thenReturn(authorizationDTO2);
        when(authorizationService.authorizeAndGetBaseAuthorizedDTO(credentials3.getEmail(), credentials3.getPassword())).thenThrow(new RuntimeException("Error"));

        scheduledFetchingAccountsReauthorization.reauthorizeFetchingUsersAndSave();

        verify(authorizedUbiUsersService).saveAuthorizedUbiUsersAndRemoveOthers(argThat(argument -> argument.size() == 2 && argument.contains(authorizationDTO1) && argument.contains(authorizationDTO2)));
    }
}