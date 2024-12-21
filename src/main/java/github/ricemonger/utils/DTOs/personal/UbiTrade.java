package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public String getItemId() {
        return item == null ? null : item.getItemId();
    }

    public String getItemName() {
        return item == null ? null : item.getName();
    }

    public boolean isFullyEqualExceptItem(UbiTrade other) {
        if(other == null) {
            return false;
        }
        boolean itemsAreEqual = item == null ? other.item == null : Objects.equals(item.getItemId(), other.item.getItemId());

        return tradeId.equals(other.tradeId) &&
               state == other.state &&
               category == other.category &&
               expiresAt.equals(other.expiresAt) &&
               lastModifiedAt.equals(other.lastModifiedAt) &&
               itemsAreEqual &&
               successPaymentPrice.equals(other.successPaymentPrice) &&
               successPaymentFee.equals(other.successPaymentFee) &&
               proposedPaymentPrice.equals(other.proposedPaymentPrice) &&
               proposedPaymentFee.equals(other.proposedPaymentFee);
    }
}
