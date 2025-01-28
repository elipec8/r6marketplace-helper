package github.ricemonger.marketplace.graphQl.personal_query_user_stats;

import github.ricemonger.marketplace.graphQl.GraphQlCommonValuesService;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.Meta;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.MarketableItems;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.SecondaryStoreItem;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.Trades;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.TradesLimitations;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.tradeLimitations.Sell;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.tradeLimitations.sell.ResaleLocks;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.Nodes;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.Payment;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.TradeItems;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalUserStatsMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.ClientResponseField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonalQueryUserStatsMapperTest {

    private final GraphQlCommonValuesService commonValuesService = mock(GraphQlCommonValuesService.class);

    private final PersonalQueryUserStatsMapper personalQueryUserStatsMapper = spy(new PersonalQueryUserStatsMapper(commonValuesService));

    @Test
    public void mapUserStats_should_throw_if_meta_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = null;

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_secondaryStoreItem_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setSecondaryStoreItem(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_secondaryStoreItem_Meta_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.getSecondaryStoreItem().setMeta(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_currentTrades_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setCurrentTrades(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_finishedTrades_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setFinishedTrades(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_marketableItems_is_null_and_aliases_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setMarketableItems(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, null, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_marketableItems_is_null_and_aliases_is_empty() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of();

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setMarketableItems(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_marketableItems_nodes_is_null_and_aliases_is_null() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.getMarketableItems().setNodes(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, null, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_marketableItems_nodes_is_null_and_aliases_is_empty() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of();

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.getMarketableItems().setNodes(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId));
    }

    @Test
    public void mapUserStats_should_throw_if_tradesLimitations_is_null() {
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setTradesLimitations(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapUserStats(response, null, profileId));
    }

    @Test
    public void mapUserStats_should_return_expected_result_if_marketableItems_used_without_aliases() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();

        when(responseField.toEntity(Meta.class)).thenReturn(meta);

        UbiUserStats expected = new UbiUserStats();
        expected.setCreditAmount(50);
        expected.setCurrentOrders(List.of(
                new UbiTrade("tradeId1", TradeState.Created, TradeCategory.Buy, LocalDateTime.of(1970, 1, 1, 0, 0),
                        LocalDateTime.of(1970, 1, 1, 0, 0), new github.ricemonger.utils.DTOs.common.Item("1"), 1000, 100, 199, 20),
                new UbiTrade("tradeId2", TradeState.Created, TradeCategory.Buy, LocalDateTime.of(1970, 1, 1, 0, 0),
                        LocalDateTime.of(1970, 1, 1, 0, 0), new github.ricemonger.utils.DTOs.common.Item("2"), 1000, 100, 299, 30)));


        expected.setFinishedOrders(List.of(
                new UbiTrade("tradeId3", TradeState.Created, TradeCategory.Buy, LocalDateTime.of(1970, 1, 1, 0, 0),
                        LocalDateTime.of(1970, 1, 1, 0, 0), new github.ricemonger.utils.DTOs.common.Item("3"), 1000, 100, 499, 50)));

        expected.setOwnedItemsIds(List.of("2", "3", "4"));

        expected.setUserTradesLimitations(new UserTradesLimitations(profileId, 1, 2, 3, 4, List.of(new ItemResaleLock("1",
                LocalDateTime.of(-999999999, 1, 1, 0, 0)), new ItemResaleLock("2", LocalDateTime.of(-999999999, 1, 1, 0, 0)))));


        UbiUserStats result = personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId);

        assertEquals(expected.getCreditAmount(), result.getCreditAmount());
        assertEquals(expected.getCurrentOrders().size(), result.getCurrentOrders().size());
        assertTrue(expected.getCurrentOrders().containsAll(result.getCurrentOrders()));
        assertEquals(expected.getFinishedOrders().size(), result.getFinishedOrders().size());
        assertTrue(expected.getFinishedOrders().containsAll(result.getFinishedOrders()));
        assertEquals(expected.getOwnedItemsIds().size(), (result.getOwnedItemsIds().size()));
        assertTrue(expected.getOwnedItemsIds().containsAll(result.getOwnedItemsIds()));
        assertEquals(expected.getUserTradesLimitations().getUbiProfileId(), result.getUserTradesLimitations().getUbiProfileId());
        assertEquals(expected.getUserTradesLimitations().getActiveBuyTransactionCount(), result.getUserTradesLimitations().getActiveBuyTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getActiveSellTransactionCount(), result.getUserTradesLimitations().getActiveSellTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getResolvedBuyTransactionCount(), result.getUserTradesLimitations().getResolvedBuyTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getResolvedSellTransactionCount(), result.getUserTradesLimitations().getResolvedSellTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getResaleLocks().size(), result.getUserTradesLimitations().getResaleLocks().size());
        assertTrue(expected.getUserTradesLimitations().getResaleLocks().containsAll(result.getUserTradesLimitations().getResaleLocks()));
    }

    @Test
    public void mapUserStats_should_return_expected_result_if_marketableItems_used_with_aliases() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);
        ClientGraphQlResponse response = mock(ClientGraphQlResponse.class);

        ClientResponseField responseField = mock(ClientResponseField.class);

        when(response.field(any())).thenReturn(responseField);

        Map<String, String> aliasesToFields = Map.of(
                "marketableItems0", "marketableItems",
                "marketableItems1", "marketableItems",
                "marketableItems2", "marketableItems"
        );

        String profileId = "profileId";

        Meta meta = createMeta();
        meta.setMarketableItems(null);

        when(responseField.toEntity(Meta.class)).thenReturn(meta);
        when(response.field("game.viewer.meta.marketableItems0.nodes").toEntityList(Node.class)).thenReturn(List.of(new Node(new Item("5")), new Node(new Item("7"))));
        when(response.field("game.viewer.meta.marketableItems1.nodes").toEntityList(Node.class)).thenReturn(List.of());
        when(response.field("game.viewer.meta.marketableItems2.nodes").toEntityList(Node.class)).thenReturn(List.of(new Node(new Item("7"))));


        UbiUserStats expected = new UbiUserStats();
        expected.setCreditAmount(50);
        expected.setCurrentOrders(List.of(
                new UbiTrade("tradeId1", TradeState.Created, TradeCategory.Buy, LocalDateTime.of(1970, 1, 1, 0, 0),
                        LocalDateTime.of(1970, 1, 1, 0, 0), new github.ricemonger.utils.DTOs.common.Item("1"), 1000, 100, 199, 20),
                new UbiTrade("tradeId2", TradeState.Created, TradeCategory.Buy, LocalDateTime.of(1970, 1, 1, 0, 0),
                        LocalDateTime.of(1970, 1, 1, 0, 0), new github.ricemonger.utils.DTOs.common.Item("2"), 1000, 100, 299, 30)));


        expected.setFinishedOrders(List.of(
                new UbiTrade("tradeId3", TradeState.Created, TradeCategory.Buy, LocalDateTime.of(1970, 1, 1, 0, 0),
                        LocalDateTime.of(1970, 1, 1, 0, 0), new github.ricemonger.utils.DTOs.common.Item("3"), 1000, 100, 499, 50)));

        expected.setOwnedItemsIds(List.of("5", "6", "7"));

        expected.setUserTradesLimitations(new UserTradesLimitations(profileId, 1, 2, 3, 4, List.of(new ItemResaleLock("1",
                LocalDateTime.of(-999999999, 1, 1, 0, 0)), new ItemResaleLock("2", LocalDateTime.of(-999999999, 1, 1, 0, 0)))));

        UbiUserStats result = personalQueryUserStatsMapper.mapUserStats(response, aliasesToFields, profileId);

        assertEquals(expected.getCreditAmount(), result.getCreditAmount());
        assertEquals(expected.getCurrentOrders().size(), result.getCurrentOrders().size());
        assertTrue(expected.getCurrentOrders().containsAll(result.getCurrentOrders()));
        assertEquals(expected.getFinishedOrders().size(), result.getFinishedOrders().size());
        assertTrue(expected.getFinishedOrders().containsAll(result.getFinishedOrders()));
        assertEquals(expected.getOwnedItemsIds().size(), (result.getOwnedItemsIds().size()));
        assertTrue(expected.getOwnedItemsIds().containsAll(result.getOwnedItemsIds()));
        assertEquals(expected.getUserTradesLimitations().getUbiProfileId(), result.getUserTradesLimitations().getUbiProfileId());
        assertEquals(expected.getUserTradesLimitations().getActiveBuyTransactionCount(), result.getUserTradesLimitations().getActiveBuyTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getActiveSellTransactionCount(), result.getUserTradesLimitations().getActiveSellTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getResolvedBuyTransactionCount(), result.getUserTradesLimitations().getResolvedBuyTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getResolvedSellTransactionCount(), result.getUserTradesLimitations().getResolvedSellTransactionCount());
        assertEquals(expected.getUserTradesLimitations().getResaleLocks().size(), result.getUserTradesLimitations().getResaleLocks().size());
        assertTrue(expected.getUserTradesLimitations().getResaleLocks().containsAll(result.getUserTradesLimitations().getResaleLocks()));
    }

    private Meta createMeta() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd");
        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        Meta meta = new Meta();

        TradesLimitations tradesLimitations = new TradesLimitations();
        Buy buy = new Buy();
        buy.setResolvedTransactionCount(1);
        buy.setActiveTransactionCount(2);
        Sell sell = new Sell();
        sell.setResolvedTransactionCount(3);
        sell.setActiveTransactionCount(4);
        List<ResaleLocks> resaleLocks = new ArrayList<>();
        resaleLocks.add(new ResaleLocks("1", "2021-01-01"));
        resaleLocks.add(new ResaleLocks("2", "2022-01-01"));
        sell.setResaleLocks(resaleLocks);
        tradesLimitations.setBuy(buy);
        tradesLimitations.setSell(sell);

        MarketableItems marketableItems = new MarketableItems();
        marketableItems.setTotalCount(3);
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(new Item("2")));
        nodes.add(new Node(new Item("3")));
        nodes.add(new Node(new Item("4")));
        marketableItems.setNodes(nodes);

        SecondaryStoreItem secondaryStoreItem = new SecondaryStoreItem();
        secondaryStoreItem.setMeta(new github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.secondaryStoreItem.Meta(50));

        Trades currentTrades = new Trades();
        List<Nodes> currentTradesNodes = new ArrayList<>();
        currentTradesNodes.add(new Nodes("tradeId1", "Created", "Buy", "2021-01-01", "2021-01-01",
                new TradeItems[]{new TradeItems(new github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.tradeItems.Item("1", "itemName1"))}, new Payment(1000, 100), new PaymentOptions[]{new PaymentOptions(199)}, null));
        currentTradesNodes.add(new Nodes("tradeId2", "Created", "Buy", "2021-01-01", "2021-01-01",
                new TradeItems[]{new TradeItems(new github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.tradeItems.Item("2", "itemName2"))}, new Payment(1000, 100), null, new PaymentProposal(299)));

        currentTrades.setNodes(currentTradesNodes);

        List<Nodes> finishedTradesNodes = new ArrayList<>();
        finishedTradesNodes.add(new Nodes("tradeId3", "Created", "Buy", "2021-01-01", "2021-01-01",
                new TradeItems[]{new TradeItems(new github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.tradeItems.Item("3", "itemName3"))}, new Payment(1000, 100), new PaymentOptions[]{}, new PaymentProposal(499)));
        Trades finishedTrades = new Trades(finishedTradesNodes);

        meta.setTradesLimitations(tradesLimitations);
        meta.setMarketableItems(marketableItems);
        meta.setSecondaryStoreItem(secondaryStoreItem);
        meta.setCurrentTrades(currentTrades);
        meta.setFinishedTrades(finishedTrades);

        return meta;
    }

    @Test
    public void mapCreditAmount_should_map_if_valid_fields() {
        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.secondaryStoreItem.Meta meta = new github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.secondaryStoreItem.Meta(50);

        assertEquals(50, personalQueryUserStatsMapper.mapCreditAmount(meta));
    }

    @Test
    public void mapCreditAmount_should_throw_exception_if_meta_is_null() {
        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.secondaryStoreItem.Meta meta = null;

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapCreditAmount(meta));
    }

    @Test
    public void mapCreditAmount_should_throw_exception_if_quantity_is_null() {
        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.secondaryStoreItem.Meta meta = new github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.secondaryStoreItem.Meta(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapCreditAmount(meta));
    }

    @Test
    public void mapOrders_should_map_each_order() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node1 = createNode(dtf, date, date2);
        node1.setPaymentProposal(null);
        node1.setTradeId("tradeId1");

        Nodes node2 = createNode(dtf, date, date2);
        node2.setPaymentProposal(null);
        node2.setTradeId("tradeId2");

        Nodes node3 = createNode(dtf, date, date2);
        node3.setPaymentProposal(null);
        node3.setTradeId("tradeId3");

        Trades trades = new Trades(List.of(node1, node2, node3));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        assertEquals(3, personalQueryUserStatsMapper.mapOrders(trades).size());

        verify(personalQueryUserStatsMapper, times(3)).mapOrder(any());
    }

    @Test
    public void mapOrders_should_throw_if_null_trades() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrders(null);
        });
    }

    @Test
    public void mapOrders_should_map_order_if_valid_fields_and_null_paymentProposal() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        github.ricemonger.utils.DTOs.common.Item expectedItem = new github.ricemonger.utils.DTOs.common.Item("1");
        expectedItem.setName("name");
        expected.setItem(expectedItem);

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryUserStatsMapper.mapOrder(node));
    }

    @Test
    public void mapOrders_should_map_order_if_valid_fields_and_null_paymentOptions() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        github.ricemonger.utils.DTOs.common.Item expectedItem = new github.ricemonger.utils.DTOs.common.Item("1");
        expectedItem.setName("name");
        expected.setItem(expectedItem);

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryUserStatsMapper.mapOrder(node));
    }

    @Test
    public void mapOrders_should_map_order_if_null_payment() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPayment(null);

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(0);
        expected.setSuccessPaymentFee(0);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryUserStatsMapper.mapOrder(node));
    }

    @Test
    public void mapOrders_should_map_order_if_invalid_tradeState() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setState("invalid");

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Unknown);
        expected.setCategory(TradeCategory.Buy);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryUserStatsMapper.mapOrder(node));
    }

    @Test
    public void mapOrders_should_map_order_if_invalid_tradeCategory() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory("invalid");

        UbiTrade expected = new UbiTrade();
        expected.setTradeId("tradeId");
        expected.setState(TradeState.Created);
        expected.setCategory(TradeCategory.Unknown);
        expected.setExpiresAt(date);
        expected.setLastModifiedAt(date2);

        expected.setItem(new github.ricemonger.utils.DTOs.common.Item("1"));

        ConfigTrades configTrades = new ConfigTrades();
        configTrades.setFeePercentage(10);
        when(commonValuesService.getConfigTrades()).thenReturn(configTrades);

        expected.setSuccessPaymentPrice(1000);
        expected.setSuccessPaymentFee(100);

        expected.setProposedPaymentPrice(100);
        expected.setProposedPaymentFee(10);

        assertEquals(expected, personalQueryUserStatsMapper.mapOrder(node));
    }

    @Test
    public void mapOrders_should_throw_if_valid_fields_and_both_paymentOptions_and_paymentProposal() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_node() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrders(null);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_tradeId() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeId(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_state() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setState(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_category() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setCategory(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_lastModifiedAt() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setLastModifiedAt(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_tradeItems() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_empty_tradeItems() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setTradeItems(new TradeItems[0]);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_item() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].setItem(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_itemId() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getTradeItems()[0].getItem().setItemId(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_payment_price() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPayment().setPrice(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_payment_transactionFee() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPayment().setTransactionFee(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_paymentProposal_and_paymentOptions() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_empty_paymentOptions() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.setPaymentOptions(new PaymentOptions[0]);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_paymentOptions_price() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentProposal(null);
        node.getPaymentOptions()[0].setPrice(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    @Test
    public void mapOrders_should_throw_if_null_paymentProposal_price() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime date2 = LocalDateTime.now().withNano(0);

        Nodes node = createNode(dtf, date, date2);
        node.setPaymentOptions(null);
        node.getPaymentProposal().setPrice(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapOrder(node);
        });
    }

    private Nodes createNode(DateTimeFormatter dtf, LocalDateTime date1, LocalDateTime date2) {
        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.tradeItems.Item item = new
                github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.trades.nodes.tradeItems.Item();
        item.setItemId("1");
        TradeItems tradeItems = new TradeItems();
        tradeItems.setItem(item);

        Payment payment = new Payment(1000, 100);

        PaymentOptions paymentOption = new PaymentOptions(100);

        PaymentProposal paymentProposal = new PaymentProposal(100);

        return new Nodes("tradeId",
                TradeState.Created.name(),
                TradeCategory.Buy.name(),
                dtf.format(date1),
                dtf.format(date2),
                new TradeItems[]{tradeItems},
                payment,
                new PaymentOptions[]{paymentOption},
                paymentProposal);
    }

    @Test
    public void mapOwnedItems_should_map_each_item() {
        List<Node> nodes = new ArrayList<>();

        Node node1 = new Node();

        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item item1 = new
                github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item();
        item1.setItemId("item1");
        node1.setItem(item1);
        nodes.add(node1);

        Node node2 = new Node();

        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item item2 = new
                github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item();
        item2.setItemId("item2");
        node2.setItem(item2);
        nodes.add(node2);

        List<String> result = personalQueryUserStatsMapper.mapOwnedItems(nodes);

        assertEquals(2, result.size());
        assertTrue(result.contains("item1") && result.contains("item2"));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_nodes_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapOwnedItems(null));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_node_is_null() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapOwnedItems(nodes));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_item_is_null() {
        List<Node> nodes = new ArrayList<>();

        Node node = new Node();
        node.setItem(null);
        nodes.add(node);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapOwnedItems(nodes));
    }

    @Test
    public void mapOwnedItems_should_throw_exception_when_itemId_is_null() {
        List<Node> nodes = new ArrayList<>();

        Node node = new Node();

        github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item item = new
                github.ricemonger.marketplace.graphQl.personal_query_user_stats.DTO.meta.marketableItems.node.Item();
        item.setItemId(null);
        node.setItem(item);
        nodes.add(node);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> personalQueryUserStatsMapper.mapOwnedItems(nodes));
    }

    @Test
    public void mapLockedItems_should_map_each_item() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);
        LocalDateTime date2 = date.plusSeconds(1000);

        TradesLimitations tradesLimitations = new TradesLimitations();
        tradesLimitations.setBuy(new Buy());
        tradesLimitations.getBuy().setActiveTransactionCount(1);
        tradesLimitations.getBuy().setResolvedTransactionCount(2);
        tradesLimitations.setSell(new Sell());
        tradesLimitations.getSell().setActiveTransactionCount(3);
        tradesLimitations.getSell().setResolvedTransactionCount(4);
        tradesLimitations.getSell().setResaleLocks(new ArrayList<>());
        tradesLimitations.getSell().getResaleLocks().add(new ResaleLocks("1", dtf.format(date)));
        tradesLimitations.getSell().getResaleLocks().add(new ResaleLocks("2", dtf.format(date2)));

        ItemResaleLock expected1 = new ItemResaleLock("1", date);
        ItemResaleLock expected2 = new ItemResaleLock("2", date2);

        UserTradesLimitations userTradesLimitations = personalQueryUserStatsMapper.mapTradesLimitationsForUser(tradesLimitations, "ubiProfileId");

        assertEquals("ubiProfileId", userTradesLimitations.getUbiProfileId());
        assertEquals(1, userTradesLimitations.getActiveBuyTransactionCount());
        assertEquals(2, userTradesLimitations.getResolvedBuyTransactionCount());
        assertEquals(3, userTradesLimitations.getActiveSellTransactionCount());
        assertEquals(4, userTradesLimitations.getResolvedSellTransactionCount());
        assertTrue(userTradesLimitations.getResaleLocks().contains(expected1) && userTradesLimitations.getResaleLocks().contains(expected2));
        verify(personalQueryUserStatsMapper, times(2)).mapLockedItem(any());
    }

    @Test
    public void mapLockedItems_should_throw_exception_when_tradeLimitations_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapTradesLimitationsForUser(null, "ubiProfileId");
        });
    }

    @Test
    public void mapLockedItems_should_throw_exception_when_sell_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapTradesLimitationsForUser(new TradesLimitations(), "ubiProfileId");
        });
    }

    @Test
    public void mapLockedItems_should_throw_exception_when_resaleLocks_is_null() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        tradesLimitations.setSell(new Sell());

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapTradesLimitationsForUser(tradesLimitations, "ubiProfileId");
        });
    }

    @Test
    public void mapLockedItem_should_map_item_with_valid_fields() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        ResaleLocks resaleLocks = new ResaleLocks("1", dtf.format(date));

        ItemResaleLock expected = new ItemResaleLock("1", date);

        assertEquals(expected, personalQueryUserStatsMapper.mapLockedItem(resaleLocks));
    }

    @Test
    public void mapLockedItem_should_map_item_with_invalid_expiresAt() {
        when(commonValuesService.getDateFormat()).thenReturn("yyyy-MM-dd HH:mm:ss");

        ResaleLocks resaleLocks = new ResaleLocks("1", "invalid date");

        ItemResaleLock expected = new ItemResaleLock("1", LocalDateTime.MIN);

        assertEquals(expected, personalQueryUserStatsMapper.mapLockedItem(resaleLocks));
    }

    @Test
    public void mapLockedItem_should_throw_exception_when_resaleLocks_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapLockedItem(null);
        });
    }

    @Test
    public void mapLockedItem_should_throw_exception_when_itemId_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapLockedItem(new ResaleLocks(null, "2021-01-01"));
        });
    }

    @Test
    public void mapLockedItem_should_throw_exception_when_expiresAt_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapLockedItem(new ResaleLocks("1", null));
        });
    }

    @Test
    public void mapUserTransactionsCount_should_map_user_transactions_count() {
        TradesLimitations tradesLimitations = createTradeLimitations();

        UserTransactionsCount expected = new UserTransactionsCount(1, 2, 3, 4);

        assertEquals(expected, personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations));
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_tradeLimitations_is_null() {
        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(null);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_buy_is_null() {
        TradesLimitations tradesLimitations = createTradeLimitations();
        tradesLimitations.setBuy(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_sell_is_null() {
        TradesLimitations tradesLimitations = createTradeLimitations();
        tradesLimitations.setSell(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_buy_activeTransactionCount_is_null() {
        TradesLimitations tradesLimitations = createTradeLimitations();
        tradesLimitations.getBuy().setActiveTransactionCount(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_buy_resolvedTransactionCount_is_null() {
        TradesLimitations tradesLimitations = createTradeLimitations();
        tradesLimitations.getBuy().setResolvedTransactionCount(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_sell_activeTransactionCount_is_null() {
        TradesLimitations tradesLimitations = createTradeLimitations();
        tradesLimitations.getSell().setActiveTransactionCount(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_sell_resolvedTransactionCount_is_null() {
        TradesLimitations tradesLimitations = createTradeLimitations();
        tradesLimitations.getSell().setResolvedTransactionCount(null);

        assertThrows(GraphQlPersonalUserStatsMappingException.class, () -> {
            personalQueryUserStatsMapper.mapUserTransactionsCount(tradesLimitations);
        });
    }

    private TradesLimitations createTradeLimitations() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        tradesLimitations.setBuy(new Buy());
        tradesLimitations.setSell(new Sell());
        tradesLimitations.getBuy().setResolvedTransactionCount(1);
        tradesLimitations.getBuy().setActiveTransactionCount(2);
        tradesLimitations.getSell().setResaleLocks(new ArrayList<>());
        tradesLimitations.getSell().setResolvedTransactionCount(3);
        tradesLimitations.getSell().setActiveTransactionCount(4);
        return tradesLimitations;
    }
}