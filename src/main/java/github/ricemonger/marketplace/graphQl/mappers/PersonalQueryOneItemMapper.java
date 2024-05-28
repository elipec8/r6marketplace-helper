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

        Item.ItemBuilder itemBuilder = Item.builder()
                .itemId(itemId)
                .assetUrl(item.getAssetUrl())
                .name(item.getName())
                .tags(item.getTags());

        try {
            itemBuilder
                    .type(ItemType.valueOf(item.getType()));
        } catch (IllegalArgumentException e) {
            log.error("Unknown item type: {}", item.getType());
            itemBuilder.type(ItemType.Unknown);
        }

        MarketData marketData = marketableItem.getMarketData();
        BuyStats buyStats = marketData.getBuyStats() == null ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null ? null : marketData.getSellStats()[0];
        LastSoldAt lastSoldAt = marketData.getLastSoldAt() == null ? null : marketData.getLastSoldAt()[0];

        if (buyStats != null) {
            itemBuilder
                    .maxBuyPrice(buyStats.getHighestPrice())
                    .buyOrdersCount(buyStats.getActiveCount());
        } else {
            itemBuilder
                    .maxBuyPrice(0)
                    .buyOrdersCount(0);
        }

        if (sellStats != null) {
            itemBuilder
                    .minSellPrice(sellStats.getLowestPrice())
                    .sellOrdersCount(sellStats.getActiveCount());
        } else {
            itemBuilder
                    .minSellPrice(0)
                    .sellOrdersCount(0);
        }

        if (lastSoldAt != null) {
            try {
                itemBuilder
                        .lastSoldAt(sdf.parse(lastSoldAt.getPerformedAt()));
            } catch (ParseException e) {
                itemBuilder
                        .lastSoldAt(new Date(0));
                log.error("Error parsing date: {}", lastSoldAt.getPerformedAt());
            }
            itemBuilder
                    .lastSoldPrice(lastSoldAt.getPrice());
        } else {
            itemBuilder
                    .lastSoldAt(new Date(0))
                    .lastSoldPrice(0);
        }

        itemBuilder.limitMinPrice(marketableItem.getPaymentLimitations().getMinPrice())
                .limitMaxPrice(marketableItem.getPaymentLimitations().getMaxPrice());

        itemBuilder.isOwned(marketableItem.getItem().getViewer().getMeta().isOwned());

        itemBuilder.trades(game.getViewer().getMeta().getTrades().getNodes().stream().map((Nodes node) -> mapTrade(node,itemId)).toList());


        return itemBuilder.build();
    }

    private Trade mapTrade(Nodes node, String itemId) {
        Trade.TradeBuilder builder = Trade.builder();

        builder.tradeId(node.getTradeId());
        builder.itemId(itemId);

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
