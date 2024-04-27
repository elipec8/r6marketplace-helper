package github.ricemonger.marketplace.mainUserAuth;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledMainUserAuthorization {

    private final AuthorizationService authorizationService;

    @Scheduled(fixedRate = 3 * 60 * 60 * 1000) // every 3 hours
    public void authorizeMainUser() {
        log.info("Authorizing main user");
    }

}
