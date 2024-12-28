package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManageableUserUbiAccountEntryEntityId {
    private ManageableUserEntity user;
    private String email;

    public ManageableUserUbiAccountEntryEntityId(Long userId, String email) {
        this.user = new ManageableUserEntity(userId);
        this.email = email;
    }

    public Long getUserId_() {
        return user.getId();
    }

    public int hashCode() {
        return user.getId().hashCode() + email.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ManageableUserUbiAccountEntryEntityId ubiAccountEntryEntityId)) {
            return false;
        }
        if (this.hashCode() != ubiAccountEntryEntityId.hashCode()) {
            return false;
        }
        return ubiAccountEntryEntityId.user.getId().equals(user.getId()) && ubiAccountEntryEntityId.email.equals(email);
    }
}
