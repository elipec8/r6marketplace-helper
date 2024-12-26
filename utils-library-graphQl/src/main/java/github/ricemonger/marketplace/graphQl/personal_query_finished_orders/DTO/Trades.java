package github.ricemonger.marketplace.graphQl.personal_query_finished_orders.DTO;

import github.ricemonger.marketplace.graphQl.personal_query_finished_orders.DTO.trades.Nodes;
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
