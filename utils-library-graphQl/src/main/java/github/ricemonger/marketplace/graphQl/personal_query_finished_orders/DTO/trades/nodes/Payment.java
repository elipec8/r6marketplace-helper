package github.ricemonger.marketplace.graphQl.personal_query_finished_orders.DTO.trades.nodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Integer price;
    private Integer transactionFee;
}
