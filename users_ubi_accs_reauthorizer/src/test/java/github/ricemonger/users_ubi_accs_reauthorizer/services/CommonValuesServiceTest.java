package github.ricemonger.users_ubi_accs_reauthorizer.services;

import github.ricemonger.users_ubi_accs_reauthorizer.services.configurations.UbiServiceConfiguration;
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
    void getTrustedDevice_Id_should_handle_to_service() {
        String trustedDevice = "trustedDevice";
        when(ubiServiceConfiguration.getTrustedDeviceId()).thenReturn(trustedDevice);

        assertEquals(trustedDevice, commonValuesService.getTrustedDeviceId());
    }

    @Test
    void getTrustedDeviceFriendlyName_should_handle_to_service() {
        String friendlyName = "friendlyName";
        when(ubiServiceConfiguration.getTrustedDeviceFriendlyName()).thenReturn(friendlyName);

        assertEquals(friendlyName, commonValuesService.getTrustedDeviceFriendlyName());
    }

    @Test
    void getAuthorizationUrl_should_handle_to_service() {
        String authorizationUrl = "authorizationUrl";
        when(ubiServiceConfiguration.getAuthorizationUrl()).thenReturn(authorizationUrl);

        assertEquals(authorizationUrl, commonValuesService.getAuthorizationUrl());
    }

    @Test
    void getContentType_should_handle_to_service() {
        String contentType = "contentType";
        when(ubiServiceConfiguration.getContentType()).thenReturn(contentType);

        assertEquals(contentType, commonValuesService.getContentType());
    }

    @Test
    void getUserAgent_should_handle_to_service() {
        String userAgent = "userAgent";
        when(ubiServiceConfiguration.getUserAgent()).thenReturn(userAgent);

        assertEquals(userAgent, commonValuesService.getUserAgent());
    }

    @Test
    public void getUbiTwoFaAppId_should_handle_to_service() {
        String ubiTwoFaAppId = "ubiTwoFaAppId";
        when(ubiServiceConfiguration.getUbiTwoFaAppId()).thenReturn(ubiTwoFaAppId);

        assertEquals(ubiTwoFaAppId, commonValuesService.getUbiTwoFaAppId());
    }
}