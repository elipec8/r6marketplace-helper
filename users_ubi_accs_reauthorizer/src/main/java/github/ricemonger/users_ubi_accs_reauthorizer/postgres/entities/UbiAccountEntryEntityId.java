package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityId {
    private UserIdEntity user;
    private String email;

    public UbiAccountEntryEntityId(Long userId, String email) {
        this.user = new UserIdEntity(userId);
        this.email = email;
    }
}
