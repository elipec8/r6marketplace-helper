package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUbiAccountAuthorizedProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUbiAccountCredentialsProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UserUnauthorizedUbiAccountProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiAccountCredentials;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UbiAccountEntryEntityMapperTest {
    @Autowired
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void createUserUbiAccountCredentials_should_return_expected_result() {
        UserUbiAccountCredentialsProjection projection = new UserUbiAccountCredentialsProjection(1L, "email", "encodedPassword", "ubiAuthTicket", "ubiRememberDeviceTicket");

        UserUbiAccountCredentials credentials = ubiAccountEntryEntityMapper.createUserUbiAccountCredentials(projection);

        assertEquals(1L, credentials.getUserId());
        assertEquals("email", credentials.getEmail());
        assertEquals("encodedPassword", credentials.getEncodedPassword());
        assertEquals("ubiAuthTicket", credentials.getTicket());
        assertEquals("ubiRememberDeviceTicket", credentials.getRememberDeviceTicket());
    }

    @Test
    public void createUserUbiAccountCredentialsProjection_should_return_expected_result() {
        Long userId = 1L;
        String email = "email";
        AuthorizationDTO authDTO = new AuthorizationDTO();
        authDTO.setSessionId("sessionId");
        authDTO.setSpaceId("spaceId");
        authDTO.setTicket("ticket");
        authDTO.setRememberDeviceTicket("rememberDeviceTicket");
        authDTO.setRememberMeTicket("rememberMeTicket");

        UserUbiAccountAuthorizedProjection projection = ubiAccountEntryEntityMapper.createUserUbiAccountAuthorizedProjection(userId, email, authDTO);

        assertEquals(userId, projection.getUserId());
        assertEquals(email, projection.getEmail());
        assertEquals("sessionId", projection.getUbiSessionId());
        assertEquals("spaceId", projection.getUbiSpaceId());
        assertEquals("ticket", projection.getUbiAuthTicket());
        assertEquals("rememberDeviceTicket", projection.getUbiRememberDeviceTicket());
        assertEquals("rememberMeTicket", projection.getUbiRememberMeTicket());
    }

    @Test
    public void createUnauthorizedAccountProjection_should_return_expected_result() {
        UserUnauthorizedUbiAccount userUnauthorizedUbiAccount = new UserUnauthorizedUbiAccount(1L, "email");

        UserUnauthorizedUbiAccountProjection projection = ubiAccountEntryEntityMapper.createUnauthorizedAccountProjection(userUnauthorizedUbiAccount);

        assertEquals(1L, projection.getId());
        assertEquals("email", projection.getEmail());
    }
}