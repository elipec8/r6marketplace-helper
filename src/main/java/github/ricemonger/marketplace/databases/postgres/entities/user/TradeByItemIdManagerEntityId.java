package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntityId {
    private UserEntity user;
    private String itemId;

    public int hashCode() {
        return user.getId().hashCode() + itemId.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TradeByItemIdManagerEntityId tradeByItemIdManagerEntityId)) {
            return false;
        }
        if (this.hashCode() != tradeByItemIdManagerEntityId.hashCode()) {
            return false;
        }
        return tradeByItemIdManagerEntityId.user.getId().equals(user.getId()) && tradeByItemIdManagerEntityId.itemId.equals(itemId);
    }
}
