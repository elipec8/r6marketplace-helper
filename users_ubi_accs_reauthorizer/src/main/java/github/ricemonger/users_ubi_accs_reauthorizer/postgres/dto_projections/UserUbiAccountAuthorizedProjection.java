package github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUbiAccountAuthorizedProjection {
    private Long userId;
    private String email;
    private String ubiSessionId;
    private String ubiSpaceId;
    private String ubiAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;
}
