package github.ricemonger.marketplace.graphQl.dtos.common_query_items.marketableItems.node;

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

}
