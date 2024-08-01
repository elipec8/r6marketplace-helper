package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagerByItemIdEntityId {
    private UserEntity user;
    private String itemId;

    public int hashCode() {
        return user.getId().hashCode() + itemId.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TradeManagerByItemIdEntityId tradeManagerByItemIdEntityId)) {
            return false;
        }
        if (this.hashCode() != tradeManagerByItemIdEntityId.hashCode()) {
            return false;
        }
        return tradeManagerByItemIdEntityId.user.getId().equals(user.getId()) && tradeManagerByItemIdEntityId.itemId.equals(itemId);
    }
}
