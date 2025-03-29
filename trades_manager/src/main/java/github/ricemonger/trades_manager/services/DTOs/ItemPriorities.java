package github.ricemonger.trades_manager.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPriorities {
    private String itemId;

    private Long priorityToSellByMaxBuyPrice;
    private Long priorityToSellByNextFancySellPrice;

    private Long priorityToBuyByMinSellPrice;

    private Long priorityToBuyIn1Hour;
    private Long priorityToBuyIn6Hours;
    private Long priorityToBuyIn24Hours;
    private Long priorityToBuyIn168Hours;
    private Long priorityToBuyIn720Hours;
}
