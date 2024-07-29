package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagerByItemFiltersEntityId {
    private UserEntity user;
    private String name;

    public int hashCode() {
        return user.getId().hashCode() + name.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TradeManagerByItemFiltersEntityId tradeManagerByItemFiltersEntityId)) {
            return false;
        }
        return tradeManagerByItemFiltersEntityId.user.getId().equals(user.getId()) && tradeManagerByItemFiltersEntityId.name.equals(name);
    }
}
