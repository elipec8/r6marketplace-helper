package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.marketableItem.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastSoldAt {

    private int price;

    private String performedAt;
}
