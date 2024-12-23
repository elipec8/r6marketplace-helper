package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.Game;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.MarketableItem;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.MarketData;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.SellStats;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.Nodes;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.ItemDetails;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOneItemMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalQueryOneItemMapper {

    private final CommonValuesService commonValuesService;

    public ItemDetails mapItem(Game game) throws GraphQlPersonalOneItemMappingException {
        if (game == null) {
            throw new GraphQlPersonalOneItemMappingException("Game is null");
        }

        MarketableItem marketableItem = game.getMarketableItem();
        if (marketableItem == null) {
            throw new GraphQlPersonalOneItemMappingException("MarketableItem is null in Game: " + game);
        }

        github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.Item item = marketableItem.getItem();

        if (item == null || item.getItemId() == null || item.getAssetUrl() == null || item.getName() == null || item.getTags() == null || item.getType() == null) {
            throw new GraphQlPersonalOneItemMappingException("Item or one of it's fields is null in MarketableItem: " + marketableItem);
        }

        ItemDetails result = new ItemDetails();
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
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
                result.setLastSoldAt(LocalDateTime.parse(lastSoldAt.getPerformedAt(), dtf));
            } catch (DateTimeParseException e) {
                result.setLastSoldAt(LocalDateTime.of(1970, 1, 1, 0, 0));
                log.error("Error parsing date: {}", lastSoldAt.getPerformedAt());
            }
            result.setLastSoldPrice(lastSoldAt.getPrice());
        } else {
            result.setLastSoldAt(LocalDateTime.of(1970, 1, 1, 0, 0));
            result.setLastSoldPrice(0);
        }

        if (marketableItem.getPaymentLimitations() == null || marketableItem.getPaymentLimitations().getMinPrice() == null || marketableItem.getPaymentLimitations().getMaxPrice() == null) {
            throw new GraphQlPersonalOneItemMappingException("PaymentLimitations or it's field is null in MarketableItem: " + marketableItem);
        }

        if (marketableItem.getItem().getViewer() == null || marketableItem.getItem().getViewer().getMeta() == null || marketableItem.getItem().getViewer().getMeta().getIsOwned() == null) {
            throw new GraphQlPersonalOneItemMappingException("Item's viewer or it's field is null in MarketableItem: " + marketableItem);
        }
        result.setIsOwned(marketableItem.getItem().getViewer().getMeta().getIsOwned());


        if (game.getViewer() == null || game.getViewer().getMeta() == null || game.getViewer().getMeta().getTrades() == null || game.getViewer().getMeta().getTrades().getNodes() == null) {
            throw new GraphQlPersonalOneItemMappingException("Game's Viewer or it's field is null in Game: " + game);
        }
        result.setTrades(game.getViewer().getMeta().getTrades().getNodes().stream().map((Nodes node) -> mapTrade(node, item.getItemId())).toList());

        return result;
    }

    private UbiTrade mapTrade(Nodes node, String itemId) throws GraphQlPersonalOneItemMappingException {
        if (node == null || node.getTradeId() == null || node.getState() == null || node.getCategory() == null || node.getExpiresAt() == null || node.getLastModifiedAt() == null) {
            throw new GraphQlPersonalOneItemMappingException("Trade node or one of it's fields is null: " + node);
        }
        UbiTrade result = new UbiTrade();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());

        result.setTradeId(node.getTradeId());
        result.setItem(new Item(itemId));

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
            if (node.getExpiresAt() == null) {
                result.setExpiresAt(LocalDateTime.of(1970, 1, 1, 0, 0));
            }
            result.setExpiresAt(LocalDateTime.parse(node.getExpiresAt(), dtf));
        } catch (DateTimeParseException e) {
            result.setExpiresAt(LocalDateTime.of(1970, 1, 1, 0, 0));
            log.error("Invalid expiresAt {} for Node: {}", node.getExpiresAt(), node);
        }

        try {
            result.setLastModifiedAt(LocalDateTime.parse(node.getLastModifiedAt(), dtf));
        } catch (DateTimeParseException | NullPointerException e) {
            result.setLastModifiedAt(LocalDateTime.of(1970, 1, 1, 0, 0));
            log.error("Invalid lastModifiedAt {} for Node: {}", node.getLastModifiedAt(), node);
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

        PaymentOptions[] paymentOptions = node.getPaymentOptions();
        PaymentProposal paymentProposal = node.getPaymentProposal();

        boolean paymentOptionsIsNull = paymentOptions == null || paymentOptions.length == 0 || paymentOptions[0].getPrice() == null;
        boolean paymentProposalIsNull = paymentProposal == null || paymentProposal.getPrice() == null;

        if (!paymentOptionsIsNull && !paymentProposalIsNull) {
            throw new GraphQlPersonalOneItemMappingException("Node have both paymentOptions and paymentProposal-" + node);
        } else if (!paymentOptionsIsNull) {
            result.setProposedPaymentPrice(paymentOptions[0].getPrice());
            result.setProposedPaymentFee((int) Math.ceil((double) (paymentOptions[0].getPrice() * commonValuesService.getConfigTrades().getFeePercentage()) / 100));
        } else if (!paymentProposalIsNull) {
            result.setProposedPaymentPrice(paymentProposal.getPrice());
            result.setProposedPaymentFee((int) Math.ceil((double) (paymentProposal.getPrice() * commonValuesService.getConfigTrades().getFeePercentage()) / 100));
        } else {
            throw new GraphQlPersonalOneItemMappingException("Node doesnt have neither paymentOptions, neither paymentProposal-" + node);
        }

        return result;
    }
}
