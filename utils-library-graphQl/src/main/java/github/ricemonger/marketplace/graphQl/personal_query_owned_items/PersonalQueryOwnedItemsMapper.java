package github.ricemonger.marketplace.graphQl.personal_query_owned_items;

import github.ricemonger.marketplace.graphQl.personal_query_owned_items.DTO.marketableItems.Node;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOwnedItemsMappingException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PersonalQueryOwnedItemsMapper {

    public List<String> mapOwnedItems(Collection<Node> nodes) throws GraphQlPersonalOwnedItemsMappingException {
        if (nodes == null) {
            throw new GraphQlPersonalOwnedItemsMappingException("Nodes is null");
        }

        List<String> result = new ArrayList<>();

        for (Node node : nodes) {
            if (node == null || node.getItem() == null || node.getItem().getItemId() == null) {
                throw new GraphQlPersonalOwnedItemsMappingException("Node or item or itemId is null in nodes: " + nodes);
            }
            result.add(node.getItem().getItemId());
        }

        return result;
    }
}
