package github.ricemonger.marketplace.graphQl.mappers;


import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.Game;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.MarketableItem;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.Viewer;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.MarketData;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.PaymentLimitations;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.marketData.SellStats;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.Meta;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.Trades;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.Nodes;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.nodes.Payment;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentOptions;
import github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.viewer.meta.trades.nodes.PaymentProposal;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.DTOs.items.PersonalItem;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utils.exceptions.server.GraphQlPersonalOneItemMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonalQueryOneItemMapperTest {
    @Autowired
    private PersonalQueryOneItemMapper personalQueryOneItemMapper;
    @Autowired
    private CommonValuesService commonValuesService;

    @Test
    public void mapItem_should_map_item_with_valid_fields_with_PaymentProposal() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_valid_fields_with_PaymentOptions() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentOptions(new PaymentOptions[]{new PaymentOptions(1000)});
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentProposal(null);

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_type() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getMarketableItem().getItem().setType("invalidType");

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);
        expected.setType(ItemType.Unknown);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_last_sold_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getMarketableItem().getMarketData().getLastSoldAt()[0].setPerformedAt("invalidDate");

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);
        expected.setLastSoldAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0));

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_expires_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getViewer().getMeta().getTrades().getNodes().get(0).setExpiresAt("invalidDate");

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);
        expected.getTrades().get(0).setExpiresAt(LocalDateTime.MIN);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_last_modified_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getViewer().getMeta().getTrades().getNodes().get(0).setLastModifiedAt("invalidDate");

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);
        expected.getTrades().get(0).setLastModifiedAt(LocalDateTime.MIN);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_TradeState() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getViewer().getMeta().getTrades().getNodes().get(0).setState("invalidState");

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);
        expected.getTrades().get(0).setState(TradeState.Unknown);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_map_item_with_invalid_TradeCategory() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);

        Game game = createGame(dtf, date);
        game.getViewer().getMeta().getTrades().getNodes().get(0).setCategory("invalidCategory");

        PersonalItem result = personalQueryOneItemMapper.mapItem(game);

        PersonalItem expected = createPersonalItem(date);
        expected.getTrades().get(0).setCategory(TradeCategory.Unknown);

        assertEquals(expected, result);
    }

    @Test
    public void mapItem_should_throw_exception_when_game_is_null() {
        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(null));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_is_null() {
        Game game = createGame();
        game.setMarketableItem(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_item_is_null() {
        Game game = createGame();
        game.getMarketableItem().setItem(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_item_itemId_is_null() {
        Game game = createGame();
        game.getMarketableItem().getItem().setItemId(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_item_assetUrl_is_null() {
        Game game = createGame();
        game.getMarketableItem().getItem().setAssetUrl(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_item_name_is_null() {
        Game game = createGame();
        game.getMarketableItem().getItem().setName(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_item_tags_is_null() {
        Game game = createGame();
        game.getMarketableItem().getItem().setTags(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_item_type_is_null() {
        Game game = createGame();
        game.getMarketableItem().getItem().setType(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_is_null() {
        Game game = createGame();
        game.getMarketableItem().setMarketData(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_buyStats_highestPrice_is_null() {
        Game game = createGame();
        game.getMarketableItem().getMarketData().getBuyStats()[0].setHighestPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_buyStats_activeCount_is_null() {
        Game game = createGame();
        game.getMarketableItem().getMarketData().getBuyStats()[0].setActiveCount(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_sellStats_lowestPrice_is_null() {
        Game game = createGame();
        game.getMarketableItem().getMarketData().getSellStats()[0].setLowestPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_sellStats_activeCount_is_null() {
        Game game = createGame();
        game.getMarketableItem().getMarketData().getSellStats()[0].setActiveCount(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_lastSoldAt_performedAt_is_null() {
        Game game = createGame();
        game.getMarketableItem().getMarketData().getLastSoldAt()[0].setPerformedAt(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_marketData_lastSoldAt_price_is_null() {
        Game game = createGame();
        game.getMarketableItem().getMarketData().getLastSoldAt()[0].setPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_paymentLimitations_is_null() {
        Game game = createGame();
        game.getMarketableItem().setPaymentLimitations(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_paymentLimitations_minPrice_is_null() {
        Game game = createGame();
        game.getMarketableItem().getPaymentLimitations().setMinPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_marketableItem_paymentLimitations_maxPrice_is_null() {
        Game game = createGame();
        game.getMarketableItem().getPaymentLimitations().setMaxPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_is_null() {
        Game game = createGame();
        game.setViewer(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_is_null() {
        Game game = createGame();
        game.getViewer().setMeta(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().setTrades(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().setNodes(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_not_throw_exception_when_game_viewer_meta_trades_nodes_is_empty() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().setNodes(new ArrayList<>());

        assertDoesNotThrow(() -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_tradeId_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setTradeId(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_state_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setState(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_category_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setCategory(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_expiresAt_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setExpiresAt(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_lastModifiedAt_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setLastModifiedAt(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_not_throw_exception_when_game_viewer_meta_trades_nodes_payment_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPayment(null);

        assertDoesNotThrow(() -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_payment_price_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).getPayment().setPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_payment_transaction_fee_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).getPayment().setTransactionFee(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }


    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_paymentOptions_and_paymentProposal_are_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentOptions(null);
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentProposal(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_both_game_viewer_meta_trades_nodes_paymentOptions_and_paymentProposal_arent_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentOptions(new PaymentOptions[]{new PaymentOptions(1000)});

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_paymentOptions_price_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentOptions(new PaymentOptions[]{new PaymentOptions(null)});
        game.getViewer().getMeta().getTrades().getNodes().get(0).setPaymentProposal(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_paymentProposal_price_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).getPaymentProposal().setPrice(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    @Test
    public void mapItem_should_throw_exception_when_game_viewer_meta_trades_nodes_paymentProposal_transaction_fee_is_null() {
        Game game = createGame();
        game.getViewer().getMeta().getTrades().getNodes().get(0).getPaymentProposal().setTransactionFee(null);

        assertThrows(GraphQlPersonalOneItemMappingException.class, () -> personalQueryOneItemMapper.mapItem(game));
    }

    private Game createGame() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(commonValuesService.getDateFormat());
        LocalDateTime date = LocalDateTime.now().withNano(0);
        return createGame(dtf, date);
    }

    private Game createGame(DateTimeFormatter dtf, LocalDateTime date) {
        github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.item.viewer.Meta itemMeta =
                new github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.item.viewer.Meta();
        itemMeta.setIsOwned(true);

        github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.item.Viewer itemViewer =
                new github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.item.Viewer(itemMeta);

        github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.Item item =
                new github.ricemonger.marketplace.graphQl.DTOs.personal_query_one_item.game.marketableItem.Item();
        item.setItemId("223");
        item.setAssetUrl("https://assetUrl.com");
        item.setName("item name");
        item.setTags(List.of("Y1S2", "Y7S3"));
        item.setType(ItemType.DroneSkin.name());
        item.setViewer(itemViewer);

        SellStats sellStats = new SellStats();
        sellStats.setActiveCount(99);
        sellStats.setLowestPrice(999);
        BuyStats buyStats = new BuyStats();
        buyStats.setActiveCount(88);
        buyStats.setHighestPrice(888);
        LastSoldAt lastSoldAt = new LastSoldAt();
        lastSoldAt.setPrice(777);
        lastSoldAt.setPerformedAt(dtf.format(date.minusSeconds(1000)));

        MarketableItem marketableItem = new MarketableItem();
        marketableItem.setItem(item);
        marketableItem.setMarketData(new MarketData(new SellStats[]{sellStats}, new BuyStats[]{buyStats}, new LastSoldAt[]{lastSoldAt}));
        marketableItem.setPaymentLimitations(new PaymentLimitations(10000, 100000));


        Nodes nodes1 = new Nodes();
        nodes1.setTradeId("123");
        nodes1.setState(TradeState.Created.name());
        nodes1.setCategory(TradeCategory.Sell.name());
        nodes1.setExpiresAt(dtf.format(date));
        nodes1.setLastModifiedAt(dtf.format(date.plusSeconds(1000)));
        nodes1.setPayment(new Payment(550, 55));
        //nodes1.setPaymentOptions(new PaymentOptions[]{new PaymentOptions(1000)});
        nodes1.setPaymentProposal(new PaymentProposal(1000, 100));

        List<Nodes> nodes = new ArrayList<>();
        nodes.add(nodes1);

        Game game = new Game();
        game.setMarketableItem(marketableItem);
        game.setViewer(new Viewer(new Meta(new Trades(nodes))));

        return game;
    }

    private PersonalItem createPersonalItem(LocalDateTime date) {
        PersonalItem expected = new PersonalItem();
        expected.setItemId("223");
        expected.setAssetUrl("https://assetUrl.com");
        expected.setName("item name");
        expected.setTags(List.of("Y1S2", "Y7S3"));
        expected.setType(ItemType.DroneSkin);
        expected.setMaxBuyPrice(888);
        expected.setBuyOrdersCount(88);
        expected.setMinSellPrice(999);
        expected.setSellOrdersCount(99);
        expected.setLastSoldAt(date.minusSeconds(1000));
        expected.setLastSoldPrice(777);
        expected.setOwned(true);
        UbiTrade expectedTrade = new UbiTrade();
        expectedTrade.setItem("223");
        expectedTrade.setTradeId("123");
        expectedTrade.setState(TradeState.Created);
        expectedTrade.setCategory(TradeCategory.Sell);
        expectedTrade.setExpiresAt(date);
        expectedTrade.setLastModifiedAt(date.plusSeconds(1000));
        expectedTrade.setProposedPaymentPrice(1000);
        expectedTrade.setProposedPaymentFee(100);
        expectedTrade.setSuccessPaymentPrice(550);
        expectedTrade.setSuccessPaymentFee(55);
        expected.setTrades(List.of(expectedTrade));

        return expected;
    }
}