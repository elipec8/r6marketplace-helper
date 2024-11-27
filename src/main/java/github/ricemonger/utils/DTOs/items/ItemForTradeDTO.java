package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.enums.TradeOperationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ItemForTradeDTO {

    private String itemId;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private LocalDateTime LastSoldAt;
    private int lastSoldPrice;

    private TradeOperationType tradeOperationType;

    private int priority;

    public ItemForTradeDTO(Item item, TradeOperationType tradeOperationType, int priority) {
        setItemId(item.getItemId());

        setMaxBuyPrice(item.getMaxBuyPrice());
        setBuyOrdersCount(item.getBuyOrdersCount());

        setMinSellPrice(item.getMinSellPrice());
        setSellOrdersCount(item.getSellOrdersCount());

        setLastSoldAt(item.getLastSoldAt());
        setLastSoldPrice(item.getLastSoldPrice());

        this.tradeOperationType = tradeOperationType;

        this.priority = priority;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), tradeOperationType, priority);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemForTradeDTO itemForTradeDTO)) {
            return false;
        }
        return super.equals(obj) && tradeOperationType.equals(itemForTradeDTO.getTradeOperationType()) && priority == itemForTradeDTO.priority;
    }
}
