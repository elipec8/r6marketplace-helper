package github.ricemonger.fetching_acounts_manager.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.ricemonger.fetching_acounts_manager.services.DTOs.UbiUserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CredentialsJsonDeserializer {

    private final ObjectMapper objectMapper;

    public List<UbiUserCredentials> getCredentials() {
        try {
            InputStream inputStream = new ClassPathResource("fetching_accounts_credentials.json").getInputStream();
            Map<String, List<UbiUserCredentials>> credentialsMap = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<UbiUserCredentials>>>() {
            });
            return credentialsMap.get("ubi_credentials");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read credentials from JSON file", e);
        }
    }
}
