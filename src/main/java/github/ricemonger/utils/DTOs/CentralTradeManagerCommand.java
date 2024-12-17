package github.ricemonger.utils.DTOs;

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
    private String chatId;
    private boolean privateNotificationsEnabledFlag;
    private String tradeId;

    private Integer oldPrice;

    private Integer newPrice;

    //Cancel no notification
    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, CentralTradeManagerCommandType commandType, String itemId, String itemName,
                                      String tradeId, Integer oldPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.tradeId = tradeId;
        this.oldPrice = oldPrice;
    }

    //Update no notification
    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, CentralTradeManagerCommandType commandType, String itemId, String itemName,
                                      String tradeId, Integer oldPrice, Integer newPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.tradeId = tradeId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    //Create no notification
    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, CentralTradeManagerCommandType commandType, String itemId,
                                      String itemName, Integer newPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.newPrice = newPrice;
    }

    //Cancel with notification possibility
    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, String chatId, boolean privateNotificationsEnabledFlag, CentralTradeManagerCommandType commandType, String itemId, String itemName,
                                      String tradeId, Integer oldPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.chatId = chatId;
        this.privateNotificationsEnabledFlag = privateNotificationsEnabledFlag;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.tradeId = tradeId;
        this.oldPrice = oldPrice;
    }

    //Update with notification possibility
    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, String chatId, boolean privateNotificationsEnabledFlag, CentralTradeManagerCommandType commandType, String itemId, String itemName,
                                      String tradeId, Integer oldPrice, Integer newPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.chatId = chatId;
        this.privateNotificationsEnabledFlag = privateNotificationsEnabledFlag;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.tradeId = tradeId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    //Create with notification possibility
    public CentralTradeManagerCommand(Long userId, AuthorizationDTO authorizationDTO, String chatId, boolean privateNotificationsEnabledFlag, CentralTradeManagerCommandType commandType, String itemId,
                                      String itemName, Integer newPrice) {
        this.userId = userId;
        this.authorizationDTO = authorizationDTO;
        this.chatId = chatId;
        this.privateNotificationsEnabledFlag = privateNotificationsEnabledFlag;
        this.commandType = commandType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.newPrice = newPrice;
    }

    @Override
    public int compareTo(@NotNull CentralTradeManagerCommand o) {
        return this.commandType.compareTo(o.commandType);
    }
}
