package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntityId {
    private UbiAccountStatsEntity ubiAccount;
    private ItemIdEntity item;
}
