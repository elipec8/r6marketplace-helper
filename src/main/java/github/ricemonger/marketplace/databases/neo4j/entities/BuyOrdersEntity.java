package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


import java.util.ArrayList;
import java.util.List;

@Node("BuyOrders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyOrdersEntity {

    @Id
    @GeneratedValue
    private String id;

    @Relationship(value = "CONTROLS", direction = Relationship.Direction.INCOMING)
    private UbiUserEntity user;

    private int currentAmount;

    private int finishedIn24h;
}
