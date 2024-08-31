package github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats;

import github.ricemonger.marketplace.graphQl.dtos.common_query_items_sale_stats.marketableItems.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketableItems {
    private List<Node> nodes;

    private Integer totalCount;
}
