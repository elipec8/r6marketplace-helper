package github.ricemonger.marketplace.graphQl.mappers;


import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.Trades;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.Nodes;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.Payment;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.TradeItems;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.tradeItems.Item;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.Trade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.GraphQlPersonalFinishedOrdersMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node1 = createNode(sdf, date, date2);
        node1.setPaymentProposal(null);
        node1.setTradeId("tradeId1");

        Nodes node2 = createNode(sdf, date, date2);
        node2.setPaymentProposal(null);
        node2.setTradeId("tradeId2");

        Nodes node3 = createNode(sdf, date, date2);
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);

        Trade expected = new Trade();
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentOptions(null);

        Trade expected = new Trade();
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setPayment(null);

        Trade expected = new Trade();
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setState("invalid");

        Trade expected = new Trade();
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory("invalid");

        Trade expected = new Trade();
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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);

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
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeId(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_state() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setState(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_category() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_expiresAt() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setExpiresAt(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_lastModifiedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setLastModifiedAt(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_tradeItems() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_empty_tradeItems() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(new TradeItems[0]);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_item() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].setItem(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_itemId() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].getItem().setItemId(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_payment_price() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.getPayment().setPrice(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_payment_transactionFee() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.getPayment().setTransactionFee(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentProposal_and_paymentOptions() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_empty_paymentOptions() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(new PaymentOptions[0]);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentOptions_price() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentProposal(null);
        node.getPaymentOptions()[0].setPrice(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentProposal_price() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentOptions(null);
        node.getPaymentProposal().setPrice(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    @Test
    public void mapFinishedOrder_should_throw_if_null_paymentProposal_fee() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 1000);

        Nodes node = createNode(sdf, date, date2);
        node.setPaymentOptions(null);
        node.getPaymentProposal().setTransactionFee(null);

        assertThrows(GraphQlPersonalFinishedOrdersMappingException.class, () -> {
            personalQueryCurrentOrdersMapper.mapFinishedOrder(node);
        });
    }

    private Nodes createNode(SimpleDateFormat sdf, Date date1, Date date2) {
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
                sdf.format(date1),
                sdf.format(date2),
                new TradeItems[]{tradeItems},
                payment,
                new PaymentOptions[]{paymentOption},
                paymentProposal);
    }
}