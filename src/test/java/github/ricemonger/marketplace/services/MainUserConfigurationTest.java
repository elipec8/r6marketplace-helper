package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.configurations.MainUserConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainUserConfigurationTest {

    @Autowired
    private MainUserConfiguration mainUserConfiguration;

    @Test
    public void mainUserConfigurationValuesShouldNotBeNull() {
        assert mainUserConfiguration.getEmail() != null;
        assert mainUserConfiguration.getPassword() != null;
        assert mainUserConfiguration.getPlatform() != null;
    }
}
