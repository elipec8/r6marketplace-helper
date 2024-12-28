package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntityId {
    private ManageableUserEntity user;
    private ItemIdEntity item;

    public TradeByItemIdManagerEntityId(Long userId, String itemId) {
        this.user = new ManageableUserEntity(userId);
        this.item = new ItemIdEntity(itemId);
    }

    public Long getUserId_() {
        return user.getId();
    }

    public String getItemId_() {
        return item.getItemId();
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
