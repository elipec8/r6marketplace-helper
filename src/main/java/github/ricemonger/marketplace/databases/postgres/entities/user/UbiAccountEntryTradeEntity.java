package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Entity(name = "ubi_user_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryTradeEntity {
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
