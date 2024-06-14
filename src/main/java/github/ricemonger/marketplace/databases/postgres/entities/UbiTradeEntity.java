package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity(name = "ubi_trade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UbiTradeEntity {
    @Id
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
