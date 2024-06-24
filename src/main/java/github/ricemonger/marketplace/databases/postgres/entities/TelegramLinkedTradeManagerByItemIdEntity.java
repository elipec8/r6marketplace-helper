package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.enums.TradeManagerTradeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "planned_trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramLinkedTradeManagerByItemIdEntity.class)
public class TelegramLinkedTradeManagerByItemIdEntity {
    @Id
    private String chatId;

    @Id
    private String itemId;

    private TradeManagerTradeType tradeType;

    private Integer sellStartingPrice;

    private Integer sellBoundaryPrice;

    private Integer buyStartingPrice;

    private Integer buyBoundaryPrice;

    private Integer priority;

    public TelegramLinkedTradeManagerByItemIdEntity(TradeManagerByItemId tradeManager) {
        this.chatId = tradeManager.getChatId();
        this.itemId = tradeManager.getItemId();
        this.tradeType = tradeManager.getTradeType();
        this.sellStartingPrice = tradeManager.getSellStartingPrice();
        this.sellBoundaryPrice = tradeManager.getSellBoundaryPrice();
        this.buyStartingPrice = tradeManager.getBuyStartingPrice();
        this.buyBoundaryPrice = tradeManager.getBuyBoundaryPrice();
        this.priority = tradeManager.getPriority();
    }
}
