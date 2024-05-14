package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemServiceTests {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private DTOsToEntityMapper mapper;

    @Autowired
    private ItemService itemService;

    @Test
    public void saveAllShouldMapAndCallRepositorySaveAll() {
        List<Node> nodes = new ArrayList<>();
        List<ItemEntity> items = new ArrayList<>();
        when(mapper.nodesDTOToItemEntities(nodes)).thenReturn(items);

        itemService.saveAll(nodes);

        verify(mapper).nodesDTOToItemEntities(nodes);

        verify(itemRepository).saveAll(items);
    }

    @Test
    public void getSpeculativeItemsShouldGetFromRepositoryByValuesByExpectedProfit() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setExpectedProfit(10);
        itemEntity1.setExpectedProfitPercentage(10);
        itemEntity1.setMinSellPrice(0);

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setExpectedProfit(0);
        itemEntity2.setExpectedProfitPercentage(10);
        itemEntity2.setMinSellPrice(10);

        ItemEntity itemEntity3 = new ItemEntity();
        itemEntity3.setExpectedProfit(10);
        itemEntity3.setExpectedProfitPercentage(10);
        itemEntity3.setMinSellPrice(1000);

        ItemEntity itemEntity4 = new ItemEntity();
        itemEntity4.setExpectedProfit(10);
        itemEntity4.setExpectedProfitPercentage(0);
        itemEntity4.setMinSellPrice(10);

        ItemEntity itemEntity5 = new ItemEntity();
        itemEntity5.setExpectedProfit(10);
        itemEntity5.setExpectedProfitPercentage(10);
        itemEntity5.setMinSellPrice(10);

        List<ItemEntity> items = new ArrayList<>();
        items.add(itemEntity1);
        items.add(itemEntity2);
        items.add(itemEntity3);
        items.add(itemEntity4);
        items.add(itemEntity5);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemEntity> result = itemService.getSpeculativeItemsByExpectedProfit(1, 1, 1, 100);

        verify(itemRepository).findAll();

        assertEquals(1, result.size());
        assertEquals(itemEntity5, result.get(0));
    }
}
