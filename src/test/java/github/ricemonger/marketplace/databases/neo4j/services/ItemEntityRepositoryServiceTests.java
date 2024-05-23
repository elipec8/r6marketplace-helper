package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryEntityRepository;
import github.ricemonger.marketplace.databases.postgres.services.ItemEntityRepositoryService;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.databases.postgres.services.ItemDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemEntityRepositoryServiceTests {

    @MockBean
    private ItemEntityRepository itemRepository;

    @MockBean
    private ItemSaleEntityRepository itemSaleRepository;

    @MockBean
    private ItemSaleHistoryEntityRepository itemSaleHistoryRepository;

    @MockBean
    private ItemDtoMapper mapper;

    @Autowired
    private ItemEntityRepositoryService itemRepositoryService;

    @Test
    public void saveAllShouldMapAndCallRepositorySaveAll() {
        Set<Node> nodes = new HashSet<>();
        Set<ItemEntity> items = new HashSet<>();
        when(mapper.nodesDTOToItemEntities(nodes)).thenReturn(items);

        itemRepositoryService.saveAll(nodes);

        verify(mapper).nodesDTOToItemEntities(nodes);

        verify(itemRepository).saveAll(items);
    }

    @Test
    public void getSpeculativeItemsShouldGetFromRepositoryByValuesByExpectedProfit() {
        ItemEntity itemNode1 = new ItemEntity();
        itemNode1.setExpectedProfit(10);
        itemNode1.setExpectedProfitPercentage(10);
        itemNode1.setMinSellPrice(0);

        ItemEntity itemNode2 = new ItemEntity();
        itemNode2.setExpectedProfit(0);
        itemNode2.setExpectedProfitPercentage(10);
        itemNode2.setMinSellPrice(10);

        ItemEntity itemNode3 = new ItemEntity();
        itemNode3.setExpectedProfit(10);
        itemNode3.setExpectedProfitPercentage(10);
        itemNode3.setMinSellPrice(1000);

        ItemEntity itemNode4 = new ItemEntity();
        itemNode4.setExpectedProfit(10);
        itemNode4.setExpectedProfitPercentage(0);
        itemNode4.setMinSellPrice(10);

        ItemEntity itemNode5 = new ItemEntity();
        itemNode5.setExpectedProfit(10);
        itemNode5.setExpectedProfitPercentage(10);
        itemNode5.setMinSellPrice(10);

        List<ItemEntity> items = new ArrayList<>();
        items.add(itemNode1);
        items.add(itemNode2);
        items.add(itemNode3);
        items.add(itemNode4);
        items.add(itemNode5);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemEntity> result = itemRepositoryService.findAll(new ArrayList<>(),1, 1, 1, 100);

        verify(itemRepository).findAll();

        assertEquals(1, result.size());
        assertEquals(itemNode5, result.get(0));
    }

    @Test
    public void calculateItemSaleStatsShouldCallRepositories(){
        itemRepositoryService.calculateItemsSaleStats();

        verify(itemRepository).findAll();

        verify(itemSaleRepository).findAll();

        verify(itemSaleHistoryRepository).saveAll(any());
    }
}
