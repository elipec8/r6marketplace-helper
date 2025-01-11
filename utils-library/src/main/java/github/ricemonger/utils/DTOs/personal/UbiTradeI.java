package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;

import java.time.LocalDateTime;

public interface UbiTradeI {
    String getTradeId();

    void setTradeId(String tradeId);

    TradeState getState();

    void setState(TradeState state);

    TradeCategory getCategory();

    void setCategory(TradeCategory category);

    LocalDateTime getExpiresAt();

    void setExpiresAt(LocalDateTime expiresAt);

    LocalDateTime getLastModifiedAt();

    void setLastModifiedAt(LocalDateTime lastModifiedAt);

    Item getItem();

    void setItem(Item item);

    Integer getSuccessPaymentPrice();

    void setSuccessPaymentPrice(Integer successPaymentPrice);

    Integer getSuccessPaymentFee();

    void setSuccessPaymentFee(Integer successPaymentFee);

    Integer getProposedPaymentPrice();

    void setProposedPaymentPrice(Integer proposedPaymentPrice);

    Integer getProposedPaymentFee();

    void setProposedPaymentFee(Integer proposedPaymentFee);

    String getItemId();
}
