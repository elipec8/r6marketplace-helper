package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiTrade {
    private String tradeId;
    private TradeState state;
    private TradeCategory category;
    private LocalDateTime expiresAt;
    private LocalDateTime lastModifiedAt;

    private String itemId;

    private Integer successPaymentPrice;
    private Integer successPaymentFee;

    private Integer proposedPaymentPrice;
    private Integer proposedPaymentFee;
}
