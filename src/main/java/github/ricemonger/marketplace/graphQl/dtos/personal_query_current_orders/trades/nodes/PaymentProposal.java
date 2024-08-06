package github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.trades.nodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProposal {
    private Integer price;
    private Integer transactionFee;
}
