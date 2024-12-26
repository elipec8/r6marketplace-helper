package github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game;

import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.Item;
import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.MarketData;
import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.PaymentLimitations;
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
