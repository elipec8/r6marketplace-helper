package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.MarketableItems;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_owned_items.marketableItems.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class PersonalQueryOwnedItemsMapper {

    public List<String> mapOwnedItems(Collection<Node> nodes) {
        return nodes.stream().map(node -> node.getItem().getItemId()).toList();
    }
}
