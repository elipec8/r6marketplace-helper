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
        when(mapper.NodesDTOToItemEntities(nodes)).thenReturn(items);

        itemService.saveAll(nodes);

        verify(mapper).NodesDTOToItemEntities(nodes);

        verify(itemRepository).saveAll(items);
    }
}
