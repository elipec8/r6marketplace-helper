package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledMainUserReauthorizationTest {

    @MockBean
    private CommonValuesService commonValuesService;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private ScheduledMainUserReauthorization scheduledMainUserReauthorization;

    @Test
    public void reauthorizeMainUserAndSave_should_call_services() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setTicket("ticket");
        authorizationDTO.setProfileId("profileId");
        authorizationDTO.setSpaceId("spaceId");
        authorizationDTO.setSessionId("sessionId");
        authorizationDTO.setRememberMeTicket("rememberMeTicket");
        authorizationDTO.setRememberDeviceTicket("rememberDeviceTicket");
        authorizationDTO.setTwoFactorAuthenticationTicket("twoFactorAuthenticationTicket");

        when(commonValuesService.getMainUserEmail()).thenReturn("email");
        when(commonValuesService.getMainUserPassword()).thenReturn("password");
        when(commonValuesService.getExpireTimeout()).thenReturn(1000);

        when(authorizationService.authorizeAndGetDTO("email", "password")).thenReturn(authorizationDTO);

        scheduledMainUserReauthorization.reauthorizeMainUserAndSave();

        verify(authorizationService).authorizeAndGetDTO("email", "password");

        verify(commonValuesService).setMainUserAuthorization(same(authorizationDTO));
    }
}