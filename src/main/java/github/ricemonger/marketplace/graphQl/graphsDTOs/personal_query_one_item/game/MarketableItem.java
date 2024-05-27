package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game;

import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.marketableItem.Item;
import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.marketableItem.MarketData;
import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.marketableItem.PaymentLimitations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketableItem {
    private Item item;
    private MarketData marketData;
    private PaymentLimitations paymentLimitations;
}
