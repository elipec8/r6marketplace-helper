package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.SoldItemDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "item_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemSaleEntityId.class)
public class ItemSaleEntity {
    @MapsId
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemEntity item;
    @Id
    private Date soldAt;
    private int price;

    public ItemSaleEntity(SoldItemDetails item) {
        this.item = new ItemEntity(item.getItemId());
        this.soldAt = item.getLastSoldAt();
        this.price = item.getLastSoldPrice();
    }

    public ItemSale toItemSale() {
        ItemSale item = new ItemSale();
        if (this.item != null) {
            item.setItemId(this.item.getItemId());
        }
        item.setSoldAt(this.soldAt);
        item.setPrice(this.price);
        return item;
    }
}
