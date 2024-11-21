package github.ricemonger.marketplace.graphQl.mappers;


import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.Trades;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.trades.Nodes;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.trades.nodes.Payment;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.trades.nodes.TradeItems;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_finished_orders.trades.nodes.tradeItems.Item;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalFinishedOrdersMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PersonalQueryFinishedOrdersMapperTest {
    @SpyBean
    private PersonalQueryFinishedOrdersMapper personalQueryCurrentOrdersMapper;
    @Autowired
    private CommonValuesService commonValuesService;

    @Test
    public void mapFinishedOrders_should_map_each_order() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

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

        assertEquals(3, personalQueryCurrentOrdersMapper.mapFinishedOrders(trades).size());

        verify(personalQueryCurrentOrdersMapper, times(3)).mapFinishedOrder(any());
    }

    @Test
    public void mapFinishedOrders_should_throw_if_null_trades() {
        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrders(null);
        });
    }

    @Test
    public void mapFinishedOrder_should_map_order_if_valid_fields_and_null_paymentProposal() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItemId("1");

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapFinishedOrder(node));
    }

    @Test
    public void mapFinishedOrder_should_map_order_if_valid_fields_and_null_paymentOptions() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItemId("1");

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapFinishedOrder(node));
    }

    @Test
    public void mapFinishedOrder_should_map_order_if_null_payment() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPayment(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItemId("1");

        expected.setSuccessPaymentPrice(0);
        expected.setSuccessPaymentFee(0);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapFinishedOrder(node));
    }

    @Test
    public void mapFinishedOrder_should_map_order_if_invalid_tradeState() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setState("invalid");

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Unknown);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItemId("1");

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapFinishedOrder(node));
    }

    @Test
    public void mapFinishedOrder_should_map_order_if_invalid_tradeCategory() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory("invalid");

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Unknown);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItemId("1");

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryCurrentOrdersMapper.mapFinishedOrder(node));
    }

    @Test
    public void mapFinishedOrder_should_throw_if_valid_fields_and_both_paymentOptions_and_paymentProposal() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_node() {
        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(null);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_tradeId() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeId(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_state() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setState(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_category() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_expiresAt() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setExpiresAt(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_lastModifiedAt() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setLastModifiedAt(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_tradeItems() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_empty_tradeItems() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(new TradeItems[0]);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_item() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].setItem(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_itemId() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].getItem().setItemId(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_payment_price() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPayment().setPrice(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_payment_transactionFee() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPayment().setTransactionFee(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentProposal_and_paymentOptions() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_empty_paymentOptions() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(new PaymentOptions[0]);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentOptions_price() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPaymentOptions()[0].setPrice(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentProposal_price() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);
        node.getPaymentProposal().setPrice(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentProposal_fee() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.MIN;
        LocalDateTime date2 = LocalDateTime.MAX;

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);
        node.getPaymentProposal().setTransactionFee(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    private Nodes createNode(DateTimeFormatter dtf, LocalDateTime date1, LocalDateTime date2) {
        Item item = new Item();
        item.setItemId("1");
        TradeItems tradeItems = new TradeItems();
        tradeItems.setItem(item);

        Payment payment = new Payment(1000, 100);

        PaymentOptions paymentOption = new PaymentOptions();
        paymentOption.setPrice(100);

        PaymentProposal paymentProposal = new PaymentProposal(100, 10);

        return new Nodes("tradeId",
                TradeState.Created.name(),
                TradeCategory.Buy.name(),
                dtf.format(date1),
                dtf.format(date2),
                new TradeItems[]{tradeItems},
                payment,
                new PaymentOptions[]{paymentOption},
                paymentProposal);
    }
}