package github.ricemonger.marketplace.databases.postgres.entities.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private LocalDateTime soldAt;
    private Integer price;

    public ItemSaleEntity(String itemId) {
        this.item = new ItemEntity(itemId);
    }

    public String getItemId_() {
        return item.getItemId();
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemSaleEntity entity) {
            return item.isEqual(entity.item) && Objects.equals(soldAt, entity.soldAt);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemSaleEntity entity) {
            return isEqual(entity) && Objects.equals(price, entity.price);
        }
        return false;
    }

    @Override
    public String toString() {
        return "ItemSaleEntity(itemId=" + getItemId_() + ", soldAt=" + soldAt + ", price=" + price + ")";
    }
}
