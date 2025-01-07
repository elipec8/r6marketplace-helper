package github.ricemonger.users_ubi_accs_reauthorizer.services.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UbiServiceConfiguration {
    @Value("${ubi.urls.authorization}")
    private String authorizationUrl;
    @Value("${ubi.urls.twoFaCodeToSMS}")
    private String twoFaCodeToSmsUrl;
    @Value("${ubi.session.contentType}")
    private String contentType;
    @Value("${ubi.session.userAgent}")
    private String userAgent;
    @Value("${ubi.session.twoFaAppId}")
    private String ubiTwoFaAppId;
    @Value("${ubi.session.spaceId}")
    private String ubiSpaceId;
    @Value("${ubi.session.regionId}")
    private String ubiRegionId;
    @Value("${ubi.session.localeCode}")
    private String UbiLocaleCode;
    @Value("${ubi.session.trustedDeviceId}")
    private String trustedDeviceId;
    @Value("${ubi.session.trustedDeviceFriendlyName}")
    private String trustedDeviceFriendlyName;
}
