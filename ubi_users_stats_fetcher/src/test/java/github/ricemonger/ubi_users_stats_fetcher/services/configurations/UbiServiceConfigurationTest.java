package github.ricemonger.ubi_users_stats_fetcher.services.configurations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UbiServiceConfigurationTest {
    @Autowired
    private UbiServiceConfiguration ubiServiceConfiguration;

    @Test
    public void values_should_be_initialized() {
        assertNotNull(ubiServiceConfiguration);
    }
}