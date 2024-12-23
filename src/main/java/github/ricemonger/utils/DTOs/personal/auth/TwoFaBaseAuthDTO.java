package github.ricemonger.utils.DTOs.personal.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoFaBaseAuthDTO {
    private String sessionId;
    private String twoFactorAuthenticationTicket;
}
