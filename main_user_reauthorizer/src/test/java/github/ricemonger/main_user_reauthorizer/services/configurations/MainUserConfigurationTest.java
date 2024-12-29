package github.ricemonger.main_user_reauthorizer.services.configurations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MainUserConfigurationTest {
    @Autowired
    private MainUserConfiguration mainUserConfiguration;

    @Test
    public void mainUserConfigurationValuesShouldNotBeNull() {
        assertNotNull(mainUserConfiguration.getEmail());
        assertNotNull(mainUserConfiguration.getPassword());
        assertNotNull(mainUserConfiguration.getPlatform());
    }
}