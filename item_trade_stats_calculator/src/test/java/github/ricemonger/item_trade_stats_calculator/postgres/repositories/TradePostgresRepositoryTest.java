package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.PrioritizedTradeProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.UbiTradeProjection;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradePostgresRepositoryTest {
    @Autowired
    private TradePostgresRepository tradePostgresRepository;
    @Autowired
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        tradePostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void prioritizeAllTrades_should_update_potential_trade_stats_fields_for_all_projections() {
        ItemEntity item1 = itemPostgresRepository.saveAndFlush(new ItemEntity("itemId1"));
        ItemEntity item2 = itemPostgresRepository.saveAndFlush(new ItemEntity("itemId2"));

        TradeEntity trade1 = new TradeEntity();
        trade1.setTradeId("tradeId1");
        trade1.setState(TradeState.Created);
        trade1.setCategory(TradeCategory.Sell);
        trade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        trade1.setLastModifiedAt(LocalDateTime.of(2021, 1, 2, 1, 1));
        trade1.setSuccessPaymentPrice(101);
        trade1.setSuccessPaymentFee(102);
        trade1.setProposedPaymentPrice(103);
        trade1.setProposedPaymentFee(104);
        trade1.setItem(item1);
        trade1.setMinutesToTrade(105);
        trade1.setTradePriority(106L);

        TradeEntity trade2 = new TradeEntity();
        trade2.setTradeId("tradeId2");
        trade2.setState(TradeState.Created);
        trade2.setCategory(TradeCategory.Sell);
        trade2.setExpiresAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        trade2.setLastModifiedAt(LocalDateTime.of(2022, 1, 2, 1, 1));
        trade2.setSuccessPaymentPrice(201);
        trade2.setSuccessPaymentFee(202);
        trade2.setProposedPaymentPrice(203);
        trade2.setProposedPaymentFee(204);
        trade2.setItem(item2);
        trade2.setMinutesToTrade(205);
        trade2.setTradePriority(206L);

        TradeEntity trade3 = new TradeEntity();
        trade3.setTradeId("tradeId3");
        trade3.setState(TradeState.Created);
        trade3.setCategory(TradeCategory.Sell);
        trade3.setExpiresAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        trade3.setLastModifiedAt(LocalDateTime.of(2023, 1, 2, 1, 1));
        trade3.setSuccessPaymentPrice(301);
        trade3.setSuccessPaymentFee(302);
        trade3.setProposedPaymentPrice(303);
        trade3.setProposedPaymentFee(304);
        trade3.setItem(item1);
        trade3.setMinutesToTrade(305);
        trade3.setTradePriority(306L);

        tradePostgresRepository.saveAllAndFlush(Arrays.asList(trade1, trade2, trade3));

        PrioritizedTradeProjection projection1 = new PrioritizedTradeProjection("tradeId1", 1001, 1002L);
        PrioritizedTradeProjection projection2 = new PrioritizedTradeProjection("tradeId2", 2001, 2002L);
        PrioritizedTradeProjection projection4 = new PrioritizedTradeProjection("tradeId4", 3001, 3002L);

        tradePostgresRepository.prioritizeAllTrades(Arrays.asList(projection1, projection2, projection4));

        List<TradeEntity> result = tradePostgresRepository.findAll();

        TradeEntity expectedTrade1 = new TradeEntity();
        expectedTrade1.setTradeId("tradeId1");
        expectedTrade1.setState(TradeState.Created);
        expectedTrade1.setCategory(TradeCategory.Sell);
        expectedTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        expectedTrade1.setLastModifiedAt(LocalDateTime.of(2021, 1, 2, 1, 1));
        expectedTrade1.setSuccessPaymentPrice(101);
        expectedTrade1.setSuccessPaymentFee(102);
        expectedTrade1.setProposedPaymentPrice(103);
        expectedTrade1.setProposedPaymentFee(104);
        expectedTrade1.setItem(item1);
        expectedTrade1.setMinutesToTrade(1001);
        expectedTrade1.setTradePriority(1002L);

        TradeEntity expectedTrade2 = new TradeEntity();
        expectedTrade2.setTradeId("tradeId2");
        expectedTrade2.setState(TradeState.Created);
        expectedTrade2.setCategory(TradeCategory.Sell);
        expectedTrade2.setExpiresAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        expectedTrade2.setLastModifiedAt(LocalDateTime.of(2022, 1, 2, 1, 1));
        expectedTrade2.setSuccessPaymentPrice(201);
        expectedTrade2.setSuccessPaymentFee(202);
        expectedTrade2.setProposedPaymentPrice(203);
        expectedTrade2.setProposedPaymentFee(204);
        expectedTrade2.setItem(item2);
        expectedTrade2.setMinutesToTrade(2001);
        expectedTrade2.setTradePriority(2002L);

        TradeEntity expectedTrade3 = trade3;

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(trade -> trade.isFullyEqual(expectedTrade1)));
        assertTrue(result.stream().anyMatch(trade -> trade.isFullyEqual(expectedTrade2)));
        assertTrue(result.stream().anyMatch(trade -> trade.isFullyEqual(expectedTrade3)));
    }

    @Test
    public void prioritizeAllTrades_should_update_potential_trade_stats_fields_for_projection() {
        ItemEntity item1 = itemPostgresRepository.saveAndFlush(new ItemEntity("itemId1"));
        ItemEntity item2 = itemPostgresRepository.saveAndFlush(new ItemEntity("itemId2"));

        TradeEntity trade1 = new TradeEntity();
        trade1.setTradeId("tradeId1");
        trade1.setState(TradeState.Created);
        trade1.setCategory(TradeCategory.Sell);
        trade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        trade1.setLastModifiedAt(LocalDateTime.of(2021, 1, 2, 1, 1));
        trade1.setSuccessPaymentPrice(101);
        trade1.setSuccessPaymentFee(102);
        trade1.setProposedPaymentPrice(103);
        trade1.setProposedPaymentFee(104);
        trade1.setItem(item1);
        trade1.setMinutesToTrade(105);
        trade1.setTradePriority(106L);

        TradeEntity trade2 = new TradeEntity();
        trade2.setTradeId("tradeId2");
        trade2.setState(TradeState.Created);
        trade2.setCategory(TradeCategory.Sell);
        trade2.setExpiresAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        trade2.setLastModifiedAt(LocalDateTime.of(2022, 1, 2, 1, 1));
        trade2.setSuccessPaymentPrice(201);
        trade2.setSuccessPaymentFee(202);
        trade2.setProposedPaymentPrice(203);
        trade2.setProposedPaymentFee(204);
        trade2.setItem(item2);
        trade2.setMinutesToTrade(205);
        trade2.setTradePriority(206L);

        TradeEntity trade3 = new TradeEntity();
        trade3.setTradeId("tradeId3");
        trade3.setState(TradeState.Created);
        trade3.setCategory(TradeCategory.Sell);
        trade3.setExpiresAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        trade3.setLastModifiedAt(LocalDateTime.of(2023, 1, 2, 1, 1));
        trade3.setSuccessPaymentPrice(301);
        trade3.setSuccessPaymentFee(302);
        trade3.setProposedPaymentPrice(303);
        trade3.setProposedPaymentFee(304);
        trade3.setItem(item1);
        trade3.setMinutesToTrade(305);
        trade3.setTradePriority(306L);

        tradePostgresRepository.saveAllAndFlush(Arrays.asList(trade1, trade2, trade3));

        PrioritizedTradeProjection projection1 = new PrioritizedTradeProjection("tradeId1", 1001, 1002L);

        tradePostgresRepository.prioritizeTrade(projection1);

        List<TradeEntity> result = tradePostgresRepository.findAll();

        TradeEntity expectedTrade1 = new TradeEntity();
        expectedTrade1.setTradeId("tradeId1");
        expectedTrade1.setState(TradeState.Created);
        expectedTrade1.setCategory(TradeCategory.Sell);
        expectedTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        expectedTrade1.setLastModifiedAt(LocalDateTime.of(2021, 1, 2, 1, 1));
        expectedTrade1.setSuccessPaymentPrice(101);
        expectedTrade1.setSuccessPaymentFee(102);
        expectedTrade1.setProposedPaymentPrice(103);
        expectedTrade1.setProposedPaymentFee(104);
        expectedTrade1.setItem(item1);
        expectedTrade1.setMinutesToTrade(1001);
        expectedTrade1.setTradePriority(1002L);

        TradeEntity expectedTrade2 = trade2;

        TradeEntity expectedTrade3 = trade3;

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(trade -> trade.isFullyEqual(expectedTrade1)));
        assertTrue(result.stream().anyMatch(trade -> trade.isFullyEqual(expectedTrade2)));
        assertTrue(result.stream().anyMatch(trade -> trade.isFullyEqual(expectedTrade3)));
    }

    @Test
    public void findAllUbiTrades_should_return_projection_for_all_trades() {
        ItemEntity item1 = itemPostgresRepository.saveAndFlush(new ItemEntity("itemId1"));
        ItemEntity item2 = itemPostgresRepository.saveAndFlush(new ItemEntity("itemId2"));

        TradeEntity trade1 = new TradeEntity();
        trade1.setTradeId("tradeId1");
        trade1.setState(TradeState.Created);
        trade1.setCategory(TradeCategory.Sell);
        trade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        trade1.setLastModifiedAt(LocalDateTime.of(2021, 1, 2, 1, 1));
        trade1.setSuccessPaymentPrice(101);
        trade1.setSuccessPaymentFee(102);
        trade1.setProposedPaymentPrice(103);
        trade1.setProposedPaymentFee(104);
        trade1.setItem(item1);
        trade1.setMinutesToTrade(105);
        trade1.setTradePriority(106L);

        TradeEntity trade2 = new TradeEntity();
        trade2.setTradeId("tradeId2");
        trade2.setState(TradeState.Created);
        trade2.setCategory(TradeCategory.Sell);
        trade2.setExpiresAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        trade2.setLastModifiedAt(LocalDateTime.of(2022, 1, 2, 1, 1));
        trade2.setSuccessPaymentPrice(201);
        trade2.setSuccessPaymentFee(202);
        trade2.setProposedPaymentPrice(203);
        trade2.setProposedPaymentFee(204);
        trade2.setItem(item2);
        trade2.setMinutesToTrade(205);
        trade2.setTradePriority(206L);

        TradeEntity trade3 = new TradeEntity();
        trade3.setTradeId("tradeId3");
        trade3.setState(TradeState.Created);
        trade3.setCategory(TradeCategory.Sell);
        trade3.setExpiresAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        trade3.setLastModifiedAt(LocalDateTime.of(2023, 1, 2, 1, 1));
        trade3.setSuccessPaymentPrice(301);
        trade3.setSuccessPaymentFee(302);
        trade3.setProposedPaymentPrice(303);
        trade3.setProposedPaymentFee(304);
        trade3.setItem(item1);
        trade3.setMinutesToTrade(305);
        trade3.setTradePriority(306L);

        tradePostgresRepository.saveAllAndFlush(Arrays.asList(trade1, trade2, trade3));

        UbiTradeProjection expected1 = new UbiTradeProjection();
        expected1.setTradeId("tradeId1");
        expected1.setState(TradeState.Created);
        expected1.setCategory(TradeCategory.Sell);
        expected1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        expected1.setLastModifiedAt(LocalDateTime.of(2021, 1, 2, 1, 1));
        expected1.setItem(item1);
        expected1.setSuccessPaymentPrice(101);
        expected1.setSuccessPaymentFee(102);
        expected1.setProposedPaymentPrice(103);
        expected1.setProposedPaymentFee(104);

        UbiTradeProjection expected2 = new UbiTradeProjection();
        expected2.setTradeId("tradeId2");
        expected2.setState(TradeState.Created);
        expected2.setCategory(TradeCategory.Sell);
        expected2.setExpiresAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        expected2.setLastModifiedAt(LocalDateTime.of(2022, 1, 2, 1, 1));
        expected2.setItem(item2);
        expected2.setSuccessPaymentPrice(201);
        expected2.setSuccessPaymentFee(202);
        expected2.setProposedPaymentPrice(203);
        expected2.setProposedPaymentFee(204);

        UbiTradeProjection expected3 = new UbiTradeProjection();
        expected3.setTradeId("tradeId3");
        expected3.setState(TradeState.Created);
        expected3.setCategory(TradeCategory.Sell);
        expected3.setExpiresAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        expected3.setLastModifiedAt(LocalDateTime.of(2023, 1, 2, 1, 1));
        expected3.setItem(item1);
        expected3.setSuccessPaymentPrice(301);
        expected3.setSuccessPaymentFee(302);
        expected3.setProposedPaymentPrice(303);
        expected3.setProposedPaymentFee(304);

        List<UbiTradeProjection> result = tradePostgresRepository.findAllUbiTrades();

        assertEquals(3, result.size());
        assertTrue(result.contains(expected1));
        assertTrue(result.contains(expected2));
        assertTrue(result.contains(expected3));
    }

}