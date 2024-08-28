package github.ricemonger.utils.dtos;

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
