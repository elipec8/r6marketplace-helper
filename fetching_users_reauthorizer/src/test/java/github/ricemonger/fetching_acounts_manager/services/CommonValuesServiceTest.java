package github.ricemonger.fetching_acounts_manager.services;

import github.ricemonger.fetching_acounts_manager.services.configurations.UbiServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommonValuesServiceTest {
    @Autowired
    private CommonValuesService commonValuesService;
    @MockBean
    private UbiServiceConfiguration ubiServiceConfiguration;

    @Test
    public void getAuthorizationUrl_should_return_config_value() {
        when(ubiServiceConfiguration.getAuthorizationUrl()).thenReturn("http://localhost:8080");

        String result = commonValuesService.getAuthorizationUrl();

        assertEquals("http://localhost:8080", result);
    }

    @Test
    public void getContentType_should_return_config_value() {
        when(ubiServiceConfiguration.getContentType()).thenReturn("application/json");

        String result = commonValuesService.getContentType();

        assertEquals("application/json", result);
    }

    @Test
    public void getUserAgent_should_return_config_value() {
        when(ubiServiceConfiguration.getUserAgent()).thenReturn("Mozilla/5.0");

        String result = commonValuesService.getUserAgent();

        assertEquals("Mozilla/5.0", result);
    }

    @Test
    public void getUbiBaseAppId_should_return_config_value() {
        when(ubiServiceConfiguration.getUbiBaseAppId()).thenReturn("ubi_base_app_id");

        String result = commonValuesService.getUbiBaseAppId();

        assertEquals("ubi_base_app_id", result);
    }
}