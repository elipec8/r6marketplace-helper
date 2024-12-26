package github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.MarketableItem;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.Viewer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private MarketableItem marketableItem;
    private Viewer viewer;
}
