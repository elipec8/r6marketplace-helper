package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManagerEntityId {
    private UserEntity user;
    private String name;

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
