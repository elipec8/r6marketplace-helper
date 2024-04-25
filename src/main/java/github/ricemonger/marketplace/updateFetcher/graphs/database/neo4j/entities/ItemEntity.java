package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    private String itemFullId;

    private String assertUrl;

    private String name;

    private List<String> tags;

    private ItemType type;

    @Relationship(value = "SELL_STATS", direction = Relationship.Direction.OUTGOING)
    private SellStatsEntity sellStats;

    @Relationship(value = "BUY_STATS", direction = Relationship.Direction.OUTGOING)
    private BuyStatsEntity buyStats;

    @Relationship(value = "LAST_SOLD_AT", direction = Relationship.Direction.OUTGOING)
    private LastSoldAtEntity lastSoldAt;
}
