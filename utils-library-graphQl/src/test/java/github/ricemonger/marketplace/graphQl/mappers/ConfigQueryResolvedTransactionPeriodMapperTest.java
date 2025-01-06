package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.ConfigQueryResolvedTransactionPeriodMapper;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.TradesLimitations;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.DTO.tradeLimitations.Sell;
import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.exceptions.server.GraphQlConfigResolvedTransactionPeriodMappingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ConfigQueryResolvedTransactionPeriodMapperTest {

    private final ConfigQueryResolvedTransactionPeriodMapper configQueryResolvedTransactionPeriodMapper = new ConfigQueryResolvedTransactionPeriodMapper();

    @Test
    public void mapResolvedTransactionPeriod_should_map_with_valid_fields() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        Buy buy = new Buy();
        buy.setResolvedTransactionPeriodInMinutes(10);
        tradesLimitations.setBuy(buy);
        Sell sell = new Sell();
        sell.setResolvedTransactionPeriodInMinutes(20);
        tradesLimitations.setSell(sell);

        ConfigResolvedTransactionPeriod result = configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);

        assertEquals(10, result.getBuyResolvedTransactionPeriod());
        assertEquals(20, result.getSellResolvedTransactionPeriod());
    }

    @Test
    public void mapResolvedTransactionPeriod_should_throw_exception_with_null_tradesLimitations() {
        assertThrows(GraphQlConfigResolvedTransactionPeriodMappingException.class, () -> {
            configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(null);
        });
    }

    @Test
    public void mapResolvedTransactionPeriod_should_throw_exception_with_null_buy() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        Buy buy = null;
        tradesLimitations.setBuy(buy);
        Sell sell = new Sell();
        sell.setResolvedTransactionPeriodInMinutes(20);
        tradesLimitations.setSell(sell);

        assertThrows(GraphQlConfigResolvedTransactionPeriodMappingException.class, () -> {
            configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);
        });
    }

    @Test
    public void mapResolvedTransactionPeriod_should_throw_exception_with_null_buy_buyResolvedTransactionPeriod() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        Buy buy = new Buy();
        buy.setResolvedTransactionPeriodInMinutes(null);
        tradesLimitations.setBuy(buy);
        Sell sell = new Sell();
        sell.setResolvedTransactionPeriodInMinutes(20);
        tradesLimitations.setSell(sell);

        assertThrows(GraphQlConfigResolvedTransactionPeriodMappingException.class, () -> {
            configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);
        });
    }

    @Test
    public void mapResolvedTransactionPeriod_should_throw_exception_with_null_sell() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        Buy buy = new Buy();
        buy.setResolvedTransactionPeriodInMinutes(10);
        tradesLimitations.setBuy(buy);
        Sell sell = null;
        tradesLimitations.setSell(sell);

        assertThrows(GraphQlConfigResolvedTransactionPeriodMappingException.class, () -> {
            configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);
        });
    }

    @Test
    public void mapResolvedTransactionPeriod_should_throw_exception_with_null_sell_sellResolvedTransactionPeriod() {
        TradesLimitations tradesLimitations = new TradesLimitations();
        Buy buy = new Buy();
        buy.setResolvedTransactionPeriodInMinutes(10);
        tradesLimitations.setBuy(buy);
        Sell sell = new Sell();
        sell.setResolvedTransactionPeriodInMinutes(null);
        tradesLimitations.setSell(sell);

        assertThrows(GraphQlConfigResolvedTransactionPeriodMappingException.class, () -> {
            configQueryResolvedTransactionPeriodMapper.mapConfigResolvedTransactionPeriod(tradesLimitations);
        });
    }
}