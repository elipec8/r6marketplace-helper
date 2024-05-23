package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.ItemNode;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemNodeRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemSaleHistoryNodeRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.ItemSaleNodeRepository;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.service.ItemDtoMapper;
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
    private ItemNodeRepository itemNodeRepository;

    @MockBean
    private ItemSaleNodeRepository itemSaleNodeRepository;

    @MockBean
    private ItemSaleHistoryNodeRepository itemSaleHistoryNodeRepository;

    @MockBean
    private ItemDtoMapper mapper;

    @Autowired
    private ItemNodeRepositoryService itemNodeRepositoryService;

    @Test
    public void saveAllShouldMapAndCallRepositorySaveAll() {
        Set<Node> nodes = new HashSet<>();
        Set<ItemNode> items = new HashSet<>();
        when(mapper.nodesDTOToItemNodes(nodes)).thenReturn(items);

        itemNodeRepositoryService.saveAll(nodes);

        verify(mapper).nodesDTOToItemNodes(nodes);

        verify(itemNodeRepository).saveAll(items);
    }

    @Test
    public void getSpeculativeItemsShouldGetFromRepositoryByValuesByExpectedProfit() {
        ItemNode itemNode1 = new ItemNode();
        itemNode1.setExpectedProfit(10);
        itemNode1.setExpectedProfitPercentage(10);
        itemNode1.setMinSellPrice(0);

        ItemNode itemNode2 = new ItemNode();
        itemNode2.setExpectedProfit(0);
        itemNode2.setExpectedProfitPercentage(10);
        itemNode2.setMinSellPrice(10);

        ItemNode itemNode3 = new ItemNode();
        itemNode3.setExpectedProfit(10);
        itemNode3.setExpectedProfitPercentage(10);
        itemNode3.setMinSellPrice(1000);

        ItemNode itemNode4 = new ItemNode();
        itemNode4.setExpectedProfit(10);
        itemNode4.setExpectedProfitPercentage(0);
        itemNode4.setMinSellPrice(10);

        ItemNode itemNode5 = new ItemNode();
        itemNode5.setExpectedProfit(10);
        itemNode5.setExpectedProfitPercentage(10);
        itemNode5.setMinSellPrice(10);

        List<ItemNode> items = new ArrayList<>();
        items.add(itemNode1);
        items.add(itemNode2);
        items.add(itemNode3);
        items.add(itemNode4);
        items.add(itemNode5);
        when(itemNodeRepository.findAll()).thenReturn(items);

        List<ItemNode> result = itemNodeRepositoryService.getSpeculativeItemsByExpectedProfit(1, 1, 1, 100);

        verify(itemNodeRepository).findAll();

        assertEquals(1, result.size());
        assertEquals(itemNode5, result.get(0));
    }

    @Test
    public void calculateItemSaleStatsShouldCallRepositories(){
        itemNodeRepositoryService.calculateItemsSaleStats();

        verify(itemNodeRepository).findAll();

        verify(itemSaleNodeRepository).findAll();

        verify(itemSaleHistoryNodeRepository).saveAll(any());
    }
}
