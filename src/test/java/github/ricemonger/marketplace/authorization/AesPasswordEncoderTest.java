package github.ricemonger.marketplace.authorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    public void decode_should_return_decoded_password() {
        String password = "password";
        String encodedPassword = aesPasswordEncoder.encode(password);
        String decodedPassword = aesPasswordEncoder.decode(encodedPassword);
        assertEquals(password, decodedPassword);
    }
}