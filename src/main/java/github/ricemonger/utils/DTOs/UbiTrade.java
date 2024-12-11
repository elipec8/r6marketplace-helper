package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import static github.ricemonger.marketplace.services.CentralTradeManager.TRADE_MANAGER_FIXED_RATE_MINUTES;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiTrade {
    private String tradeId;
    private TradeState state;
    private TradeCategory category;
    private LocalDateTime expiresAt;
    private LocalDateTime lastModifiedAt;

    private Item item;

    private Integer successPaymentPrice;
    private Integer successPaymentFee;

    private Integer proposedPaymentPrice;
    private Integer proposedPaymentFee;

    public Integer getPrognosedPaymentsSuccessMinutes() {
        int secondsTradeExists = (int) Duration.between(lastModifiedAt, expiresAt).toMinutes();
        return Math.max(item.getPrognosedTradeSuccessMinutes(proposedPaymentPrice, category) - secondsTradeExists, TRADE_MANAGER_FIXED_RATE_MINUTES);
    }

    public String getItemId() {
        return item.getItemId();
    }
}
