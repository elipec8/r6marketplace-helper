package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TagEntity;
import github.ricemonger.marketplace.databases.postgres.enums.ItemType;
import github.ricemonger.marketplace.databases.postgres.services.ItemDtoMapper;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.SellStats;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ItemDtoMapperTests {

    private static final Item ITEM = new Item("id", "url", "name", List.of("tag1", "tag2"), "WeaponSkin");

    private static final SellStats SELL_STATS = new SellStats("id", 150, 300, 3);

    private static final BuyStats BUY_STATS = new BuyStats("id", 0, 0, 0);

    private static final LastSoldAt LAST_SOLD_AT = new LastSoldAt("id", 150, "2024-04-24T06:00:13.157Z");

    private static final MarketData MARKETDATA = new MarketData(new SellStats[]{SELL_STATS}, new BuyStats[]{BUY_STATS}, new LastSoldAt[]{LAST_SOLD_AT});

    private static final Node NODE = new Node(ITEM, MARKETDATA);

    @SpyBean
    private UbiServiceConfiguration ubisoftServiceConfiguration;

    @SpyBean
    private ItemDtoMapper itemDtoMapper;

    @Test
    public void NodeDTOsToItemEntitiesShouldCallNodeDTOToItemEntityForEveryEntity() {
        itemDtoMapper.nodesDTOToItemEntities(List.of(NODE, NODE));

        verify(itemDtoMapper, times(2)).nodeDTOToItemEntity(NODE);
    }

    @Test
    public void NodeDTOToItemEntityShouldMapValues() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(ubisoftServiceConfiguration.getPerformedAtDateFormat());

        ItemEntity expected = ItemEntity.builder()
                .itemFullId(ITEM.getId())
                .assetUrl(ITEM.getAssetUrl())
                .name(ITEM.getName())
                .tags(List.of(new TagEntity("tag1"), new TagEntity("tag2")))
                .type(ItemType.valueOf(ITEM.getType()))
                .maxBuyPrice(BUY_STATS.getHighestPrice())
                .buyOrdersCount(BUY_STATS.getActiveCount())
                .minSellPrice(SELL_STATS.getLowestPrice())
                .sellOrdersCount(SELL_STATS.getActiveCount())
                .lastSoldPrice(LAST_SOLD_AT.getPrice())
                .lastSoldAt(sdf.parse(LAST_SOLD_AT.getPerformedAt()))
                .expectedProfit(15)
                .expectedProfitPercentage(12)
                .build();

        ItemEntity mapped = itemDtoMapper.nodeDTOToItemEntity(NODE);

        assertEquals(expected.getItemFullId(), mapped.getItemFullId());
        assertEquals(expected.getAssetUrl(), mapped.getAssetUrl());
        assertEquals(expected.getName(), mapped.getName());
        assertEquals(expected.getTags(), mapped.getTags());
        assertEquals(expected.getType(), mapped.getType());
        assertEquals(expected.getMaxBuyPrice(), mapped.getMaxBuyPrice());
        assertEquals(expected.getBuyOrdersCount(), mapped.getBuyOrdersCount());
        assertEquals(expected.getMinSellPrice(), mapped.getMinSellPrice());
        assertEquals(expected.getSellOrdersCount(), mapped.getSellOrdersCount());
        assertEquals(expected.getLastSoldPrice(), mapped.getLastSoldPrice());
        assertEquals(expected.getLastSoldAt(), mapped.getLastSoldAt());
        assertEquals(expected.getExpectedProfit(), mapped.getExpectedProfit());
        assertEquals(expected.getExpectedProfitPercentage(), mapped.getExpectedProfitPercentage());
    }

    @Test
    public void NodeDTOsToItemSaleEntitiesShouldCallNodeDTOToItemSaleEntityForEveryEntity() {
        itemDtoMapper.nodesDTOToItemSaleEntities(List.of(NODE, NODE));

        verify(itemDtoMapper, times(2)).nodeDTOToItemSaleEntity(NODE);
    }

    @Test
    public void NodeDTOToItemSaleNodeShouldMapValues() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(ubisoftServiceConfiguration.getPerformedAtDateFormat());

        ItemSaleEntity expected = new ItemSaleEntity(ITEM.getId(), sdf.parse(LAST_SOLD_AT.getPerformedAt()), LAST_SOLD_AT.getPrice());

        ItemSaleEntity mapped = itemDtoMapper.nodeDTOToItemSaleEntity(NODE);

        assertEquals(expected.getItemId(), mapped.getItemId());
        assertEquals(expected.getSoldAt(), mapped.getSoldAt());
        assertEquals(expected.getPrice(), mapped.getPrice());
    }
}
