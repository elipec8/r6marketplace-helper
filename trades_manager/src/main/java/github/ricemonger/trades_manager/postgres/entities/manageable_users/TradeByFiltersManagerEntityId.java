package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManagerEntityId {
    private ManageableUserEntity user;
    private String name;

    public TradeByFiltersManagerEntityId(Long userId, String name) {
        this.user = new ManageableUserEntity(userId);
        this.name = name;
    }

    public Long getUserId_() {
        return user.getId();
    }

    public int hashCode() {
        return user.getId().hashCode() + name.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TradeByFiltersManagerEntityId tradeByFiltersManagerEntityId)) {
            return false;
        }
        if (this.hashCode() != tradeByFiltersManagerEntityId.hashCode()) {
            return false;
        }
        return tradeByFiltersManagerEntityId.user.getId().equals(user.getId()) && tradeByFiltersManagerEntityId.name.equals(name);
    }
}
