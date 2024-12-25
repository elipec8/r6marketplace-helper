package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagersSettings {
    private Boolean newManagersAreActiveFlag;

    private Boolean managingEnabledFlag;

    public String toHandsomeString() {
        return "New managers are active: " + newManagersAreActiveFlag + "\n" +
               "Managing enabled: " + managingEnabledFlag + "\n";
    }
}
