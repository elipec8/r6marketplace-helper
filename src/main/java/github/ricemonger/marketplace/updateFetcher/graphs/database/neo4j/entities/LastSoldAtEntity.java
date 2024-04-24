package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Node("LastSoldAt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastSoldAtEntity {

    @Id
    private String id;

    @Version
    private Long version;

    private int price;

    private Date performedAt;
}
