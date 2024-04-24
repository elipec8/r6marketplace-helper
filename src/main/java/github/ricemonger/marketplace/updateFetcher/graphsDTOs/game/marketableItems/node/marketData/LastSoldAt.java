package github.ricemonger.marketplace.updateFetcher.graphsDTOs.game.marketableItems.node.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastSoldAt {

    private int price;

    private String lastPerformedAt;
}
