package github.ricemonger.marketplace.updateFetcher.graphsDTOs.game.marketableItems;

import github.ricemonger.marketplace.updateFetcher.graphsDTOs.game.marketableItems.node.Item;
import github.ricemonger.marketplace.updateFetcher.graphsDTOs.game.marketableItems.node.MarketData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    private Item item;

    private MarketData marketData;
}
