package github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.node;

import github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.updateFetcher.graphs.game.marketableItems.node.marketData.SellStats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketData {

    private SellStats[] sellStats;

    private BuyStats[] buyStats;

    private LastSoldAt[] lastSoldAt;
}
