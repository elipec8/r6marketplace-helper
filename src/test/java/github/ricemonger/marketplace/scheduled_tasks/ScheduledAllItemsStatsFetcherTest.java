package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.ItemService;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.telegramBot.BotService;
import github.ricemonger.utils.dtos.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScheduledAllItemsStatsFetcherTest {

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
    public void fetchAllItemStats_should_save_items_from_graphql_and_calculate() {
        List<Item> items = new ArrayList<>();

        when(graphQlClientService.fetchAllItemStats()).thenReturn(items);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(itemService).saveAll(same(items));
        verify(itemService).calculateItemsSaleHistoryStats();
    }

    @Test
    public void fetchAllItemStats_should_call_services_when_item_amount_increased() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());

        when(graphQlClientService.fetchAllItemStats()).thenReturn(items);
        when(redisService.getExpectedItemCount()).thenReturn(0);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(itemService).saveAll(same(items));
        verify(itemService).calculateItemsSaleHistoryStats();
        verify(redisService).setExpectedItemCount(1);
        verify(botService).notifyAllUsersAboutItemAmountIncrease(0, 1);
    }
}
