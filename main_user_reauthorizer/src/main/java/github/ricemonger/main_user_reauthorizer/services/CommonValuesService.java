package github.ricemonger.main_user_reauthorizer.services;

import github.ricemonger.main_user_reauthorizer.services.configurations.MainUserConfiguration;
import github.ricemonger.main_user_reauthorizer.services.configurations.UbiServiceConfiguration;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService {

    private final CommonValuesDatabaseService commonValuesDatabaseService;

    private final MainUserConfiguration mainUserConfiguration;

    private final UbiServiceConfiguration ubiServiceConfiguration;

    public void setMainUserAuthorization(AuthorizationDTO dto) {
        int expireTimeout = ubiServiceConfiguration.getExpireTimeout();
        commonValuesDatabaseService.setMainUserAuthorization(dto, expireTimeout);
    }

    public String getMainUserEmail() {
        return mainUserConfiguration.getEmail();
    }

    public String getMainUserPassword() {
        return mainUserConfiguration.getPassword();
    }

    public String getAuthorizationUrl() {
        return ubiServiceConfiguration.getAuthorizationUrl();
    }

    public String getContentType() {
        return ubiServiceConfiguration.getContentType();
    }

    public String getUserAgent() {
        return ubiServiceConfiguration.getUserAgent();
    }

    public String getUbiBaseAppId() {
        return ubiServiceConfiguration.getUbiBaseAppId();
    }

    public Integer getExpireTimeout() {
        return ubiServiceConfiguration.getExpireTimeout();
    }
}
