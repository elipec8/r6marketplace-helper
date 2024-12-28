package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntityId {

    private UbiAccountStatsEntity ubiAccount;

    private ItemIdEntity item;

    public ItemResaleLockEntityId(String ubiProfileId, String itemId) {
        this.ubiAccount = new UbiAccountStatsEntity(ubiProfileId);
        this.item = new ItemIdEntity(itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ubiAccount.getUbiProfileId(), item.getItemId());
    }

    @Override
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
