package github.ricemonger.marketplace.databases.postgres.entities.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSaleEntityId implements Serializable {
    private ItemEntity item;
    private LocalDateTime soldAt;

    public int hashCode() {
        if (item == null) {
            return Objects.hash(soldAt);
        } else {
            return Objects.hash(item.getItemId(), soldAt);
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemSaleEntityId itemSaleEntityId)) {
            return false;
        }
        boolean item = this.item == itemSaleEntityId.item || (
                this.item != null &&
                itemSaleEntityId.item != null &&
                Objects.equals(this.item.getItemId(), itemSaleEntityId.item.getItemId()));

        boolean soldAt = Objects.equals(this.soldAt, itemSaleEntityId.soldAt);

        return item && soldAt;
    }
}
