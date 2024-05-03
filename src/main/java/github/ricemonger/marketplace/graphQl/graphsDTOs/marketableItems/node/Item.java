package github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String id;

    private String assertUrl;

    private String name;

    private List<String> tags;

    private String type;

}
