package github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO;

import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.MarketableItem;
import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.Viewer;
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
