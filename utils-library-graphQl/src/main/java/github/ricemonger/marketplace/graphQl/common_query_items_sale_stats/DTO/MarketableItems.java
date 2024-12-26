package github.ricemonger.marketplace.graphQl.common_query_items_sale_stats.DTO;

import github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.Node;
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
