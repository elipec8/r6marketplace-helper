package github.ricemonger.utilspostgresschema.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityId {
    private UserEntity user;
    private String email;
}
