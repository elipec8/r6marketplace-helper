package github.ricemonger.marketplace.graphQl.personal_query_finished_orders.DTO.trades.nodes.tradeItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String itemId;
    private String name;
}
