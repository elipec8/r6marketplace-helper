package github.ricemonger.marketplace.authorization;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorizationServiceTest {
    @MockBean
    private CommonValuesService commonValuesService;

    @MockBean
    private AesPasswordEncoder aesPasswordEncoder;

    @SpyBean
    private AuthorizationService authorizationService;

    @Test
    void authorizeAndGetDtoForEncodedPassword_should_encode_password_and_return_authorizeAndGet_result() throws Exception {
        String email = "email";
        String encodedPassword = "encodedPassword";
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();

        when(aesPasswordEncoder.decode(encodedPassword)).thenReturn("password");
        when(authorizationService.authorizeAndGetDTO(email, "password")).thenReturn(authorizationDTO);

        AuthorizationDTO actualAuthorizationDTO = authorizationService.authorizeAndGetDtoForEncodedPassword(email, encodedPassword);

        assertSame(authorizationDTO, actualAuthorizationDTO);
    }

    @Test
    void getEncodedPassword_should_return_encoded_password() {
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(aesPasswordEncoder.encode(password)).thenReturn(encodedPassword);

        String actualEncodedPassword = authorizationService.getEncodedPassword(password);

        assertEquals(encodedPassword, actualEncodedPassword);
    }
}
