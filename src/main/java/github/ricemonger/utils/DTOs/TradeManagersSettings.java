package github.ricemonger.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagersSettings {
    private boolean newManagersAreActiveFlag;

    private boolean managingEnabledFlag;
}
