package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManager;
import github.ricemonger.utils.enums.TradeCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
public class TradeForCentralTradeManager implements Comparable<TradeForCentralTradeManager> {
    private final Item item;
    private final TradeCategory category;
    private final Integer price;
    private final Integer prognosedTradeSuccessMinutes;
    private final Long tradePriority;
    private final Boolean alreadyExists;
    // If new trade
    private Integer priority;
    // If existing trade
    private String existingTradeId;
    private LocalDateTime existingTradeExpiresAt;
    private LocalDateTime existingTradeLastModifiedAt;

    public TradeForCentralTradeManager(UbiTrade ubiTrade) {
        this(ubiTrade.getItem(),
                ubiTrade.getCategory(),
                ubiTrade.getProposedPaymentPrice(),
                ubiTrade.getPrognosedPaymentsSuccessMinutes(),
                ItemForCentralTradeManager.getExistingUbiTradePriority(ubiTrade),
                ubiTrade.getTradeId(),
                ubiTrade.getExpiresAt(),
                ubiTrade.getLastModifiedAt());
    }

    public TradeForCentralTradeManager(Item item, TradeCategory category, Integer price, Integer prognosedTradeSuccessMinutes, Long tradePriority,
                                       String existingTradeId, LocalDateTime existingTradeExpiresAt, LocalDateTime existingTradeLastModifiedAt) {
        this.item = item;
        this.category = category;
        this.price = price;
        this.prognosedTradeSuccessMinutes = prognosedTradeSuccessMinutes;
        this.tradePriority = tradePriority;
        this.alreadyExists = true;
        this.existingTradeId = existingTradeId;
        this.existingTradeExpiresAt = existingTradeExpiresAt;
        this.existingTradeLastModifiedAt = existingTradeLastModifiedAt;
    }

    public TradeForCentralTradeManager(ItemForCentralTradeManager itemForCentralTradeManager) {
        this(itemForCentralTradeManager.getItem(),
                itemForCentralTradeManager.getIsOwned() ? TradeCategory.Sell : TradeCategory.Buy,
                itemForCentralTradeManager.getTradePrice(),
                itemForCentralTradeManager.getPrognosedTradeSuccessMinutes(),
                itemForCentralTradeManager.getTradePriority(),
                itemForCentralTradeManager.getPriority());
    }

    public TradeForCentralTradeManager(Item item, TradeCategory category, Integer price, Integer prognosedTradeSuccessMinutes,
                                       Long tradePriority, Integer priority) {
        this.item = item;
        this.category = category;
        this.price = price;
        this.prognosedTradeSuccessMinutes = prognosedTradeSuccessMinutes;
        this.tradePriority = tradePriority;
        this.alreadyExists = false;
        this.priority = priority;
    }

    @Override
    public int compareTo(@NotNull TradeForCentralTradeManager other) {
        if (this.alreadyExists && !other.alreadyExists) {
            return (int) (this.tradePriority - (other.tradePriority * this.priority));
        } else if (!this.alreadyExists && other.alreadyExists) {
            return (int) ((this.tradePriority * other.priority) - (other.tradePriority));
        } else {
            return (int) (this.tradePriority - (other.tradePriority));
        }
    }

    public String getItemId() {
        return item.getItemId();
    }
}
