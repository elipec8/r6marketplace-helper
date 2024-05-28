package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.UbiServiceConfiguration;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.Trades;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.Nodes;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_finished_orders.trades.nodes.TradeItems;
import github.ricemonger.utils.dtos.Trade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PersonalQueryFinishedOrdersMapper {

    private final SimpleDateFormat sdf;

    public PersonalQueryFinishedOrdersMapper(UbiServiceConfiguration ubiServiceConfiguration) {
        this.sdf = new SimpleDateFormat(ubiServiceConfiguration.getDateFormat());
    }

    public Collection<Trade> mapFinishedOrders(Trades trades) {
        List<Nodes> nodes = trades.getNodes();

        return nodes.stream().map(this::mapFinishedOrder).toList();
    }

    public Trade mapFinishedOrder(Nodes node) {
        Trade.TradeBuilder builder = Trade.builder();

        builder.tradeId(node.getTradeId());

        try {
            builder.state(TradeState.valueOf(node.getState()));
        } catch (IllegalArgumentException e) {
            builder.state(TradeState.Unknown);
            log.error("Invalid tradeState: {}", node.getState());
        }

        try {
            builder.category(TradeCategory.valueOf(node.getCategory()));
        } catch (IllegalArgumentException e) {
            builder.category(TradeCategory.Unknown);
            log.error("Invalid tradeCategory: {}", node.getCategory());
        }

        try {
            builder.expiresAt(sdf.parse(node.getExpiresAt()));
        } catch (ParseException e) {
            builder.expiresAt(new Date(0));
            log.error("Invalid expiresAt: {}", node.getExpiresAt());
        }

        try {
            builder.lastModifiedAt(sdf.parse(node.getLastModifiedAt()));
        } catch (ParseException e) {
            builder.lastModifiedAt(new Date(0));
            log.error("Invalid lastModifiedAt: {}", node.getLastModifiedAt());
        }

        TradeItems tradeItems = node.getTradeItems() == null ? null : node.getTradeItems()[0];
        if (tradeItems != null && tradeItems.getItem() != null) {
            builder.itemId(tradeItems.getItem().getItemId());
        } else {
            builder.itemId("");
            log.error("Invalid tradeItem or itemId: {}", tradeItems);
        }

        if(node.getPayment() != null){
            builder.successPaymentPrice(node.getPayment().getPrice());
            builder.successPaymentFee(node.getPayment().getTransactionFee());
        }
        else{
            builder.successPaymentPrice(0);
            builder.successPaymentFee(0);
            log.error("Invalid payment: {}", node.getPayment());
        }


        PaymentOptions paymentOptions = node.getPaymentOptions() == null ? null : node.getPaymentOptions()[0];

        if (paymentOptions != null) {
            builder.proposedPaymentPrice(paymentOptions.getPrice());
            builder.proposedPaymentFee((int) Math.ceil(paymentOptions.getPrice() / 10.));
        } else if (node.getPaymentProposal() != null) {
            builder.proposedPaymentPrice(node.getPaymentProposal().getPrice());
            builder.proposedPaymentFee(node.getPaymentProposal().getTransactionFee());
        } else {
            builder.proposedPaymentPrice(0);
            builder.proposedPaymentFee(0);
            log.error("Invalid paymentOptions or paymentProposal: {}", paymentOptions);
        }

        return builder.build();
    }
}
