package github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem;

import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.marketData.SellStats;
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
