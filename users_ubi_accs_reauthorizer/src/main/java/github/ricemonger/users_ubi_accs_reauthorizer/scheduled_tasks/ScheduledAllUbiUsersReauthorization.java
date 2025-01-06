package github.ricemonger.users_ubi_accs_reauthorizer.scheduled_tasks;


import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserToNotify;
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

    @Scheduled(fixedRate = 150 * 60 * 1000, initialDelay = 90 * 1000) // every 2.5h after 1.5m of delay
    public void reauthorizeAllUbiUsersAndNotifyAboutFailures() {
        Collection<UserToNotify> toNotify = userUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for (UserToNotify user : toNotify) {
            telegramBotService.sendPrivateNotification(user.getId(), String.format("Your Ubisoft account with email:%s could no be authorized. Please check for errors.", user.getEmail()));
        }
    }
}
