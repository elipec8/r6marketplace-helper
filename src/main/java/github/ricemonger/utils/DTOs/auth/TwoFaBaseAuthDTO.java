package github.ricemonger.utils.DTOs.auth;

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
