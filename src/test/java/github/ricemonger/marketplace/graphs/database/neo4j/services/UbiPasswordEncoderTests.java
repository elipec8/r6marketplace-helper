package github.ricemonger.marketplace.graphs.database.neo4j.services;

import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.services.UbiPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UbiPasswordEncoderTests {

    @Autowired
    private UbiPasswordEncoder ubiPasswordEncoder;

    @Test
    public void encryptionShouldReturnEncodedPassword() {
        assertNotEquals(ubiPasswordEncoder.getEncodedPassword("password"), "password");
    }

    @Test
    public void decryptionShouldReturnOriginalPassword() {
        String password = "password";
        String encodedPassword = ubiPasswordEncoder.getEncodedPassword(password);

        assertEquals(ubiPasswordEncoder.getDecodedPassword(encodedPassword), password);
    }
}
