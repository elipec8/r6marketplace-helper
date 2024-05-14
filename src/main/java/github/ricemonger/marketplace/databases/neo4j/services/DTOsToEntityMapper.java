package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.enums.ItemType;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.SellStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DTOsToEntityMapper {

    public DTOsToEntityMapper(UbiServiceConfiguration ubisoftServiceConfiguration) {
        performedAtDateFormat = new SimpleDateFormat(ubisoftServiceConfiguration.getPerformedAtDateFormat());
        marketplaceProfitPercent = ubisoftServiceConfiguration.getMarketplaceProfitPercent();
    }

    private final SimpleDateFormat performedAtDateFormat;

    private final float marketplaceProfitPercent;

    public List<ItemEntity> nodesDTOToItemEntities(List<Node> nodes) {
        return nodes.stream().map(this::nodeDTOToItemEntity).collect(Collectors.toList());
    }

    public ItemEntity nodeDTOToItemEntity(Node node) {
        Item itemDTO = node.getItem();
        MarketData marketDataDTO = node.getMarketData();

        ItemEntity.ItemEntityBuilder builder = ItemEntity.builder();

        builder
                .itemFullId(itemDTO.getId())
                .assetUrl(itemDTO.getAssetUrl())
                .name(itemDTO.getName())
                .tags(itemDTO.getTags())
                .type(ItemType.valueOf(itemDTO.getType()));

        buildBuyPriceAndCountFromDTO(builder, marketDataDTO);
        buildSellPriceAndCountFromDTO(builder, marketDataDTO);
        buildLastSoldAtPriceAndDateFromDTO(builder, marketDataDTO);
        buildExpectedProfitAndPercentageFromBuilderValues(builder);

        return builder.build();
    }

    private void buildBuyPriceAndCountFromDTO(ItemEntity.ItemEntityBuilder builder, MarketData marketDataDTO) {

        BuyStats buyStatsDTO = marketDataDTO.getBuyStats() == null ? null : marketDataDTO.getBuyStats()[0];

        if (buyStatsDTO == null) {
            builder
                    .maxBuyPrice(0)
                    .buyOrders(0);
        } else {
            builder
                    .maxBuyPrice(buyStatsDTO.getHighestPrice())
                    .buyOrders(buyStatsDTO.getActiveCount());
        }
    }

    private void buildSellPriceAndCountFromDTO(ItemEntity.ItemEntityBuilder builder, MarketData marketDataDTO) {

        SellStats sellStatsDTO = marketDataDTO.getSellStats() == null ? null : marketDataDTO.getSellStats()[0];

        if (sellStatsDTO == null) {
            builder
                    .minSellPrice(0)
                    .sellOrders(0);
        } else {
            builder
                    .minSellPrice(sellStatsDTO.getLowestPrice())
                    .sellOrders(sellStatsDTO.getActiveCount());
        }
    }

    private void buildLastSoldAtPriceAndDateFromDTO(ItemEntity.ItemEntityBuilder builder, MarketData marketDataDTO) {

        LastSoldAt lastSoldAtDTO = marketDataDTO.getLastSoldAt() == null ? null : marketDataDTO.getLastSoldAt()[0];

        if (lastSoldAtDTO == null) {
            builder
                    .lastSoldPrice(0)
                    .lastSoldAt(new Date(0));
        } else {
            try {
                builder
                        .lastSoldPrice(lastSoldAtDTO.getPrice())
                        .lastSoldAt(performedAtDateFormat.parse(lastSoldAtDTO.getPerformedAt()));
            } catch (ParseException e) {
                log.error("Error parsing date: " + lastSoldAtDTO.getPerformedAt());
            }
        }
    }

    private void buildExpectedProfitAndPercentageFromBuilderValues(ItemEntity.ItemEntityBuilder builder) {
        ItemEntity entity = builder.build();

        int sellPrice = entity.getSellOrders() == 0 ? entity.getLastSoldPrice() : entity.getMinSellPrice();
        int buyPrice = getNextFancyBuyPrice(entity.getMaxBuyPrice(), sellPrice);

        int priceDifference = (int) (sellPrice * marketplaceProfitPercent) - buyPrice;
        int expectedProfit = priceDifference < 0 ? 0 : priceDifference;

        int expectedProfitPercentage = (int) ((expectedProfit * 100.0) / buyPrice);

        builder.expectedProfit(expectedProfit)
                .expectedProfitPercentage(expectedProfitPercentage);
    }

    private int getNextFancyBuyPrice(int buyPrice, int sellPrice) {
        if (buyPrice == 0) {
            return 120;
        } else if (sellPrice < 200) {
            return ((buyPrice + 10) / 10) * 10;
        } else if (sellPrice < 1000) {
            return ((buyPrice + 50) / 50) * 50;
        } else if (sellPrice < 3000) {
            return ((buyPrice + 100) / 100) * 100;
        } else if (sellPrice < 10000) {
            return ((buyPrice + 500) / 500) * 500;
        } else if (sellPrice < 50000) {
            return ((buyPrice + 1000) / 1000) * 1000;
        } else {
            return ((buyPrice + 5000) / 5000) * 5000;
        }
    }
}
