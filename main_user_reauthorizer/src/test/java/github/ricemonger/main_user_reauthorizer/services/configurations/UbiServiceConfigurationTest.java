package github.ricemonger.main_user_reauthorizer.services.configurations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UbiServiceConfigurationTest {
    @Autowired
    private UbiServiceConfiguration ubiServiceConfiguration;

    @Test
    public void values_should_not_be_null() {
        assertNotNull(ubiServiceConfiguration.getAuthorizationUrl());
        assertNotNull(ubiServiceConfiguration.getContentType());
        assertNotNull(ubiServiceConfiguration.getUserAgent());
        assertNotNull(ubiServiceConfiguration.getUbiBaseAppId());
        assertNotNull(ubiServiceConfiguration.getUbiSpaceId());
        assertNotNull(ubiServiceConfiguration.getUbiRegionId());
        assertNotNull(ubiServiceConfiguration.getUbiLocaleCode());
        assertNotNull(ubiServiceConfiguration.getExpireTimeout());
    }
}