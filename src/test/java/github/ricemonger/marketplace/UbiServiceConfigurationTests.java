package github.ricemonger.marketplace;

import github.ricemonger.marketplace.graphs.UbiServiceConfiguration;
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
        assertNotNull(ubiServiceConfiguration.getPlatform());
        assertNotNull(ubiServiceConfiguration.getAuthorization());
        assertNotNull(ubiServiceConfiguration.getRegionId());
        assertNotNull(ubiServiceConfiguration.getLocaleCode());
        assertNotNull(ubiServiceConfiguration.getUbiAppId());
        assertNotNull(ubiServiceConfiguration.getSessionId());
        assertNotNull(ubiServiceConfiguration.getProfileId());
    }
}
