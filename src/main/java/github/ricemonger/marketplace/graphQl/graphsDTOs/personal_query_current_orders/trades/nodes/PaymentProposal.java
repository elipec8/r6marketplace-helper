package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_current_orders.trades.nodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProposal {
    private int price;
    private int transactionFee;
}
