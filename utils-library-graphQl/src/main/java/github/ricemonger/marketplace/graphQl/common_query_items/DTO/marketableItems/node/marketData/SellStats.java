package github.ricemonger.marketplace.graphQl.common_query_items.DTO.marketableItems.node.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellStats {

    private Integer lowestPrice;

    private Integer activeCount;
}
