package github.ricemonger.marketplace.databases.postgres.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityId {
    private String ubiProfileId;
    private UserEntity user;

    public int hashCode() {
        return user.getId().hashCode() + ubiProfileId.hashCode();
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
        return ubiAccountEntryEntityId.user.getId().equals(user.getId()) && ubiAccountEntryEntityId.ubiProfileId.equals(ubiProfileId);
    }
}
