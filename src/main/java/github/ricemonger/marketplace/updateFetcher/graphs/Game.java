package github.ricemonger.marketplace.updateFetcher.graphs;

import github.ricemonger.marketplace.updateFetcher.graphs.game.MarketableItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private MarketableItems marketableItems;
}
