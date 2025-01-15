package github.ricemonger.fast_sell_trade_manager.services.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FastTradeManagerCommandTest {

    @Test
    public void compareTo_should_compare_by_TradeCommandType_only() {
        FastTradeManagerCommand sellOrderCancel = new FastTradeManagerCommand(null, FastTradeManagerCommandType.SELL_ORDER_CANCEL, null, 0);
        FastTradeManagerCommand sellOrderUpdate = new FastTradeManagerCommand(null, FastTradeManagerCommandType.SELL_ORDER_UPDATE, null, 0);
        FastTradeManagerCommand sellOrderCreate = new FastTradeManagerCommand(null, FastTradeManagerCommandType.SELL_ORDER_CREATE, null, 0);

        assertEquals(-1, sellOrderCancel.compareTo(sellOrderUpdate));
        assertEquals(-1, sellOrderUpdate.compareTo(sellOrderCreate));
    }

}