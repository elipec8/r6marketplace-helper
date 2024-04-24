package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.enums.TransactionStatus;
import github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


import java.util.Date;

@Node("Transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private String id;

    @Version
    private Long version;

    @Relationship(value = "OWNER", direction = Relationship.Direction.INCOMING)
    private UserEntity user;

    @Relationship(value = "ITEM", direction = Relationship.Direction.OUTGOING)
    private ItemEntity item;

    private TransactionType type;

    private TransactionStatus status;

    private Date createdAt;
}
