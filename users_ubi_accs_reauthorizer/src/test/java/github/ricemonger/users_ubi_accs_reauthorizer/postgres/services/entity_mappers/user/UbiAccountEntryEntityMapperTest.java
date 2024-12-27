package github.ricemonger.users_ubi_accs_reauthorizer.postgres.services.entity_mappers.user;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.user.UbiAccountEntryCredentialsEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.user.UserIdEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUbiCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UbiAccountEntryEntityMapperTest {

    @Test
    public void createUserUbiCredentials_should_return_expected_dto(){
        UbiAccountEntryCredentialsEntity entity = new UbiAccountEntryCredentialsEntity(new UserIdEntity(1L), "email", "encodedPassword", "ubiRememberMeTicket");

        UserUbiCredentials dto = new UbiAccountEntryEntityMapper().createUserUbiCredentials(entity);

        assertEquals(1L, dto.getUserId());
        assertEquals("email", dto.getEmail());
        assertEquals("encodedPassword", dto.getEncodedPassword());
        assertEquals("ubiRememberMeTicket", dto.getRememberDeviceTicket());
    }

}