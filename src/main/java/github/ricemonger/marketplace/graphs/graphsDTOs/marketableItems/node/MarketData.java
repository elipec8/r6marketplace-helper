package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node;

import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.SellStats;
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
