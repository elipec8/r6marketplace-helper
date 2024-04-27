package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("SellOrders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellOrdersEntity {

        @Id
        private String id;

        @Relationship(value = "OWNER", direction = Relationship.Direction.INCOMING)
        private UbiUserEntity user;

        @Relationship(value = "TRANSACTIONS", direction = Relationship.Direction.OUTGOING)
        private List<TransactionEntity> transaction;

        private int currentAmount;

        private int createdToday;
}
