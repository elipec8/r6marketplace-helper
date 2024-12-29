package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.ItemResaleLockEntityId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityId {
    private UserUbiAccountEntryEntity user;

    private String email;

    public Long getUserId_() {
        return user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId_(), email);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountEntryEntityId id)) {
            return false;
        }
        return Objects.equals(getUserId_(), id.getUserId_()) &&
               Objects.equals(email, id.getEmail());
    }
}
