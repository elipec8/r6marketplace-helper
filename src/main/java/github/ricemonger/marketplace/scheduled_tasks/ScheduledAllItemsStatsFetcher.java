package github.ricemonger.marketplace.scheduled_tasks;


import github.ricemonger.marketplace.databases.neo4j.services.ItemService;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.telegramBot.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsStatsFetcher {

    private final GraphQlClientService graphQlClientService;

    private final ItemService itemService;

    private final RedisService redisService;

    private final BotService botService;

    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 15 * 1000) // every 5m after 15s of delay
    public void fetchAllItemStats() {

        int expectedItemCount = redisService.getExpectedItemCount();

        Collection<Node> nodes = graphQlClientService.fetchAllItemStats(expectedItemCount);

        if (nodes.size() < expectedItemCount) {
            log.error("Fetched {} items' stats, expected {}", nodes.size(), expectedItemCount);
        } else if (nodes.size() > expectedItemCount) {
            log.info("Fetched {} items' stats, expected {}", nodes.size(), expectedItemCount);
            onItemsAmountIncrease(expectedItemCount, nodes.size());
        }

        itemService.saveAll(nodes);

        log.info("Fetched {} items' stats", nodes.size());
    }

    private void onItemsAmountIncrease(int expectedItemCount,int fetchedItemsCount) {
        redisService.setExpectedItemCount(fetchedItemsCount);

        botService.notifyAllUsersAboutItemAmountIncrease(expectedItemCount, fetchedItemsCount);
    }
}
