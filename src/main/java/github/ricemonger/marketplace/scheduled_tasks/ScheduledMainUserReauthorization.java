package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.redis.services.MainUserConfiguration;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledMainUserReauthorization {

    private final RedisService redisService;

    private final MainUserConfiguration mainUserConfiguration;

    private final AuthorizationService authorizationService;

    @Scheduled(fixedRate = 150 * 60 * 1000) // every 2.5h
    public void reauthorizeMainUserAndSave() {
        AuthorizationDTO authorizationDTO = authorizationService.authorizeAndGetDTO(mainUserConfiguration.getEmail(), mainUserConfiguration.getPassword());

        redisService.setMainUserAuthorization(authorizationDTO, mainUserConfiguration.getExpireTimeout());
    }
}
