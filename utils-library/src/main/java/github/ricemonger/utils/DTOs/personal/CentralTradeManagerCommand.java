package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
public class CentralTradeManagerCommand implements Comparable<CentralTradeManagerCommand> {
    private final Long userId;
    private final AuthorizationDTO authorizationDTO;
    private final CentralTradeManagerCommandType commandType;
    private final String itemId;
    private final String itemName;
    private final String tradeId;
    private final Integer oldPrice;
    private final Integer newPrice;

    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO,  CentralTradeManagerCommandType commandType, String itemId, String itemName, String tradeId, Integer oldPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.tradeId = tradeId;
        this.oldPrice = oldPrice;
        this.newPrice = null;
    }

    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, CentralTradeManagerCommandType commandType, String itemId, String itemName, String tradeId, Integer oldPrice, Integer newPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.tradeId = tradeId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;

    }

    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, CentralTradeManagerCommandType commandType, String itemId, String itemName, Integer newPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;

        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.newPrice = newPrice;
        this.oldPrice = null;
        this.tradeId = null;
    }

    @Override
    public int compareTo(@NotNull CentralTradeManagerCommand o) {
        return this.commandType.compareTo(o.commandType);
    }
}
