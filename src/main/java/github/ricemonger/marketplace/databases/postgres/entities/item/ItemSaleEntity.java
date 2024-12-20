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

    public String getItemId() {
        return item.getItemId();
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemSaleEntity entity) {
            return item.isFullyEqual(entity.getItem()) && Objects.equals(soldAt, entity.soldAt) && Objects.equals(price, entity.price);
        }
        return false;
    }
}
