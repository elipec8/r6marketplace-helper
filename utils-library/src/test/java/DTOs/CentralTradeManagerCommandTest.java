package DTOs;

import github.ricemonger.utils.DTOs.personal.CentralTradeManagerCommand;
import github.ricemonger.utils.enums.CentralTradeManagerCommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CentralTradeManagerCommandTest {

    @Test
    public void compareTo_should_compareByCommandType_only() {
        CentralTradeManagerCommand buyOrderCancel = new CentralTradeManagerCommand(null, null, null, null, CentralTradeManagerCommandType.BUY_ORDER_CANCEL, null, null, null, null);
        CentralTradeManagerCommand buyOrderUpdate = new CentralTradeManagerCommand(null, null, null, null, CentralTradeManagerCommandType.BUY_ORDER_UPDATE, null, null, null, null);
        CentralTradeManagerCommand buyOrderCreate = new CentralTradeManagerCommand(null, null, null, null, CentralTradeManagerCommandType.BUY_ORDER_CREATE, null, null, null, null);
        CentralTradeManagerCommand sellOrderCancel = new CentralTradeManagerCommand(null, null, null, null, CentralTradeManagerCommandType.SELL_ORDER_CANCEL, null, null, null, null);
        CentralTradeManagerCommand sellOrderUpdate = new CentralTradeManagerCommand(null, null, null, null, CentralTradeManagerCommandType.SELL_ORDER_UPDATE, null, null, null, null);
        CentralTradeManagerCommand sellOrderCreate = new CentralTradeManagerCommand(null, null, null, null, CentralTradeManagerCommandType.SELL_ORDER_CREATE, null, null, null, null);

        assertEquals(-1, buyOrderCancel.compareTo(buyOrderUpdate));
        assertEquals(-1, buyOrderUpdate.compareTo(buyOrderCreate));
        assertEquals(-1, buyOrderCreate.compareTo(sellOrderCancel));
        assertEquals(-1, sellOrderCancel.compareTo(sellOrderUpdate));
        assertEquals(-1, sellOrderUpdate.compareTo(sellOrderCreate));
    }
}