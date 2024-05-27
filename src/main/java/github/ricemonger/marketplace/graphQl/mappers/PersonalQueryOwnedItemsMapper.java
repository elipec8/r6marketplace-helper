package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_owned_items.MarketableItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PersonalQueryOwnedItemsMapper {

    public List<String> mapOwnedItems(MarketableItems items) {
        return items.getNodes().stream().map(node -> node.getItem().getItemId()).toList();
    }
}
