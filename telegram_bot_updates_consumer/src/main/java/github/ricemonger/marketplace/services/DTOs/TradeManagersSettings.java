package github.ricemonger.marketplace.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagersSettings {
    private Boolean newManagersAreActiveFlag;
    private Boolean managingEnabledFlag;
    private Boolean sellTradesManagingEnabledFlag;
    private Boolean buyTradesManagingEnabledFlag;

    public String toHandsomeString() {
        return "New managers are active: " + newManagersAreActiveFlag + "\n" +
               "Managing enabled: " + managingEnabledFlag + "\n" +
               "Sell trades managing enabled: " + sellTradesManagingEnabledFlag + "\n" +
               "Buy trades managing enabled: " + buyTradesManagingEnabledFlag;
    }
}
