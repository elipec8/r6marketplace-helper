package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.services.UbiUserService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledUbiUsersReauthorization {

    private final UbiUserService ubiUserService;

    private final TelegramBotClientService telegramBotClientService;

    @Scheduled(fixedRate = 150 * 60 * 1000, initialDelay = 30 * 1000) // every 2.5h after 30s of delay

    public void reauthorizeUbiUsersAndNotifyAboutFailures() {
        List<UbiUserEntity> toNotify = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for(UbiUserEntity entity: toNotify){
            telegramBotClientService.notifyUserAboutUbiAuthorizationFailure(entity.getChatId(), entity.getEmail());
        }
    }
}
