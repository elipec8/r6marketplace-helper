package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.Game;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.MarketableItem;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.MarketData;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.marketData.SellStats;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.Nodes;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.services.TagService;
import github.ricemonger.utils.dtos.PersonalItem;
import github.ricemonger.utils.dtos.Trade;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOneItemMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalQueryOneItemMapper {

    private final CommonValuesService commonValuesService;

    public PersonalItem mapItem(Game game) throws GraphQlPersonalOneItemMappingException {
        if (game == null) {
            throw new GraphQlPersonalOneItemMappingException("Game is null");
        }

        MarketableItem marketableItem = game.getMarketableItem();
        if (marketableItem == null) {
            throw new GraphQlPersonalOneItemMappingException("MarketableItem is null in Game: " + game);
        }

        github.ricemonger.marketplace.graphQl.dtos.personal_query_one_item.game.marketableItem.Item item = marketableItem.getItem();

        if (item == null || item.getItemId() == null || item.getAssetUrl() == null || item.getName() == null || item.getTags() == null || item.getType() == null) {
            throw new GraphQlPersonalOneItemMappingException("Item or one of it's fields is null in MarketableItem: " + marketableItem);
        }

        PersonalItem result = new PersonalItem();
        result.setItemId(item.getItemId());
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

        if (marketData == null) {
            throw new GraphQlPersonalOneItemMappingException("MarketData is null in MarketableItem: " + marketableItem);
        }

        BuyStats buyStats = marketData.getBuyStats() == null || marketData.getBuyStats().length == 0 ? null : marketData.getBuyStats()[0];
        SellStats sellStats = marketData.getSellStats() == null || marketData.getSellStats().length == 0 ? null : marketData.getSellStats()[0];
        LastSoldAt lastSoldAt = marketData.getLastSoldAt() == null || marketData.getLastSoldAt().length == 0 ? null : marketData.getLastSoldAt()[0];

        if (buyStats != null) {
            if (buyStats.getHighestPrice() == null || buyStats.getActiveCount() == null) {
                throw new GraphQlPersonalOneItemMappingException("BuyStats highest price or active count is null, buyStats-" + buyStats);
            }
            result.setMaxBuyPrice(buyStats.getHighestPrice());
            result.setBuyOrdersCount(buyStats.getActiveCount());
        } else {
            result.setMaxBuyPrice(0);
            result.setBuyOrdersCount(0);
        }

        if (sellStats != null) {
            if (sellStats.getLowestPrice() == null || sellStats.getActiveCount() == null) {
                throw new GraphQlPersonalOneItemMappingException("SellStats lowest price or active count is null, sellStats-" + sellStats);
            }
            result.setMinSellPrice(sellStats.getLowestPrice());
            result.setSellOrdersCount(sellStats.getActiveCount());
        } else {
            result.setMinSellPrice(0);
            result.setSellOrdersCount(0);
        }

        if (lastSoldAt != null) {
            if (lastSoldAt.getPerformedAt() == null || lastSoldAt.getPrice() == null) {
                throw new GraphQlPersonalOneItemMappingException("LastSoldAt performed at is null, lastSoldAt-" + lastSoldAt);
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
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

        if (marketableItem.getPaymentLimitations() == null || marketableItem.getPaymentLimitations().getMinPrice() == null || marketableItem.getPaymentLimitations().getMaxPrice() == null) {
            throw new GraphQlPersonalOneItemMappingException("PaymentLimitations or it's field is null in MarketableItem: " + marketableItem);
        }

        if (marketableItem.getItem().getViewer() == null || marketableItem.getItem().getViewer().getMeta() == null || marketableItem.getItem().getViewer().getMeta().getIsOwned() == null) {
            throw new GraphQlPersonalOneItemMappingException("Item's viewer or it's field is null in MarketableItem: " + marketableItem);
        }
        result.setOwned(marketableItem.getItem().getViewer().getMeta().getIsOwned());


        if (game.getViewer() == null || game.getViewer().getMeta() == null || game.getViewer().getMeta().getTrades() == null || game.getViewer().getMeta().getTrades().getNodes() == null) {
            throw new GraphQlPersonalOneItemMappingException("Game's Viewer or it's field is null in Game: " + game);
        }
        result.setTrades(game.getViewer().getMeta().getTrades().getNodes().stream().map((Nodes node) -> mapTrade(node, item.getItemId())).toList());

        return result;
    }

    private Trade mapTrade(Nodes node, String itemId) throws GraphQlPersonalOneItemMappingException {
        if (node == null || node.getTradeId() == null || node.getState() == null || node.getCategory() == null || node.getExpiresAt() == null || node.getLastModifiedAt() == null) {
            throw new GraphQlPersonalOneItemMappingException("Trade node or one of it's fields is null: " + node);
        }
        Trade result = new Trade();

        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());

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
            if (node.getPayment().getPrice() == null || node.getPayment().getTransactionFee() == null) {
                throw new GraphQlPersonalOneItemMappingException("Payment price or transaction fee is null: " + node.getPayment());
            }
            result.setSuccessPaymentPrice(node.getPayment().getPrice());
            result.setSuccessPaymentFee(node.getPayment().getTransactionFee());
        } else {
            result.setSuccessPaymentPrice(0);
            result.setSuccessPaymentFee(0);
        }

        PaymentOptions paymentOptions = node.getPaymentOptions() == null || node.getPaymentOptions().length == 0 ? null : node.getPaymentOptions()[0];
        PaymentProposal paymentProposal = node.getPaymentProposal();

        if (paymentOptions == null && paymentProposal == null) {
            throw new GraphQlPersonalOneItemMappingException("Both PaymentOptions and PaymentProposal are null: " + node);
        } else if (paymentOptions != null && paymentProposal != null) {
            throw new GraphQlPersonalOneItemMappingException("Both PaymentOptions and PaymentProposal are not null: " + node);
        } else if (paymentOptions != null) {
            if (paymentOptions.getPrice() == null) {
                throw new GraphQlPersonalOneItemMappingException("PaymentOptions price is null: " + paymentOptions);
            }
            result.setProposedPaymentPrice(paymentOptions.getPrice());
            result.setProposedPaymentFee((int) Math.ceil(paymentOptions.getPrice() / 10.));
        } else if (paymentProposal != null) {
            if (paymentProposal.getPrice() == null || paymentProposal.getTransactionFee() == null) {
                throw new GraphQlPersonalOneItemMappingException("PaymentProposal price or transaction fee is null: " + paymentProposal);
            }
            result.setProposedPaymentPrice(paymentProposal.getPrice());
            result.setProposedPaymentFee(paymentProposal.getTransactionFee());
        }

        return result;
    }
}
