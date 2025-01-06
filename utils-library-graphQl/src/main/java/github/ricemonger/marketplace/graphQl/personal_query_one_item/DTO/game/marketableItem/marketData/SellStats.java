package github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.marketableItem.marketData;

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
