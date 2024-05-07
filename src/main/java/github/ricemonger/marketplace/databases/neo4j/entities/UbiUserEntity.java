package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("UbiUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiUserEntity {

    public UbiUserEntity(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Id
    private String id;

    private String ubiProfileId;

    private String email;

    private String password;

    private String ubiSessionId;

    private String ubiAuthTicket;

    @Relationship(value = "OWNS", direction = Relationship.Direction.OUTGOING)
    private List<ItemEntity> ownedItems = new ArrayList<>();

    @Relationship(value = "CONTROLS", direction = Relationship.Direction.OUTGOING)
    private List<BuyOrdersEntity> buyOrders = new ArrayList<>();

    @Relationship(value = "CONTROLS", direction = Relationship.Direction.OUTGOING)
    private List<SellOrdersEntity> sellOrders = new ArrayList<>();
}
