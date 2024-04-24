package github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems;

import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node.MarketData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private Item item;

    private MarketData marketData;

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Node)) return false;
        return item.equals(((Node) o).item);
    }
}

