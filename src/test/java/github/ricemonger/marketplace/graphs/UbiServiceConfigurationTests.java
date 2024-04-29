package github.ricemonger.marketplace.graphs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UbiServiceConfigurationTests {

    @Autowired
    private UbiServiceConfiguration ubiServiceConfiguration;

    @Test
    public void ubiServiceConfigurationPropertiesShouldBeAutowired(){
        assertNotNull(ubiServiceConfiguration.getUpdateItemsUrl());
        assertNotNull(ubiServiceConfiguration.getAuthorizationUrl());
        assertNotNull(ubiServiceConfiguration.getContentType());
        assertNotNull(ubiServiceConfiguration.getUserAgent());
        assertNotNull(ubiServiceConfiguration.getRegionId());
        assertNotNull(ubiServiceConfiguration.getLocaleCode());
        assertNotNull(ubiServiceConfiguration.getUbiAppId());
    }
}
