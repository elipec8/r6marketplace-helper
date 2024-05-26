package github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.viewer.meta;

import github.ricemonger.marketplace.graphQl.graphsDTOs.personal_query_one_item.game.viewer.meta.trades.Nodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trades {
    private Nodes[] nodes;
}
