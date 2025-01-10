package github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUnauthorizedUbiAccountProjection {
    private Long id;
    private String email;
}
