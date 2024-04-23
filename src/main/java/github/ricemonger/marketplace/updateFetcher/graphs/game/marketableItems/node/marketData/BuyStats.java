package github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.node.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyStats {

    private int lowestPrice;

    private int highestPrice;

    private int activeCount;
}
