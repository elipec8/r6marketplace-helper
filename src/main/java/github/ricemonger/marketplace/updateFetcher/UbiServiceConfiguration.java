package github.ricemonger.marketplace.updateFetcher;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UbiServiceConfiguration {

    @Value("${ubi.session.spaceId}")
    private String spaceId;
    @Value("${ubi.session.authorization}")
    private String authorization;
    @Value("${ubi.session.regionId}")
    private String regionId;
    @Value("${ubi.session.localeCode}")
    private String localeCode;
    @Value("${ubi.session.appId}")
    private String ubiAppId;
    @Value("${ubi.session.sessionId}")
    private String sessionId;
    @Value("${ubi.session.profileId}")
    private String profileId;

}
