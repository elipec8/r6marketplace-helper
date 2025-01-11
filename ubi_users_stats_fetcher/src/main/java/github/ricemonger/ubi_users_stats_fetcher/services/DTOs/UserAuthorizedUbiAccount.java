package github.ricemonger.ubi_users_stats_fetcher.services.DTOs;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizedUbiAccount {
    private Long userId;

    private String profileId;

    private Integer creditAmount;

    private String ticket;
    private String spaceId;
    private String sessionId;
    private String rememberDeviceTicket;
    private String rememberMeTicket;

    public AuthorizationDTO toAuthorizationDTO() {
        return new AuthorizationDTO(ticket, profileId, spaceId, sessionId, rememberDeviceTicket, rememberMeTicket);
    }
}
