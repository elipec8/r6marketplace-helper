package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.TelegramLinkedUbiUserService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ScheduledUbiUsersReauthorization {

    private final TelegramLinkedUbiUserService telegramLinkedUbiUserService;

    private final TelegramBotClientService telegramBotClientService;

    @Scheduled(fixedRate = 150 * 60 * 1000, initialDelay = 30 * 1000) // every 2.5h after 2m of delay

    public void reauthorizeUbiUsersAndNotifyAboutFailures() {
        Collection<UbiAccountWithTelegram> toNotify = telegramLinkedUbiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for (UbiAccountWithTelegram user : toNotify) {
            telegramBotClientService.notifyUserAboutUbiAuthorizationFailure(user.getChatId(), user.getEmail());
        }
    }
}
