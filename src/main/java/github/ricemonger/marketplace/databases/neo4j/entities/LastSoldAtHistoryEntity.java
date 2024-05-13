package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("LastSoldAtHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastSoldAtHistoryEntity {

    @Id
    @GeneratedValue
    private String id;

    @Relationship(value = "SALES", direction = Relationship.Direction.INCOMING)
    private ItemEntity item;


}
