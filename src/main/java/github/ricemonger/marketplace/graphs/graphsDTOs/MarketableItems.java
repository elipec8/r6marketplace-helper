package github.ricemonger.marketplace.graphs.graphsDTOs;

import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketableItems {

    private List<Node> nodes;

    private int totalCount;
}
