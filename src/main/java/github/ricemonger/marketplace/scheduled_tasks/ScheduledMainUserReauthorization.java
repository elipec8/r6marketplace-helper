package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledMainUserReauthorization {

    private final CommonValuesService commonValuesService;

    private final AuthorizationService authorizationService;

    @Scheduled(fixedRate = 150 * 60 * 1000) // every 2.5h
    public void reauthorizeMainUserAndSave() {
        AuthorizationDTO authorizationDTO = authorizationService.authorizeAndGetDTO(commonValuesService.getMainUserEmail(),
                commonValuesService.getMainUserPassword());

        commonValuesService.setMainUserAuthorization(authorizationDTO);
    }
}
