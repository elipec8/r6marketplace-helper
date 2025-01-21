package github.ricemonger.fast_sell_trade_manager.services.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastSellCommandTest {

    @Test
    public void compareTo_should_compare_by_TradeCommandType_only() {
        FastSellCommand sellOrderCancel = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_CANCEL, null, 0);
        FastSellCommand sellOrderUpdate = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_UPDATE, null, 0);
        FastSellCommand sellOrderCreate = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_CREATE, null, 0);

        assertEquals(-1, sellOrderCancel.compareTo(sellOrderUpdate));
        assertEquals(-1, sellOrderUpdate.compareTo(sellOrderCreate));
    }

}