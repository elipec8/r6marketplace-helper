package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.Trades;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.trades.Nodes;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_current_orders.trades.nodes.TradeItems;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.Trade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalQueryCurrentOrdersMapper {

    private final CommonValuesService commonValuesService;

    public Collection<Trade> mapCurrentOrders(Trades trades) {
        List<Nodes> nodes = trades.getNodes();

        return nodes.stream().map(this::mapCurrentOrder).toList();
    }

    public Trade mapCurrentOrder(Nodes node) {
        Trade result = new Trade();

        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());

        result.setTradeId(node.getTradeId());

        try {
            result.setState(TradeState.valueOf(node.getState()));
        } catch (IllegalArgumentException e) {
            result.setState(TradeState.Unknown);
            log.error("Invalid tradeState: {}", node.getState());
        }

        try {
            result.setCategory(TradeCategory.valueOf(node.getCategory()));
        } catch (IllegalArgumentException e) {
            result.setCategory(TradeCategory.Unknown);
            log.error("Invalid tradeCategory: {}", node.getCategory());
        }

        try {
            result.setExpiresAt(sdf.parse(node.getExpiresAt()));
        } catch (ParseException e) {
            result.setExpiresAt(new Date(0));
            log.error("Invalid expiresAt: {}", node.getExpiresAt());
        }

        try {
            result.setLastModifiedAt(sdf.parse(node.getLastModifiedAt()));
        } catch (ParseException e) {
            result.setLastModifiedAt(new Date(0));
            log.error("Invalid lastModifiedAt: {}", node.getLastModifiedAt());
        }

        TradeItems tradeItems = node.getTradeItems() == null ? null : node.getTradeItems()[0];
        if (tradeItems != null && tradeItems.getItem() != null) {
            result.setItemId(tradeItems.getItem().getItemId());
        } else {
            result.setItemId("");
            log.error("Invalid tradeItem or itemId: {}", tradeItems);
        }

        result.setSuccessPaymentPrice(0);
        result.setSuccessPaymentFee(0);

        PaymentOptions paymentOptions = node.getPaymentOptions() == null ? null : node.getPaymentOptions()[0];

        if (paymentOptions != null) {
            result.setProposedPaymentPrice(paymentOptions.getPrice());
            result.setProposedPaymentFee((int) Math.ceil(paymentOptions.getPrice() / 10.));
        } else if (node.getPaymentProposal() != null) {
            result.setProposedPaymentPrice(node.getPaymentProposal().getPrice());
            result.setProposedPaymentFee(node.getPaymentProposal().getTransactionFee());
        } else {
            result.setProposedPaymentPrice(0);
            result.setProposedPaymentFee(0);
            log.error("Invalid paymentOptions or paymentProposal: {}", paymentOptions);
        }

        return result;
    }
}
