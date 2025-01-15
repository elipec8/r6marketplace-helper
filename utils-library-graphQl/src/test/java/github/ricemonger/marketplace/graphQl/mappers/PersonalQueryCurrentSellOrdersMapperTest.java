package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.Trades;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.trades.Nodes;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.trades.nodes.TradeItems;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.trades.nodes.tradeItems.Item;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.PersonalQueryCurrentSellOrdersMapper;
import github.ricemonger.utils.DTOs.personal.SellTrade;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCurrentSellOrderMappingException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PersonalQueryCurrentSellOrdersMapperTest {

    private final PersonalQueryCurrentSellOrdersMapper personalQueryCurrentOrdersMapper = spy(new PersonalQueryCurrentSellOrdersMapper());


    @Test
    public void mapCurrentOrders_should_map_each_order() {
        Nodes node1 = createNodeTrades();
        node1.setTradeId("tradeId1");

        Nodes node2 = createNodeTrades();
        node2.setTradeId("tradeId2");

        Nodes node3 = createNodeTrades();
        node3.setTradeId("tradeId3");

        Trades trades = new Trades(List.of(node1, node2, node3));

        assertEquals(3, personalQueryCurrentOrdersMapper.mapCurrentOrders(trades).size());

        verify(personalQueryCurrentOrdersMapper, times(3)).mapCurrentOrder(any());
    }

    @Test
    public void mapCurrentOrders_should_throw_if_null_trades() {
        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrders(null);
        });
    }

    @Test
    public void mapCurrentOrder_should_map_order_if_valid_fields_and_null_paymentProposal() {
        Nodes node = createNodeTrades();

        SellTrade expected = new SellTrade();
        expected.setTradeId("tradeId");
        expected.setItemId("1");
        expected.setPrice(100);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapCurrentOrder(node));
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_node() {
        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(null);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_tradeId() {
        Nodes node = createNodeTrades();
        node.setTradeId(null);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_tradeItems() {
        Nodes node = createNodeTrades();
        node.setTradeItems(null);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_empty_tradeItems() {
        Nodes node = createNodeTrades();
        node.setTradeItems(new TradeItems[0]);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_item() {
        Nodes node = createNodeTrades();
        node.getTradeItems()[0].setItem(null);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_itemId() {
        Nodes node = createNodeTrades();
        node.getTradeItems()[0].getItem().setItemId(null);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_paymentOptions() {
        Nodes node = createNodeTrades();
        node.setPaymentOptions(null);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_empty_paymentOptions() {
        Nodes node = createNodeTrades();
        node.setPaymentOptions(new PaymentOptions[0]);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_paymentOptions_price() {
        Nodes node = createNodeTrades();
        node.getPaymentOptions()[0].setPrice(null);

        assertThrows(GraphQlPersonalCurrentSellOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    private Nodes createNodeTrades() {
        Item item = new Item();
        item.setItemId("1");
        TradeItems tradeItems = new TradeItems();
        tradeItems.setItem(item);

        PaymentOptions paymentOption = new PaymentOptions(100);

        return new Nodes("tradeId",
                new TradeItems[]{tradeItems},
                new PaymentOptions[]{paymentOption});
    }
}
