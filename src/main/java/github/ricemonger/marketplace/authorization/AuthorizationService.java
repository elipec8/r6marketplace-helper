package github.ricemonger.marketplace.authorization;

import github.ricemonger.marketplace.graphs.UbiServiceConfiguration;
import github.ricemonger.marketplace.graphs.database.redis.services.MainUserConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UbiServiceConfiguration ubiServiceConfiguration;

    private final MainUserConfiguration mainUserConfiguration;

    public String createAndGetMainUserAuthorizationToken() {

    }
}
