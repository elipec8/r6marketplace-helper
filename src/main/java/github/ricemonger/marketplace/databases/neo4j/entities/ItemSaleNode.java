package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Node("ItemSale")
@Getter
@Setter
public class ItemSaleNode {

    @Id
    private String id;
    private String itemId;
    private Date soldAt;
    private int price;

    public ItemSaleNode(String itemId, Date soldAt, int price) {
        this.id = itemId + "_" + soldAt.getTime();
        this.itemId = itemId;
        this.soldAt = soldAt;
        this.price = price;
    }
}
