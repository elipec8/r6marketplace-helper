package github.ricemonger.fetching_acounts_manager.scheduled_tasks;


import github.ricemonger.fetching_acounts_manager.authorization.AuthorizationService;
import github.ricemonger.fetching_acounts_manager.services.AuthorizedUbiUsersService;
import github.ricemonger.fetching_acounts_manager.services.CredentialsJsonDeserializer;
import github.ricemonger.fetching_acounts_manager.services.DTOs.UbiUserCredentials;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledFetchingAccountsReauthorization {

    private final AuthorizationService authorizationService;

    private final CredentialsJsonDeserializer credentialsJsonDeserializer;

    private final AuthorizedUbiUsersService authorizedUbiUsersService;

    @Scheduled(fixedRateString = "${app.scheduling.fixedRate}", initialDelayString = "${app.scheduling.initialDelay}")
    public void reauthorizeFetchingUsersAndSave() {

        List<UbiUserCredentials> credentialsList = credentialsJsonDeserializer.getCredentials();

        List<AuthorizationDTO> authorizationDTOs = new ArrayList<>();

        for (UbiUserCredentials credentials : credentialsList) {
            try {
                authorizationDTOs.add(authorizationService.authorizeAndGetBaseAuthorizedDTO(credentials.getEmail(), credentials.getPassword()));
            } catch (Exception e) {
                log.error("Ubi account with Email: {} could not be authorized cause of: ", credentials.getEmail(), e);
            }
        }

        authorizedUbiUsersService.saveAuthorizedUbiUsersAndRemoveOthers(authorizationDTOs);
    }
}
