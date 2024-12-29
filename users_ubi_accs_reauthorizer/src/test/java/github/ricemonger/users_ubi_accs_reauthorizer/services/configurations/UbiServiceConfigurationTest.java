package github.ricemonger.users_ubi_accs_reauthorizer.services.configurations;

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
        assertNotNull(ubiServiceConfiguration.getGraphqlUrl());
        assertNotNull(ubiServiceConfiguration.getTrustedDeviceFriendlyName());
        assertNotNull(ubiServiceConfiguration.getTrustedDeviceId());
        assertNotNull(ubiServiceConfiguration.getUbiLocaleCode());
        assertNotNull(ubiServiceConfiguration.getUbiRegionId());
        assertNotNull(ubiServiceConfiguration.getUbiSpaceId());
        assertNotNull(ubiServiceConfiguration.getUbiTwoFaAppId());
        assertNotNull(ubiServiceConfiguration.getUserAgent());
        assertNotNull(ubiServiceConfiguration.getTwoFaCodeToSmsUrl());
    }
}