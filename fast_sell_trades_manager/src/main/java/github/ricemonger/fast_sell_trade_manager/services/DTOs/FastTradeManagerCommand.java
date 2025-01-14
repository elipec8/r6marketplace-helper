package github.ricemonger.fast_sell_trade_manager.services.DTOs;


import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class FastTradeManagerCommand implements Comparable<FastTradeManagerCommand> {
    private final AuthorizationDTO authorizationDTO;
    private final FastTradeManagerCommandType commandType;
    private final String itemId;
    private final String tradeId;
    private final Integer newPrice;

    // CANCEL
    public FastTradeManagerCommand(AuthorizationDTO authorizationDTO, FastTradeManagerCommandType commandType, String itemId, String tradeId) {
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.tradeId = tradeId;
        this.newPrice = null;
    }

    // UPDATE
    public FastTradeManagerCommand(AuthorizationDTO authorizationDTO, FastTradeManagerCommandType commandType, String itemId, String tradeId, Integer newPrice) {
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.tradeId = tradeId;
        this.newPrice = newPrice;
    }

    // CREATE
    public FastTradeManagerCommand(AuthorizationDTO authorizationDTO, FastTradeManagerCommandType commandType, String itemId, Integer newPrice) {
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.tradeId = null;
        this.newPrice = newPrice;
    }

    @Override
    public int compareTo(FastTradeManagerCommand o) {
        return this.commandType.compareTo(o.commandType);
    }
}
