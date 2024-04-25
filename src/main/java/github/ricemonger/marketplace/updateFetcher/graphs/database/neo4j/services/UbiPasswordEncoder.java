package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.services;

import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities.UbiCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

@Component
public class UbiPasswordEncoder{

    @Value("${ubi.password.encryption.key}")
    private String encryptionKey;

    public String getEncodedPassword(String password) {
        String encodedPassword;
        try {
            Key aesKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            encodedPassword = Base64.getEncoder().encodeToString(encrypted);
            return encodedPassword;
        } catch (GeneralSecurityException e) {
            throw new UbiCredentialsException(e);
        }
    }

    public String getDecodedPassword(String encodedPassword) {
        try {
            Key aesKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encodedPassword)));
        } catch (GeneralSecurityException e) {
            throw new UbiCredentialsException(e);
        }
    }
}
