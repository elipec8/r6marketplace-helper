package github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.Trades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private Trades trades;
}
