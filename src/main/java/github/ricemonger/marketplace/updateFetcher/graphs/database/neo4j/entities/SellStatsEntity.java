package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("SellStats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellStatsEntity {

    @Id
    private String sellStatsId;

    private int lowestPrice;

    private int highestPrice;

    private int activeCount;
}
