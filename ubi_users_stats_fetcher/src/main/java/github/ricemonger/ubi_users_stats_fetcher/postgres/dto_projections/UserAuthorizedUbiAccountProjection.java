package github.ricemonger.ubi_users_stats_fetcher.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizedUbiAccountProjection {
    private Long userId;

    private String profileId;
    private Integer creditAmount;
    private Integer ownedItemsIdsCount;

    private String ticket;
    private String spaceId;
    private String sessionId;
    private String rememberDeviceTicket;
    private String rememberMeTicket;
}
