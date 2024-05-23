package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.postgres.services.ItemEntityRepositoryService;
import github.ricemonger.marketplace.databases.postgres.services.ItemService;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.utils.exceptions.UbiUserEntityDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ItemServiceTests {

    @MockBean
    private ItemEntityRepositoryService itemRepositoryService;

    @SpyBean
    private ItemService itemService;

    @Test
    public void saveAll() {
        Collection<Node> nodeDTOs = new ArrayList<>();
        itemService.saveAll(nodeDTOs);

        verify(itemRepositoryService).saveAll(nodeDTOs);
    }

    @Test
    public void getSpeculativeItemsByExpectedProfit() throws UbiUserEntityDoesntExistException {

        itemService.getSpeculativeItemsByExpectedProfit(100, 10, 1000, 10000);

        verify(itemRepositoryService, times(1)).getSpeculativeItemsByExpectedProfit(
                any(),
                eq(100),
                eq(10),
                eq(1000),
                eq(10000));
    }

    @Test
    public void calculateItemsSaleStats() {
        itemService.calculateItemsSaleStats();

        verify(itemRepositoryService).calculateItemsSaleStats();
    }
}
