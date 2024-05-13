package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.marketplace.databases.neo4j.enums.ItemType;
import lombok.*;
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
    @ToString.Exclude
    private SellStatsEntity sellStats;

    @Relationship(value = "BUY_STATS", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    private BuyStatsEntity buyStats;

    @Relationship(value = "LAST_SOLD_AT", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    private LastSoldAtEntity lastSoldAt;

    @Relationship(value = "SALES", direction = Relationship.Direction.OUTGOING)
    @ToString.Exclude
    private List<LastSoldAtHistoryEntity> lastSoldAtHistory;
}
