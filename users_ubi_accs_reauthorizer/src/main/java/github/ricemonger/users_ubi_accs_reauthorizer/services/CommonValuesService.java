package github.ricemonger.users_ubi_accs_reauthorizer.services;


import github.ricemonger.users_ubi_accs_reauthorizer.services.configurations.UbiServiceConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService {

    private final UbiServiceConfiguration ubiServiceConfiguration;

    public String getTrustedDeviceId() {
        return ubiServiceConfiguration.getTrustedDeviceId();
    }

    public String getTrustedDeviceFriendlyName() {
        return ubiServiceConfiguration.getTrustedDeviceFriendlyName();
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

    public String getUbiTwoFaAppId() {
        return ubiServiceConfiguration.getUbiTwoFaAppId();
    }
}
