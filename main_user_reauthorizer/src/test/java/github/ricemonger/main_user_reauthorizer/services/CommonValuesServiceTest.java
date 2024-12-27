package github.ricemonger.main_user_reauthorizer.services;

import github.ricemonger.main_user_reauthorizer.services.configurations.MainUserConfiguration;
import github.ricemonger.main_user_reauthorizer.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.abstractions.CommonValuesDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommonValuesServiceTest {
    @Autowired
    private CommonValuesService commonValuesService;
    @MockBean
    private CommonValuesDatabaseService commonValuesDatabaseService;
    @MockBean
    private MainUserConfiguration mainUserConfiguration;
    @MockBean
    private UbiServiceConfiguration ubiServiceConfiguration;

    @Test
    void setMainUserAuthorization_should_handle_to_service() {
        commonValuesService.setMainUserAuthorization(new AuthorizationDTO());

        verify(commonValuesDatabaseService).setMainUserAuthorization(new AuthorizationDTO(), 0);
    }

    @Test
    void getMainUserEmail_should_handle_to_service() {
        String email = "email";
        when(mainUserConfiguration.getEmail()).thenReturn(email);

        assertEquals(email, commonValuesService.getMainUserEmail());
    }

    @Test
    void getMainUserPassword_should_handle_to_service() {
        String password = "password";
        when(mainUserConfiguration.getPassword()).thenReturn(password);

        assertEquals(password, commonValuesService.getMainUserPassword());
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
    void getUbiBaseAppId_should_handle_to_service() {
        String ubiAppId = "ubiAppId";
        when(ubiServiceConfiguration.getUbiBaseAppId()).thenReturn(ubiAppId);

        assertEquals(ubiAppId, commonValuesService.getUbiBaseAppId());
    }

    @Test
    void getExpireTimeout_should_handle_to_service() {
        int expireTimeout = 10;
        when(ubiServiceConfiguration.getExpireTimeout()).thenReturn(expireTimeout);

        assertEquals(expireTimeout, commonValuesService.getExpireTimeout());
    }
}