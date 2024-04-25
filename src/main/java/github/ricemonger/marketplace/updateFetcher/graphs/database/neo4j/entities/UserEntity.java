package github.ricemonger.marketplace.updateFetcher.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Relationship(value = "CREDENTIALS", direction = Relationship.Direction.OUTGOING)
    private UbiCredentialsEntity ubiCredentials;

    @Relationship(value = "OWNS", direction = Relationship.Direction.OUTGOING)
    private List<ItemEntity> ownedItems;

    @Relationship(value = "CONTROLS", direction = Relationship.Direction.OUTGOING)
    private List<BuyOrdersEntity> buyOrders;

    @Relationship(value = "CONTROLS", direction = Relationship.Direction.OUTGOING)
    private List<SellOrdersEntity> sellOrders;
}
