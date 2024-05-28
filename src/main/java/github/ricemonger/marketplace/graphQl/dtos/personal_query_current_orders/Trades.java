package github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.trades.Nodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trades {
    private List<Nodes> nodes;
}
