package github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.node;

import github.ricemonger.marketplace.updateFetcher.graphs.graphsDTOs.marketableItems.Node;
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


    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Item)) return false;
        return id.equals(((Item) o).id);
    }
}
