package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationDTO {
    private String ticket;
    private String profileId;
    private String spaceId;
    private String sessionId;
    private String twoFactorAuthenticationTicket;
    private String rememberDeviceTicket;
    private String rememberMeTicket;
}
