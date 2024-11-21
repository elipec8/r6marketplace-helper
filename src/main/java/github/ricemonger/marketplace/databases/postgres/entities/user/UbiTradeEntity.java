package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity(name = "ubi_user_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiTradeEntity {
    @Id
    private String tradeId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ItemEntity item;

    @Enumerated(EnumType.ORDINAL)
    private TradeState state;
    @Enumerated(EnumType.ORDINAL)
    private TradeCategory category;
    private LocalDateTime expiresAt;
    private LocalDateTime lastModifiedAt;

    private int successPaymentPrice;
    private int successPaymentFee;

    private int proposedPaymentPrice;
    private int proposedPaymentFee;

    public UbiTrade toUbiTrade() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setTradeId(tradeId);
        ubiTrade.setItemId(item.getItemId());

        ubiTrade.setState(state);
        ubiTrade.setCategory(category);
        ubiTrade.setExpiresAt(expiresAt);
        ubiTrade.setLastModifiedAt(lastModifiedAt);

        ubiTrade.setSuccessPaymentPrice(successPaymentPrice);
        ubiTrade.setSuccessPaymentFee(successPaymentFee);

        ubiTrade.setProposedPaymentPrice(proposedPaymentPrice);
        ubiTrade.setProposedPaymentFee(proposedPaymentFee);

        return ubiTrade;
    }
}
