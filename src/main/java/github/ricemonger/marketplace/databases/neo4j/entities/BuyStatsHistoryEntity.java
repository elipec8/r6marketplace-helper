package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("BuyStatsHistory")
@Data
@NoArgsConstructor
public class BuyStatsHistoryEntity {

    @Id
    @GeneratedValue
    private String id;

    @Relationship(value = "BUY_STATS_HISTORY", direction = Relationship.Direction.INCOMING)
    private ItemEntity item;

    @Relationship(value = "BUY_STATS", direction = Relationship.Direction.OUTGOING)
    private List<BuyStatsEntity> buyStats = new ArrayList<>();
}
