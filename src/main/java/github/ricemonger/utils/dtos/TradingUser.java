package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradingUser {

    private Long id;

    private String chatId;

    private UbiAccountTradingEntry ubiAccountTradeEntry;

    private List<ItemFilter> itemFilters;

    private List<TradeByItemIdManager> tradeByItemIdManagers;

    private List<TradeByFiltersManager> tradeByFiltersManagers;

    private Boolean privateNotificationsEnabledFlag;
}
