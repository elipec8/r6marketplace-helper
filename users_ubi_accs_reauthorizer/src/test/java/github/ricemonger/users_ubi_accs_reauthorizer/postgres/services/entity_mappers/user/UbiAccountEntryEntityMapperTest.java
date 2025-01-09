package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UnauthorizedAccountProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryCredentialsEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UserIdEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UnauthorizedAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UbiAccountEntryEntityMapperTest {

    @Test
    public void createUserUbiCredentials_should_return_expected_dto() {
        UbiAccountEntryCredentialsEntity entity = new UbiAccountEntryCredentialsEntity(new UserIdEntity(1L), "email", "encodedPassword", "ubiRememberMeTicket");

        UserUbiCredentials dto = new UbiAccountEntryEntityMapper().createUserUbiCredentials(entity);

        assertEquals(1L, dto.getUserId());
        assertEquals("email", dto.getEmail());
        assertEquals("encodedPassword", dto.getEncodedPassword());
        assertEquals("ubiRememberMeTicket", dto.getRememberDeviceTicket());
    }

    @Test
    public void createUnauthorizedAccountProjection_should_return_expected_result() {
        UnauthorizedAccount unauthorizedAccount = new UnauthorizedAccount(1L, "email");

        UnauthorizedAccountProjection projection = new UbiAccountEntryEntityMapper().createUnauthorizedAccountProjection(unauthorizedAccount);

        assertEquals(1L, projection.getId());
        assertEquals("email", projection.getEmail());
    }
}