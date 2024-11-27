package github.ricemonger.marketplace.graphQl.DTOs.common_query_items_sale_stats.marketableItems.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory {
    private String date;

    private Integer lowestPrice;

    private Integer averagePrice;

    private Integer highestPrice;

    private Integer itemsCount;
}
