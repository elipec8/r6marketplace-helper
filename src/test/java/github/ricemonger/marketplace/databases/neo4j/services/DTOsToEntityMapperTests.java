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
public class DTOsToEntityMapperTests {

    private static final Item ITEM = new Item("id", "url", "name", List.of("tag1", "tag2"), "WeaponSkin");

    private static final SellStats SELL_STATS = new SellStats("id", 150, 300, 3);

    private static final BuyStats BUY_STATS = new BuyStats("id", 0, 0, 0);

    private static final LastSoldAt LAST_SOLD_AT = new LastSoldAt("id", 150, "2024-04-24T06:00:13.157Z");

    private static final MarketData MARKETDATA = new MarketData(new SellStats[]{SELL_STATS}, new BuyStats[]{BUY_STATS}, new LastSoldAt[]{LAST_SOLD_AT});

    private static final Node NODE = new Node(ITEM, MARKETDATA);

    @SpyBean
    private UbiServiceConfiguration ubisoftServiceConfiguration;

    @SpyBean
    private DTOsToEntityMapper dtosToEntityMapper;

    @Test
    public void NodeDTOsToItemEntitiesShouldCallNodeDTOToItemEntityForEveryEntity() {
        dtosToEntityMapper.nodesDTOToItemEntities(List.of(NODE, NODE));

        verify(dtosToEntityMapper, times(2)).nodeDTOToItemEntity(NODE);
    }

    @Test
    public void NodeDTOToItemEntityShouldMapValues() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(ubisoftServiceConfiguration.getPerformedAtDateFormat());
        dtosToEntityMapper.nodeDTOToItemEntity(NODE);

        ItemEntity itemEntity = ItemEntity.builder()
                .itemFullId(ITEM.getId())
                .assetUrl(ITEM.getAssetUrl())
                .name(ITEM.getName())
                .tags(ITEM.getTags())
                .type(ItemType.valueOf(ITEM.getType()))
                .maxBuyPrice(BUY_STATS.getHighestPrice())
                .buyOrders(BUY_STATS.getActiveCount())
                .minSellPrice(SELL_STATS.getLowestPrice())
                .sellOrders(SELL_STATS.getActiveCount())
                .lastSoldPrice(LAST_SOLD_AT.getPrice())
                .lastSoldAt(sdf.parse(LAST_SOLD_AT.getPerformedAt()))
                .expectedProfit(15)
                .expectedProfitPercentage(12)
                .build();

        assertEquals(itemEntity, dtosToEntityMapper.nodeDTOToItemEntity(NODE));
    }
}
