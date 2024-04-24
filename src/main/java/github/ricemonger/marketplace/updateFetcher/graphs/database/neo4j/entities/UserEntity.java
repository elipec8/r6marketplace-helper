package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private String id;

    @Version
    private Long version;

    @Relationship(value = "OWNS", direction = Relationship.Direction.OUTGOING)
    private List<ItemEntity> ownedItems;

    @Relationship(value = "CONTROLS", direction = Relationship.Direction.OUTGOING)
    private List<TransactionEntity> itemTransactions;
}
