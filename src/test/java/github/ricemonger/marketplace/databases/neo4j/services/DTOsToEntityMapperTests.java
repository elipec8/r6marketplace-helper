package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.BuyStatsEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.ItemEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.LastSoldAtEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.SellStatsEntity;
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


import java.text.SimpleDateFormat;
import java.util.List;

import static github.ricemonger.marketplace.databases.neo4j.services.DTOsToEntityMapper.PERFORMED_AT_DATE_FORMAT;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DTOsToEntityMapperTests {

    private static final Item ITEM = new Item("id","url","name", List.of("tag1","tag2"),"WeaponSkin");

    private static final SellStats SELL_STATS = new SellStats("id",1,2,3);

    private static final BuyStats BUY_STATS = new BuyStats("id",1,2,3);

    private static final LastSoldAt LAST_SOLD_AT = new LastSoldAt("id",1,"2024-04-24T06:00:13.157Z");

    private static final MarketData MARKETDATA = new MarketData(new SellStats[]{SELL_STATS},new BuyStats[]{BUY_STATS},new LastSoldAt[]{LAST_SOLD_AT});

    private static final SimpleDateFormat sdf = new SimpleDateFormat(PERFORMED_AT_DATE_FORMAT);

    private static final Node NODE = new Node(ITEM, MARKETDATA);

    @SpyBean
    private DTOsToEntityMapper dtosToEntityMapper;


    @Test
    public void NodeDTOsToItemEntitiesShouldCallNodeDTOToItemEntity() {
        dtosToEntityMapper.NodesDTOToItemEntities(List.of(NODE));

        verify(dtosToEntityMapper).nodeDTOToItemEntity(NODE);
    }

    @Test
    public void nodeDTOToItemEntityShouldReturnRightItemEntity() {
        ItemEntity itemEntity = dtosToEntityMapper.nodeDTOToItemEntity(NODE);

        assert(itemEntity.getItemFullId().equals(ITEM.getId()));
        assert(itemEntity.getAssertUrl().equals(ITEM.getAssertUrl()));
        assert(itemEntity.getName().equals(ITEM.getName()));
        assert(itemEntity.getTags().equals(ITEM.getTags()));
        assert(itemEntity.getType().equals(ItemType.valueOf(ITEM.getType())));
        assert(itemEntity.getBuyStats().getBuyStatsId().equals(BUY_STATS.getId()));
        assert(itemEntity.getBuyStats().getLowestPrice() == BUY_STATS.getLowestPrice());
        assert(itemEntity.getBuyStats().getHighestPrice() == BUY_STATS.getHighestPrice());
        assert(itemEntity.getBuyStats().getActiveCount() == BUY_STATS.getActiveCount());
        assert(itemEntity.getSellStats().getSellStatsId().equals(SELL_STATS.getId()));
        assert(itemEntity.getSellStats().getLowestPrice() == SELL_STATS.getLowestPrice());
        assert(itemEntity.getSellStats().getHighestPrice() == SELL_STATS.getHighestPrice());
        assert(itemEntity.getSellStats().getActiveCount() == SELL_STATS.getActiveCount());
        assert(itemEntity.getLastSoldAt().getLastSoldAtId().equals(LAST_SOLD_AT.getId()));
        assert(itemEntity.getLastSoldAt().getPrice() == LAST_SOLD_AT.getPrice());
        assert(sdf.format(itemEntity.getLastSoldAt().getPerformedAt()).equals(LAST_SOLD_AT.getPerformedAt()));
    }

    @Test
    public void marketDataDTOToBuyStatsShouldReturnRightBuyStatsEntity() {
        BuyStatsEntity buyStatsEntity = dtosToEntityMapper.marketDataDTOToBuyStatsEntityOrNull(MARKETDATA);

        assert(buyStatsEntity.getBuyStatsId().equals(BUY_STATS.getId()));
        assert(buyStatsEntity.getLowestPrice() == BUY_STATS.getLowestPrice());
        assert(buyStatsEntity.getHighestPrice() == BUY_STATS.getHighestPrice());
        assert(buyStatsEntity.getActiveCount() == BUY_STATS.getActiveCount());
    }

    @Test
    public void marketDataDTOToBuyStatsShouldReturnNullOnEmptyData() {
        BuyStatsEntity buyStatsEntity = dtosToEntityMapper.marketDataDTOToBuyStatsEntityOrNull(new MarketData());

        assert(buyStatsEntity == null);
    }

    @Test
    public void marketDataDTOToSellStatsShouldReturnRightSellStatsEntity() {
        SellStatsEntity sellStatsEntity = dtosToEntityMapper.marketDataDTOToSellStatsEntityOrNull(MARKETDATA);

        assert(sellStatsEntity.getSellStatsId().equals(SELL_STATS.getId()));
        assert(sellStatsEntity.getLowestPrice() == SELL_STATS.getLowestPrice());
        assert(sellStatsEntity.getHighestPrice() == SELL_STATS.getHighestPrice());
        assert(sellStatsEntity.getActiveCount() == SELL_STATS.getActiveCount());
    }

    @Test
    public void marketDataDTOToSellStatsShouldReturnNullOnEmptyData() {
        SellStatsEntity sellStatsEntity = dtosToEntityMapper.marketDataDTOToSellStatsEntityOrNull(new MarketData());

        assert(sellStatsEntity == null);
    }

    @Test
    public void marketDataDTOToLastSoldAtShouldReturnRightLastSoldAtEntity() {
        LastSoldAtEntity lastSoldAtEntity = dtosToEntityMapper.marketDataDTOToLastSoldAtEntityOrNull(MARKETDATA);

        assert(lastSoldAtEntity.getLastSoldAtId().equals(LAST_SOLD_AT.getId()));
        assert(lastSoldAtEntity.getPrice() == LAST_SOLD_AT.getPrice());
        assert(sdf.format(lastSoldAtEntity.getPerformedAt()).equals(LAST_SOLD_AT.getPerformedAt()));;
    }

    @Test
    public void marketDataDTOToLastSoldAtShouldReturnNullOnEmptyData() {
        LastSoldAtEntity lastSoldAtEntity = dtosToEntityMapper.marketDataDTOToLastSoldAtEntityOrNull(new MarketData());

        assert(lastSoldAtEntity == null);
    }
}
