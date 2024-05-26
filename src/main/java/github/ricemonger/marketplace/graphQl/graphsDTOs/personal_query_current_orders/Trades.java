package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_current_orders;

import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_current_orders.trades.Nodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trades {
    private Nodes[] nodes;
}
