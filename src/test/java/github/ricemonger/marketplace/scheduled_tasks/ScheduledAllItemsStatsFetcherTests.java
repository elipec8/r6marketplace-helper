package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.databases.neo4j.services.ItemService;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.telegramBot.BotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ScheduledAllItemsStatsFetcherTests {

    @MockBean
    private GraphQlClientService graphQlClientService;

    @MockBean
    private BotService botService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private RedisService redisService;

    @Autowired
    private ScheduledAllItemsStatsFetcher scheduledAllItemsStatsFetcher;

    @Test
    public void fetchAllItemsStatsShouldCallServices(){
        int expectedItemCount = 1;

        fetchAllItemsStatsShouldCallGraphQlAndItemServices(expectedItemCount);

        verify(redisService, never()).setExpectedItemCount(anyInt());
        verify(botService, never()).notifyAllUsersAboutItemAmountIncrease(anyInt(), anyInt());
    }

    @Test
    public void fetchAllItemsStatsShouldCallBotServiceIfItemsAmountIncreased(){
        int expectedItemCount = 0;

        fetchAllItemsStatsShouldCallGraphQlAndItemServices(expectedItemCount);

        verify(redisService).setExpectedItemCount(1);
        verify(botService).notifyAllUsersAboutItemAmountIncrease(expectedItemCount, 1);
    }

    private void fetchAllItemsStatsShouldCallGraphQlAndItemServices(int expectedItemCount){
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node());

        when(redisService.getExpectedItemCount()).thenReturn(expectedItemCount);
        when(graphQlClientService.fetchAllItemStats(expectedItemCount)).thenReturn(nodes);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(graphQlClientService).fetchAllItemStats(expectedItemCount);

        verify(itemService).saveAll(nodes);
    }
}
