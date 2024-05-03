package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("BuyStats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyStatsEntity {

    @Id
    private String buyStatsId;

    private int lowestPrice;

    private int highestPrice;

    private int activeCount;
}
