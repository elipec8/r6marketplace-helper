package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.utils.exceptions.AesPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class AesPasswordEncoderTests {

    @Autowired
    private AesPasswordEncoder AESPasswordEncoder;

    @Test
    public void encryptionShouldReturnEncodedPassword() {
        assertNotEquals(AESPasswordEncoder.encode("password"), "password");
    }

    @Test
    public void decryptionShouldReturnOriginalPassword() {
        String password = "password";
        String encodedPassword = AESPasswordEncoder.encode(password);

        assertEquals(AESPasswordEncoder.decode(encodedPassword), password);
    }
}
