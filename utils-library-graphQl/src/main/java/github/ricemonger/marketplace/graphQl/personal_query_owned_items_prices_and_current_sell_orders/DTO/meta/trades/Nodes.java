package github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.DTO.meta.trades;

import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.DTO.meta.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.DTO.meta.trades.nodes.TradeItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nodes {
    private String tradeId;
    private TradeItems[] tradeItems;
    private PaymentOptions[] paymentOptions;
}
