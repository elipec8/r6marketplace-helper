package github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellStats {

    private int lowestPrice;

    private int activeCount;
}
