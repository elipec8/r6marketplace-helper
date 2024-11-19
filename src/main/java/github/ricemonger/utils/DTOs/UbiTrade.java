package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiTrade {
    private String tradeId;
    private TradeState state;
    private TradeCategory category;
    private Date expiresAt;
    private Date lastModifiedAt;

    private String itemId;

    private int successPaymentPrice;
    private int successPaymentFee;

    private int proposedPaymentPrice;
    private int proposedPaymentFee;
}
