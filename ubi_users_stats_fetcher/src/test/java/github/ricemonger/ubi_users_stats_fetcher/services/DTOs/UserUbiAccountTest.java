package github.ricemonger.ubi_users_stats_fetcher.services.DTOs;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserUbiAccountTest {

    @Test
    public void toAuthorizationDTO_should_return_correct_AuthorizationDTO() {
        UserUbiAccount userUbiAccount = new UserUbiAccount();
        userUbiAccount.setProfileId("profileId");
        userUbiAccount.setTicket("ticket");
        userUbiAccount.setSpaceId("spaceId");
        userUbiAccount.setSessionId("sessionId");
        userUbiAccount.setRememberDeviceTicket("rememberDeviceTicket");
        userUbiAccount.setRememberMeTicket("rememberMeTicket");

        AuthorizationDTO authorizationDTO = userUbiAccount.toAuthorizationDTO();

        assertEquals(authorizationDTO.getProfileId(), userUbiAccount.getProfileId());
        assertEquals(authorizationDTO.getTicket(), userUbiAccount.getTicket());
        assertEquals(authorizationDTO.getSpaceId(), userUbiAccount.getSpaceId());
        assertEquals(authorizationDTO.getSessionId(), userUbiAccount.getSessionId());
        assertEquals(authorizationDTO.getRememberDeviceTicket(), userUbiAccount.getRememberDeviceTicket());
        assertEquals(authorizationDTO.getRememberMeTicket(), userUbiAccount.getRememberMeTicket());
    }
}