package github.ricemonger.trades_manager.services.configurations;

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
        assertNotNull(ubiServiceConfiguration.getMinUncommonPrice());
        assertNotNull(ubiServiceConfiguration.getMaxUncommonPrice());
        assertNotNull(ubiServiceConfiguration.getMinRarePrice());
        assertNotNull(ubiServiceConfiguration.getMaxRarePrice());
        assertNotNull(ubiServiceConfiguration.getMinEpicPrice());
        assertNotNull(ubiServiceConfiguration.getMaxEpicPrice());
        assertNotNull(ubiServiceConfiguration.getMinLegendaryPrice());
        assertNotNull(ubiServiceConfiguration.getMaxLegendaryPrice());
    }
}