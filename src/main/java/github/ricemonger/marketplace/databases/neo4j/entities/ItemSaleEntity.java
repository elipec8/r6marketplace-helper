package github.ricemonger.marketplace.databases.neo4j.entities;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Node("ItemSale")
@Data
public class ItemSaleEntity {

    @Id
    private String id;
    private String itemId;
    private Date soldAt;
    private int price;

    public ItemSaleEntity(String itemId, Date soldAt, int price) {
        this.id = itemId + "_" + soldAt.getTime();
        this.itemId = itemId;
        this.soldAt = soldAt;
        this.price = price;
    }
}
