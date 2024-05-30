package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.ItemService;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.graphQl.dtos.marketableItems.Node;
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

}
