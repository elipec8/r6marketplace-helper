package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.node.Item;
import github.ricemonger.utils.exceptions.GraphQlPersonalOwnedItemsMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonalQueryOwnedItemsMapperTest {
    @Autowired
    PersonalQueryOwnedItemsMapper personalQueryOwnedItemsMapper;

    @Test
    public void mapOwnedItems_should_map_each_item(){
        List<Node> nodes = new ArrayList<>();

        Node node1 = new Node();
        Item item1 = new Item();
        item1.setItemId("item1");
        node1.setItem(item1);
        nodes.add(node1);

        Node node2 = new Node();
        Item item2 = new Item();
        item2.setItemId("item2");
        node2.setItem(item2);
        nodes.add(node2);

        List<String> result = personalQueryOwnedItemsMapper.mapOwnedItems(nodes);

        assertEquals(2, result.size());
        assertTrue(result.contains("item1") && result.contains("item2"));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_nodes_is_null(){
        assertThrows(GraphQlPersonalOwnedItemsMappingException.class, () -> personalQueryOwnedItemsMapper.mapOwnedItems(null));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_node_is_null(){
        List<Node> nodes = new ArrayList<>();
        nodes.add(null);

        assertThrows(GraphQlPersonalOwnedItemsMappingException.class, () -> personalQueryOwnedItemsMapper.mapOwnedItems(nodes));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_item_is_null(){
        List<Node> nodes = new ArrayList<>();

        Node node = new Node();
        node.setItem(null);
        nodes.add(node);

        assertThrows(GraphQlPersonalOwnedItemsMappingException.class, () -> personalQueryOwnedItemsMapper.mapOwnedItems(nodes));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_itemId_is_null(){
        List<Node> nodes = new ArrayList<>();

        Node node = new Node();
        Item item = new Item();
        item.setItemId(null);
        node.setItem(item);
        nodes.add(node);

        assertThrows(GraphQlPersonalOwnedItemsMappingException.class, () -> personalQueryOwnedItemsMapper.mapOwnedItems(nodes));
    }
}