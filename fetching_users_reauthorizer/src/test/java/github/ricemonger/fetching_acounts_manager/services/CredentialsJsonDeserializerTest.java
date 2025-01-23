package github.ricemonger.fetching_acounts_manager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.ricemonger.fetching_acounts_manager.services.DTOs.UbiUserCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CredentialsJsonDeserializerTest {
    @Autowired
    private CredentialsJsonDeserializer credentialsJsonDeserializer;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCredentials_should_properly_map_from_json() {
        List<UbiUserCredentials> credentials = credentialsJsonDeserializer.getCredentials();

        assertEquals(2, credentials.size());
        assertTrue(credentials.stream().anyMatch(c -> c.getEmail().equals("testEmail@gmail.com") && c.getPassword().equals("testPassword")));
        assertTrue(credentials.stream().anyMatch(c -> c.getEmail().equals("testEmail1@gmail.com") && c.getPassword().equals("testPassword1")));
    }
}