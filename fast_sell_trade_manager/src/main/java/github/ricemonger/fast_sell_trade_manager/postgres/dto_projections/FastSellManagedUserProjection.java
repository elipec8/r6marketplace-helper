package github.ricemonger.fast_sell_trade_manager.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FastSellManagedUserProjection {
    private String ubiAuthTicket;
    private String ubiProfileId;
    private String ubiSpaceId;
    private String ubiSessionId;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;
    private Integer soldIn24h;
}
