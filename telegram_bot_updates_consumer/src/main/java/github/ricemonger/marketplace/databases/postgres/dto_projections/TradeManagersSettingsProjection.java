package github.ricemonger.marketplace.databases.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagersSettingsProjection {
    private Boolean newManagersAreActiveFlag;
    private Boolean managingEnabledFlag;
    private Boolean sellTradesManagingEnabledFlag;
    private String sellTradePriorityExpression;
    private Boolean buyTradesManagingEnabledFlag;
    private String buyTradePriorityExpression;
}
