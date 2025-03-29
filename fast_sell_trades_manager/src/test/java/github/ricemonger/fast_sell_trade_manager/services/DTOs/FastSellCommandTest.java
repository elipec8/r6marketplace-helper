package github.ricemonger.fast_sell_trade_manager.services.DTOs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastSellCommandTest {

    @Test
    public void compareTo_should_compare_by_TradeCommandType_and_itemId() {
        FastSellCommand sellOrderCancel = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "aaa", 0);
        FastSellCommand sellOrderUpdate = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "a", 0);
        FastSellCommand sellOrderCreate = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_CREATE, "aa", 0);

        FastSellCommand sellOrderCancel1 = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_CANCEL, "aa", 0);
        FastSellCommand sellOrderUpdate1 = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_UPDATE, "aa", 0);
        FastSellCommand sellOrderCreate1 = new FastSellCommand(null, FastTradeManagerCommandType.SELL_ORDER_CREATE, "aaa", 0);

        assertEquals(-1, sellOrderCancel.compareTo(sellOrderUpdate));
        assertEquals(-1, sellOrderUpdate.compareTo(sellOrderCreate));
        assertEquals(-2, sellOrderCancel.compareTo(sellOrderCreate));

        assertEquals(-1, sellOrderCancel1.compareTo(sellOrderUpdate1));
        assertEquals(-1, sellOrderUpdate1.compareTo(sellOrderCreate1));
        assertEquals(-2, sellOrderCancel1.compareTo(sellOrderCreate1));

        assertEquals(-1, sellOrderCancel1.compareTo(sellOrderCancel));
        assertEquals(1, sellOrderUpdate1.compareTo(sellOrderUpdate));
        assertEquals(1, sellOrderCreate1.compareTo(sellOrderCreate));
    }
}