package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityId {
    private UserEntity user;
    private String email;

    public UbiAccountEntryEntityId(Long userId, String email) {
        this.user = new UserEntity(userId);
        this.email = email;
    }

    public Long getUserId() {
        return user.getId();
    }

    public int hashCode() {
        return user.getId().hashCode() + email.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountEntryEntityId ubiAccountEntryEntityId)) {
            return false;
        }
        if (this.hashCode() != ubiAccountEntryEntityId.hashCode()) {
            return false;
        }
        return ubiAccountEntryEntityId.user.getId().equals(user.getId()) && ubiAccountEntryEntityId.email.equals(email);
    }
}
