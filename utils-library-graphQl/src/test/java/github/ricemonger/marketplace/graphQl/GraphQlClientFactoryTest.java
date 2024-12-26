package github.ricemonger.marketplace.graphQl;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class GraphQlClientFactoryTest {
    private final GraphQlCommonValuesService commonValuesService = mock(GraphQlCommonValuesService.class);

    private final GraphQlClientFactory graphQlClientFactory = new GraphQlClientFactory(commonValuesService);


    @Test
    public void createMainClient_should_create_client_with_main_user_headers() {
        graphQlClientFactory.createMainUserClient();

        verify(commonValuesService).getContentType();
        verify(commonValuesService).getUbiBaseAppId();
        verify(commonValuesService).getUbiRegionId();
        verify(commonValuesService).getUbiLocaleCode();
        verify(commonValuesService).getUserAgent();
        verify(commonValuesService).getGraphqlUrl();

        verify(commonValuesService).getMainUserAuthorizationToken();
        verify(commonValuesService).getMainUserSessionId();
        verify(commonValuesService).getMainUserProfileId();
        verify(commonValuesService).getUbiGameSpaceId();
    }

    @Test
    public void createAuthorizedClient_should_create_client_with_authorized_user_headers() {
        AuthorizationDTO authorizationDTO = mock(AuthorizationDTO.class);

        graphQlClientFactory.createAuthorizedUserClient(authorizationDTO);

        verify(commonValuesService).getContentType();
        verify(commonValuesService).getUbiBaseAppId();
        verify(commonValuesService).getUbiRegionId();
        verify(commonValuesService).getUbiLocaleCode();
        verify(commonValuesService).getUserAgent();
        verify(commonValuesService).getGraphqlUrl();

        verify(commonValuesService, times(0)).getMainUserAuthorizationToken();
        verify(commonValuesService, times(0)).getMainUserSessionId();
        verify(commonValuesService, times(0)).getMainUserProfileId();
        verify(commonValuesService, times(0)).getUbiGameSpaceId();

        verify(authorizationDTO).getTicket();
        verify(authorizationDTO).getSessionId();
        verify(authorizationDTO).getProfileId();
        verify(authorizationDTO).getSpaceId();
    }
}
