package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
import github.ricemonger.marketplace.databases.neo4j.services.UbiUserService;
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

    @Scheduled(fixedRate = 150 * 60 * 1000, initialDelay = 45 * 1000) // every 2.5h after 45s of delay

    public void reauthorizeUbiUsersAndNotifyAboutFailures() {
        List<UbiUserNode> removed = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        for(UbiUserNode entity: removed){
            telegramBotClientService.notifyUserAboutUbiAuthorizationFailure(entity.getLinkedTelegramUser().getChatId(), entity.getEmail());
        }
    }
}
