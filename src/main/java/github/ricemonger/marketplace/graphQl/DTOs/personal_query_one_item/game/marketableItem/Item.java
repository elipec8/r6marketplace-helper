package github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.item.Viewer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String itemId;
    private String assetUrl;
    private String name;
    private List<String> tags;
    private String type;
    private Viewer viewer;
}
