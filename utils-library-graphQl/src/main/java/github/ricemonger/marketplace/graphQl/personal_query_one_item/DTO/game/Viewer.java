package github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game;

import github.ricemonger.marketplace.graphQl.personal_query_one_item.DTO.game.viewer.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viewer {
    private Meta meta;
}
