package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.TelegramUserUbiAccountEntryService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import github.ricemonger.utils.dtos.UbiAccountAuthorizationEntryWithTelegram;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ScheduledUbiUsersReauthorization {

    private final TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryService;

    private final TelegramBotClientService telegramBotClientService;

    @Scheduled(fixedRate = 150 * 60 * 1000, initialDelay = 90 * 1000) // every 2.5h after 1.5m of delay

    public void reauthorizeUbiUsersAndNotifyAboutFailures() {
        Collection<UbiAccountAuthorizationEntryWithTelegram> toNotify = telegramUserUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for (UbiAccountAuthorizationEntryWithTelegram user : toNotify) {
            telegramBotClientService.sendText(
                    user.getChatId(),
                    String.format("Your Ubisoft account with email:%s could no be authorized. Please check for errors.", user.getEmail()));
        }
    }
}
