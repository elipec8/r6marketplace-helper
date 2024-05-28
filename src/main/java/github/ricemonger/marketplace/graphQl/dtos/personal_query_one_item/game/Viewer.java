package github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viewer {
    private Meta meta;
}
