package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntityId {
    private UbiAccountEntity ubiAccount;

    private ItemEntity item;

    public int hashCode() {
        return Objects.hash(ubiAccount.getUbiProfileId(), item.getItemId());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemResaleLockEntityId itemResaleLockEntityId)) {
            return false;
        }
        return Objects.equals(ubiAccount.getUbiProfileId(), itemResaleLockEntityId.ubiAccount.getUbiProfileId()) &&
               Objects.equals(item.getItemId(), itemResaleLockEntityId.item.getItemId());
    }
}
