package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.SoldItemDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
    @Id
    private String itemId;
    @Id
    private Date soldAt;
    private int price;

    public ItemSaleEntity(SoldItemDetails item) {
        this.itemId = item.getItemId();
        this.soldAt = item.getLastSoldAt();
        this.price = item.getLastSoldPrice();
    }

    public ItemSale toItemSale() {
        ItemSale item = new ItemSale();
        item.setItemId(this.itemId);
        item.setSoldAt(this.soldAt);
        item.setPrice(this.price);
        return item;
    }
}
