package github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.SellStats;
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
