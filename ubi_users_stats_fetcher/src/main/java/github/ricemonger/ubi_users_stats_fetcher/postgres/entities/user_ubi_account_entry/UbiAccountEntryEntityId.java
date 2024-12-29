package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityId {
    private UserUbiAccountEntryEntity user;
    private String email;
}
