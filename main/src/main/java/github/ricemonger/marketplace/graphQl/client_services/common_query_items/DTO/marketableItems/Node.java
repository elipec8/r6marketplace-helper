package github.ricemonger.marketplace.graphQl.client_services.common_query_items.DTO.marketableItems;

import github.ricemonger.marketplace.graphQl.client_services.common_query_items.DTO.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.client_services.common_query_items.DTO.marketableItems.node.MarketData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private Item item;

    private MarketData marketData;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Node node)) {
            return false;
        }

        if (item == null && node.item == null) {
            return true;
        } else if (item == null || node.item == null) {
            return false;
        } else {
            return Objects.equals(item.getItemId(), node.item.getItemId());
        }
    }

    public int hashCode() {
        if (item == null || item.getItemId() == null) {
            return 0;
        }
        return item.getItemId().hashCode();
    }
}

