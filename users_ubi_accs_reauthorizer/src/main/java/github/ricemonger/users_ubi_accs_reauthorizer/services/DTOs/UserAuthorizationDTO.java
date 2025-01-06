package github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserAuthorizationDTO {
    private Long userId;

    private AuthorizationDTO authorizationDTO;
}
