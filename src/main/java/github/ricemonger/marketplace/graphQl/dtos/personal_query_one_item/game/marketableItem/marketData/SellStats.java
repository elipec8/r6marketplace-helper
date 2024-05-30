package github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData;

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
