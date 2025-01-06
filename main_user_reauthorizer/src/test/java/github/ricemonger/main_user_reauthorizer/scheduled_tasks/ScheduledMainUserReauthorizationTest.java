package github.ricemonger.main_user_reauthorizer.scheduled_tasks;

import github.ricemonger.main_user_reauthorizer.authorization.AuthorizationService;
import github.ricemonger.main_user_reauthorizer.services.CommonValuesService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledMainUserReauthorizationTest {
    @Autowired
    private ScheduledMainUserReauthorization scheduledMainUserReauthorization;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private AuthorizationService authorizationService;

    @Test
    public void reauthorizeMainUserAndSave_should_call_services() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setTicket("ticket");
        authorizationDTO.setProfileId("profileId");
        authorizationDTO.setSpaceId("spaceId");
        authorizationDTO.setSessionId("sessionId");
        authorizationDTO.setRememberMeTicket("rememberMeTicket");
        authorizationDTO.setRememberDeviceTicket("rememberDeviceTicket");

        when(commonValuesService.getMainUserEmail()).thenReturn("email");
        when(commonValuesService.getMainUserPassword()).thenReturn("password");
        when(commonValuesService.getExpireTimeout()).thenReturn(1000);

        when(authorizationService.authorizeAndGetBaseAuthorizedDTO("email", "password")).thenReturn(authorizationDTO);

        scheduledMainUserReauthorization.reauthorizeMainUserAndSave();

        verify(authorizationService).authorizeAndGetBaseAuthorizedDTO("email", "password");

        verify(commonValuesService).setMainUserAuthorization(same(authorizationDTO));
    }
}