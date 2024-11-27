package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountAuthorizationEntryEntityId {
    private UserEntity user;
    private String email;

    public int hashCode() {
        return user.getId().hashCode() + email.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountAuthorizationEntryEntityId ubiAccountAuthorizationEntryEntityId)) {
            return false;
        }
        if (this.hashCode() != ubiAccountAuthorizationEntryEntityId.hashCode()) {
            return false;
        }
        return ubiAccountAuthorizationEntryEntityId.user.getId().equals(user.getId()) && ubiAccountAuthorizationEntryEntityId.email.equals(email);
    }
}
