package github.ricemonger.users_ubi_accs_reauthorizer.scheduled_tasks;


import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UnauthorizedAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.NotificationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.UserUbiAccountEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ScheduledAllUbiUsersReauthorization {

    private final UserUbiAccountEntryService userUbiAccountEntryService;

    private final NotificationService telegramBotService;

    @Scheduled(fixedRateString = "${app.scheduling.fixedRate}", initialDelayString = "${app.scheduling.initialDelay}")
    public void reauthorizeAllUbiUsersAndNotifyAboutFailures() {
        Collection<UnauthorizedAccount> toNotify = userUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for (UnauthorizedAccount user : toNotify) {
            telegramBotService.sendPrivateNotification(user.getId(), String.format("Your Ubisoft account with email:%s could no be authorized. Please check for errors.", user.getEmail()));
        }
    }
}
