package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.Game;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.MarketableItem;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.MarketData;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData.SellStats;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.Nodes;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentOptions;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.Trade;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class PersonalQueryOneItemMapper {

    private final SimpleDateFormat sdf;

    public PersonalQueryOneItemMapper(UbiServiceConfiguration ubiServiceConfiguration) {
        this.sdf = new SimpleDateFormat(ubiServiceConfiguration.getDateFormat());
    }

    public Item mapItem(Game game) {

        MarketableItem marketableItem = game.getMarketableItem();
        github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.Item item = marketableItem.getItem();
        String itemId = item.getItemId();

        Item result = new Item();
        result.setItemId(itemId);
        result.setAssetUrl(item.getAssetUrl());
        result.setName(item.getName());
        result.setTags(item.getTags());

        try {
            result.setType(ItemType.valueOf(item.getType()));
        } catch (IllegalArgumentException e) {
            log.error("Unknown item type: {}", item.getType());
            result.setType(ItemType.Unknown);
        }

        MarketData marketData = marketableItem.getMarketData();
        BuyStats buyStats = marketData.getBuyStats() == null ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null ? null : marketData.getSellStats()[0];
        LastSoldAt lastSoldAt = marketData.getLastSoldAt() == null ? null : marketData.getLastSoldAt()[0];

        if (buyStats != null) {
            result.setMaxBuyPrice(buyStats.getHighestPrice());
            result.setBuyOrdersCount(buyStats.getActiveCount());
        } else {
            result.setMaxBuyPrice(0);
            result.setBuyOrdersCount(0);
        }

        if (sellStats != null) {
            result.setMinSellPrice(sellStats.getLowestPrice());
            result.setSellOrdersCount(sellStats.getActiveCount());
        } else {
            result.setMinSellPrice(0);
            result.setSellOrdersCount(0);
        }

        if (lastSoldAt != null) {
            try {
                result.setLastSoldAt(sdf.parse(lastSoldAt.getPerformedAt()));
            } catch (ParseException e) {
                result.setLastSoldAt(new Date(0));
                log.error("Error parsing date: {}", lastSoldAt.getPerformedAt());
            }
            result.setLastSoldPrice(lastSoldAt.getPrice());
        } else {
            result.setLastSoldAt(new Date(0));
            result.setLastSoldPrice(0);
        }

        result.setLimitMinPrice(marketableItem.getPaymentLimitations().getMinPrice());
        result.setLimitMaxPrice(marketableItem.getPaymentLimitations().getMaxPrice());

        result.setOwned(marketableItem.getItem().getViewer().getMeta().isOwned());

        result.setTrades(game.getViewer().getMeta().getTrades().getNodes().stream().map((Nodes node) -> mapTrade(node, itemId)).toList());


        return result;
    }

    private Trade mapTrade(Nodes node, String itemId) {
        Trade result = new Trade();

        result.setTradeId(node.getTradeId());
        result.setItemId(itemId);

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

        if (node.getPayment() != null) {
            result.setSuccessPaymentPrice(node.getPayment().getPrice());
            result.setSuccessPaymentFee(node.getPayment().getTransactionFee());
        } else {
            result.setSuccessPaymentPrice(0);
            result.setSuccessPaymentFee(0);
            log.error("Invalid payment: {}", node.getPayment());
        }


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
