package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntityId {
    private UserEntity user;
    private ItemEntity item;

    public TradeByItemIdManagerEntityId(Long userId, ItemEntity item) {
        this.user = new UserEntity(userId);
        this.item = item;
    }

    public int hashCode() {
        return Objects.hash(user.getId(), item.getItemId());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TradeByItemIdManagerEntityId tradeByItemIdManagerEntityId)) {
            return false;
        }
        return Objects.equals(user.getId(), tradeByItemIdManagerEntityId.user.getId()) &&
               Objects.equals(item.getItemId(), tradeByItemIdManagerEntityId.item.getItemId());
    }
}
