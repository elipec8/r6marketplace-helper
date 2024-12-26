package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.Trades;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.Nodes;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.nodes.TradeItems;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_current_orders.trades.nodes.tradeItems.Item;
import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCurrentOrderMappingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PersonalQueryCurrentOrdersMapperTest {
    private final GraphQlCommonValuesService commonValuesService = mock(GraphQlCommonValuesService.class);

    private final PersonalQueryCurrentOrdersMapper personalQueryCurrentOrdersMapper = spy(new PersonalQueryCurrentOrdersMapper(commonValuesService));


    @Test
    public void mapCurrentOrders_should_map_each_order() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node1 = createNode(dtf, date, date2);
        node1.setPaymentProposal(null);
        node1.setTradeId("tradeId1");

        Nodes node2 = createNode(dtf, date, date2);
        node2.setPaymentProposal(null);
        node2.setTradeId("tradeId2");

        Nodes node3 = createNode(dtf, date, date2);
        node3.setPaymentProposal(null);
        node3.setTradeId("tradeId3");

        Trades trades = new Trades(List.of(node1, node2, node3));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        assertEquals(3, personalQueryCurrentOrdersMapper.mapCurrentOrders(trades).size());

        verify(personalQueryCurrentOrdersMapper, times(3)).mapCurrentOrder(any());
    }

    @Test
    public void mapCurrentOrders_should_throw_if_null_trades() {
        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrders(null);
        });
    }

    @Test
    public void mapCurrentOrder_should_map_order_if_valid_fields_and_null_paymentProposal() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(0);
        expected.setSuccessPaymentFee(0);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapCurrentOrder(node));
    }

    @Test
    public void mapCurrentOrder_should_map_order_if_valid_fields_and_null_paymentOptions() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);


        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(0);
        expected.setSuccessPaymentFee(0);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapCurrentOrder(node));
    }

    @Test
    public void mapCurrentOrder_should_map_order_if_invalid_tradeState() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setState("invalid");

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Unknown);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(0);
        expected.setSuccessPaymentFee(0);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapCurrentOrder(node));
    }

    @Test
    public void mapCurrentOrder_should_map_order_if_invalid_tradeCategory() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory("invalid");

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Unknown);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(0);
        expected.setSuccessPaymentFee(0);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapCurrentOrder(node));
    }

    @Test
    public void mapCurrentOrder_should_throw_if_valid_fields_and_both_paymentOptions_and_paymentProposal() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_node() {
        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(null);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_tradeId() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeId(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_state() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setState(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_category() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_expiresAt() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setExpiresAt(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_lastModifiedAt() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setLastModifiedAt(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_tradeItems() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);
        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_empty_tradeItems() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(new TradeItems[0]);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_item() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].setItem(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_itemId() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].getItem().setItemId(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_paymentProposal_and_paymentOptions() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_empty_paymentOptions() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(new PaymentOptions[0]);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_paymentOptions_price() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPaymentOptions()[0].setPrice(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    @Test
    public void mapCurrentOrder_should_throw_if_null_paymentProposal_price() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);
        node.getPaymentProposal().setPrice(null);

        assertThrows(GraphQlPersonalCurrentOrderMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapCurrentOrder(node);
        });
    }

    private Nodes createNode(DateTimeFormatter dtf, LocalDateTime date1, LocalDateTime date2) {
        Item item = new Item();
        item.setItemId("1");
        TradeItems tradeItems = new TradeItems();
        tradeItems.setItem(item);

        PaymentOptions paymentOption = new PaymentOptions(100);

        PaymentProposal paymentProposal = new PaymentProposal(100);

        return new Nodes("tradeId",
                TradeState.Created.name(),
                TradeCategory.Buy.name(),
                dtf.format(date1),
                dtf.format(date2),
                new TradeItems[]{tradeItems},
                new PaymentOptions[]{paymentOption},
                paymentProposal);
    }
}
