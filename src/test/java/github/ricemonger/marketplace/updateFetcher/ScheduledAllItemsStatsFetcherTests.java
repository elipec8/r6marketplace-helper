package github.ricemonger.marketplace.updateFetcher;

import github.ricemonger.marketplace.graphs.GraphQlClientService;
import github.ricemonger.marketplace.graphs.database.neo4j.services.ItemService;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.Node;
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
    private ItemService itemService;

    @Autowired
    private ScheduledAllItemsStatsFetcher scheduledAllItemsStatsFetcher;

    @Test
    public void fetchAllItemsStatsShouldCallServices(){
        List<Node> nodes = new ArrayList<>();
        when(graphQlClientService.fetchAllItemStats()).thenReturn(nodes);
        reset(graphQlClientService);
        reset(itemService);

        scheduledAllItemsStatsFetcher.fetchAllItemStats();

        verify(graphQlClientService).fetchAllItemStats();

        verify(itemService).saveAll(nodes);
    }
}
