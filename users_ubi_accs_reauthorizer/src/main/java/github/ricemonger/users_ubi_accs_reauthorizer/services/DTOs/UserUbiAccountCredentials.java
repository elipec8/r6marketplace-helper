package github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUbiAccountCredentials {
    private Long userId;
    private String email;
    private String encodedPassword;
    private String ticket;
    private String rememberDeviceTicket;
}
