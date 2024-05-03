package github.ricemonger.marketplace.databases.redis.services;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainUserConfigurationTests {

    private MainUserConfiguration mainUserConfiguration;

    public void mainUserConfigurationValuesShouldNotBeNull() {
        assert mainUserConfiguration.getEmail() != null;
        assert mainUserConfiguration.getPassword() != null;
        assert mainUserConfiguration.getPlatform() != null;
    }
}
