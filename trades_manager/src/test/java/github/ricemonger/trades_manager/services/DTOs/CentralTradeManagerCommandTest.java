package github.ricemonger.trades_manager.services.DTOs;

import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CentralTradeManagerCommandTest {

    @Test
    public void compareTo_should_compareByCommandType_only() {
        CentralTradeManagerCommand buyOrderCancel = new CentralTradeManagerCommand(null, null, CentralTradeManagerCommandType.BUY_ORDER_CANCEL, null, null, null, null);
        CentralTradeManagerCommand buyOrderUpdate = new CentralTradeManagerCommand(null, null, CentralTradeManagerCommandType.BUY_ORDER_UPDATE, null, null, null, null);
        CentralTradeManagerCommand buyOrderCreate = new CentralTradeManagerCommand(null, null, CentralTradeManagerCommandType.BUY_ORDER_CREATE, null, null, null, null);
        CentralTradeManagerCommand sellOrderCancel = new CentralTradeManagerCommand(null, null, CentralTradeManagerCommandType.SELL_ORDER_CANCEL, null, null, null, null);
        CentralTradeManagerCommand sellOrderUpdate = new CentralTradeManagerCommand(null, null, CentralTradeManagerCommandType.SELL_ORDER_UPDATE, null, null, null, null);
        CentralTradeManagerCommand sellOrderCreate = new CentralTradeManagerCommand(null, null, CentralTradeManagerCommandType.SELL_ORDER_CREATE, null, null, null, null);

        Assertions.assertEquals(-1, buyOrderCancel.compareTo(buyOrderUpdate));
        Assertions.assertEquals(-1, buyOrderUpdate.compareTo(buyOrderCreate));
        Assertions.assertEquals(-1, buyOrderCreate.compareTo(sellOrderCancel));
        Assertions.assertEquals(-1, sellOrderCancel.compareTo(sellOrderUpdate));
        Assertions.assertEquals(-1, sellOrderUpdate.compareTo(sellOrderCreate));
    }
}