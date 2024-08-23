package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeManagersSettings {

    private List<TradeByItemIdManager> activeTradeByItemIdManagers = new ArrayList<>();

    private List<TradeByFiltersManager> activeTradeByFiltersManagers = new ArrayList<>();

    private boolean newManagersAreActiveFlag;

    private boolean managingEnabledFlag;
}
