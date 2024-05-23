package github.ricemonger.marketplace.databases.postgres.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "item_sale")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleEntity {

    @EmbeddedId
    private ItemSaleEntityId id;

    @MapsId("itemId")
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    private int price;

    public ItemSaleEntity(ItemEntity item, Date soldAt, int price) {
        this.item = item;
        this.id = new ItemSaleEntityId(item.getItemFullId(), soldAt);
        this.price = price;
    }

    public Date getSoldAt() {
        return id.getSoldAt();
    }

}
