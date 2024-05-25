package github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData;

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
