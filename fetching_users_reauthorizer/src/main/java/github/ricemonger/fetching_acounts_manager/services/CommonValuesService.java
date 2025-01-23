package github.ricemonger.fetching_acounts_manager.services;

import github.ricemonger.fetching_acounts_manager.services.configurations.UbiServiceConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonValuesService {

    private final UbiServiceConfiguration ubiServiceConfiguration;

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
}
