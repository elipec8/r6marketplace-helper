package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.id_entities.item.IdItemEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdTradeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeEntity extends IdTradeEntity {
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    private TradeState state;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category")
    private TradeCategory category;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private ItemEntity item;

    @Column(name = "success_payment_price")
    private Integer successPaymentPrice;
    @Column(name = "success_payment_fee")
    private Integer successPaymentFee;

    @Column(name = "proposed_payment_price")
    private Integer proposedPaymentPrice;
    @Column(name = "proposed_payment_fee")
    private Integer proposedPaymentFee;

    @Column(name = "minutes_to_trade")
    private Integer minutesToTrade;
    @Column(name = "trade_priority")
    private Long tradePriority;
}
