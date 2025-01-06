package github.ricemonger.marketplace.authorization;

import github.ricemonger.utils.exceptions.server.UbiCredentialsInnerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AesPasswordEncoderTest {

    @Autowired
    private AesPasswordEncoder aesPasswordEncoder;

    @Test
    public void encode_should_return_encoded_password() {
        String password = "password";
        String encodedPassword = aesPasswordEncoder.encode(password);
        assertNotEquals(password, encodedPassword);
    }

    @Test
    public void encode_should_return_different_encoded_passwords_for_different_inputs() {
        String password1 = "password1";
        String password2 = "password2";
        String encodedPassword1 = aesPasswordEncoder.encode(password1);
        String encodedPassword2 = aesPasswordEncoder.encode(password2);
        assertNotEquals(encodedPassword1, encodedPassword2);
    }

    @Test
    public void encode_should_throw_exception_for_null_password() {
        assertThrows(UbiCredentialsInnerException.class, () -> aesPasswordEncoder.encode(null));
    }

    @Test
    public void decode_should_throw_exception_for_invalid_encoded_password() {
        String invalidEncodedPassword = "invalidEncodedPassword";
        assertThrows(UbiCredentialsInnerException.class, () -> aesPasswordEncoder.decode(invalidEncodedPassword));
    }

    @Test
    public void decode_should_return_decoded_password() {
        String password = "password";
        String encodedPassword = aesPasswordEncoder.encode(password);
        String decodedPassword = aesPasswordEncoder.decode(encodedPassword);
        assertEquals(password, decodedPassword);
    }

    @Test
    public void decode_should_throw_exception_for_null_encoded_password() {
        assertThrows(UbiCredentialsInnerException.class, () -> aesPasswordEncoder.decode(null));
    }
}