package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.UbiUserService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import github.ricemonger.utils.dtos.UbiUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledUbiUsersReauthorization {

    private final UbiUserService ubiUserService;

    private final TelegramBotClientService telegramBotClientService;

    @Scheduled(fixedRate = 150 * 60 * 1000, initialDelay = 30 * 1000) // every 2.5h after 30s of delay

    public void reauthorizeUbiUsersAndNotifyAboutFailures() {
        Collection<UbiUser> toNotify = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for (UbiUser user : toNotify) {
            telegramBotClientService.notifyUserAboutUbiAuthorizationFailure(user.getChatId(), user.getEmail());
        }
    }
}
