package github.ricemonger.ubi_users_stats_fetcher.services.DTOs;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAuthorizedUbiAccountTest {

    @Test
    public void toAuthorizationDTO_should_return_correct_AuthorizationDTO() {
        UserAuthorizedUbiAccount userAuthorizedUbiAccount = new UserAuthorizedUbiAccount();
        userAuthorizedUbiAccount.setProfileId("profileId");
        userAuthorizedUbiAccount.setTicket("ticket");
        userAuthorizedUbiAccount.setSpaceId("spaceId");
        userAuthorizedUbiAccount.setSessionId("sessionId");
        userAuthorizedUbiAccount.setRememberDeviceTicket("rememberDeviceTicket");
        userAuthorizedUbiAccount.setRememberMeTicket("rememberMeTicket");

        AuthorizationDTO authorizationDTO = userAuthorizedUbiAccount.toAuthorizationDTO();

        assertEquals(authorizationDTO.getProfileId(), userAuthorizedUbiAccount.getProfileId());
        assertEquals(authorizationDTO.getTicket(), userAuthorizedUbiAccount.getTicket());
        assertEquals(authorizationDTO.getSpaceId(), userAuthorizedUbiAccount.getSpaceId());
        assertEquals(authorizationDTO.getSessionId(), userAuthorizedUbiAccount.getSessionId());
        assertEquals(authorizationDTO.getRememberDeviceTicket(), userAuthorizedUbiAccount.getRememberDeviceTicket());
        assertEquals(authorizationDTO.getRememberMeTicket(), userAuthorizedUbiAccount.getRememberMeTicket());
    }
}