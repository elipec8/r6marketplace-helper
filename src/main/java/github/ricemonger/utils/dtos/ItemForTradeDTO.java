package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.TradeManagingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ItemForTradeDTO {

    private String itemId;

    private int maxBuyPrice;
    private int buyOrdersCount;

    private int minSellPrice;
    private int sellOrdersCount;

    private Date LastSoldAt;
    private int lastSoldPrice;

    private TradeManagingType tradeManagingType;

    private int priority;

    public ItemForTradeDTO(Item item, TradeManagingType tradeManagingType, int priority) {
        setItemId(item.getItemId());

        setMaxBuyPrice(item.getMaxBuyPrice());
        setBuyOrdersCount(item.getBuyOrdersCount());

        setMinSellPrice(item.getMinSellPrice());
        setSellOrdersCount(item.getSellOrdersCount());

        setLastSoldAt(item.getLastSoldAt());
        setLastSoldPrice(item.getLastSoldPrice());

        this.tradeManagingType = tradeManagingType;

        this.priority = priority;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(),tradeManagingType, priority);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemForTradeDTO itemForTradeDTO)) {
            return false;
        }
        return super.equals(obj) && tradeManagingType.equals(itemForTradeDTO.getTradeManagingType()) && priority == itemForTradeDTO.priority;
    }
}
