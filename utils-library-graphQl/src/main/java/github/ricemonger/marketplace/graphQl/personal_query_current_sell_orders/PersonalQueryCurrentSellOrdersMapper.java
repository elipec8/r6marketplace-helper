package github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders;

import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.Trades;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.trades.Nodes;
import github.ricemonger.marketplace.graphQl.personal_query_current_sell_orders.DTO.trades.nodes.PaymentOptions;
import github.ricemonger.utils.DTOs.personal.SellTrade;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalCurrentSellOrderMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PersonalQueryCurrentSellOrdersMapper {

    public List<SellTrade> mapCurrentOrders(Trades trades) throws GraphQlPersonalCurrentSellOrderMappingException {
        if (trades == null) {
            throw new GraphQlPersonalCurrentSellOrderMappingException("Trades is null");
        }
        List<Nodes> nodes = trades.getNodes();

        return nodes.stream().map(this::mapCurrentOrder).toList();
    }

    public SellTrade mapCurrentOrder(Nodes node) throws GraphQlPersonalCurrentSellOrderMappingException {
        if (node == null) {
            throw new GraphQlPersonalCurrentSellOrderMappingException("Node is null");
        }

        SellTrade result = new SellTrade();

        if (node.getTradeId() == null
            || node.getTradeItems() == null || node.getTradeItems().length == 0
            || node.getTradeItems()[0].getItem() == null
            || node.getTradeItems()[0].getItem().getItemId() == null) {
            throw new GraphQlPersonalCurrentSellOrderMappingException("One of node fields is null-" + node);
        }

        result.setTradeId(node.getTradeId());

        result.setItemId(node.getTradeItems()[0].getItem().getItemId());

        PaymentOptions[] paymentOptions = node.getPaymentOptions();

        boolean paymentOptionsIsNull = paymentOptions == null || paymentOptions.length == 0 || paymentOptions[0].getPrice() == null;

        if (!paymentOptionsIsNull) {
            result.setPrice(paymentOptions[0].getPrice());
        } else {
            throw new GraphQlPersonalCurrentSellOrderMappingException("Node doesnt have paymentOptions-" + node);
        }

        return result;
    }
}
