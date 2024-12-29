package github.ricemonger.users_ubi_accs_reauthorizer.authorization;

import github.ricemonger.utils.exceptions.server.UbiCredentialsInnerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AesPasswordEncoderTest {

    @Autowired
    private AesPasswordEncoder aesPasswordEncoder;

    @Test
    public void decode_should_throw_exception_for_invalid_encoded_password() {
        String invalidEncodedPassword = "invalidEncodedPassword";
        assertThrows(UbiCredentialsInnerException.class, () -> aesPasswordEncoder.decode(invalidEncodedPassword));
    }

    @Test
    public void decode_should_throw_exception_for_null_encoded_password() {
        assertThrows(UbiCredentialsInnerException.class, () -> aesPasswordEncoder.decode(null));
    }
}