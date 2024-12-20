package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.DTOs.config_query_trade.TradesConfig;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_trade.tradesConfig.BuyLimit;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_trade.tradesConfig.SellLimit;
import github.ricemonger.marketplace.graphQl.DTOs.config_query_trade.tradesConfig.TransactionFeesConfig;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.exceptions.server.GraphQlConfigTradeMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigQueryTradeMapperTest {
    @Autowired
    private ConfigQueryTradeMapper configQueryTradeMapper;

    @Test
    public void mapConfigTrades_should_map_with_valid_fields() {
        TradesConfig tradesConfig = createTradesConfig();

        ConfigTrades result = configQueryTradeMapper.mapConfigTrades(tradesConfig);

        assertEquals(1, result.getSaleExpiresAfterMinutes());
        assertEquals(2, result.getBuySlots());
        assertEquals(3, result.getSellSlots());
        assertEquals(4, result.getResaleLockDurationInMinutes());
        assertTrue(result.isTwoFactorAuthenticationRule());
        assertFalse(result.isGameOwnershipRule());
        assertEquals(5, result.getBuyLimit());
        assertEquals(6, result.getSellLimit());
        assertEquals("paymentItemId", result.getPaymentItemId());
        assertEquals(7, result.getFeePercentage());

        tradesConfig.setTwoFactorAuthenticationRule(false);
        tradesConfig.setGameOwnershipRule(true);

        result = configQueryTradeMapper.mapConfigTrades(tradesConfig);

        assertFalse(result.isTwoFactorAuthenticationRule());
        assertTrue(result.isGameOwnershipRule());
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_tradesConfig_is_null() {
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(null);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_tradesConfig_fields_are_null() {
        TradesConfig tradesConfig = new TradesConfig();
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_transactionFeesConfig_is_empty() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setTransactionFeesConfig(new TransactionFeesConfig[0]);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_transactionFeesConfig_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setTransactionFeesConfig(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_transactionFeesConfig_paymentItemId_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.getTransactionFeesConfig()[0].setPaymentItemId(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_transactionFeesConfig_feePercentage_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.getTransactionFeesConfig()[0].setFeePercentage(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_sellLimit_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setSellLimit(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_sellLimit_maximum_count_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.getSellLimit().setMaximumCount(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_buyLimit_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setBuyLimit(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_buyLimit_maximum_count_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.getBuyLimit().setMaximumCount(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_gameOwnershipRule_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setGameOwnershipRule(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_twoFactorAuthenticationRule_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setTwoFactorAuthenticationRule(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_resaleLockDurationInMinutes_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setResaleLockDurationInMinutes(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_sellSlots_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setSellSlots(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_buySlots_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setBuySlots(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    @Test
    public void mapConfigTrades_should_throw_exception_when_saleExpiresAfterMinutes_is_null() {
        TradesConfig tradesConfig = createTradesConfig();
        tradesConfig.setSaleExpiresAfterMinutes(null);
        assertThrows(GraphQlConfigTradeMappingException.class, () -> {
            configQueryTradeMapper.mapConfigTrades(tradesConfig);
        });
    }

    private TradesConfig createTradesConfig() {
        TradesConfig tradesConfig = new TradesConfig();
        tradesConfig.setSaleExpiresAfterMinutes(1);
        tradesConfig.setBuySlots(2);
        tradesConfig.setSellSlots(3);
        tradesConfig.setResaleLockDurationInMinutes(4);
        tradesConfig.setTwoFactorAuthenticationRule(true);
        tradesConfig.setGameOwnershipRule(false);
        BuyLimit buyLimit = new BuyLimit();
        buyLimit.setMaximumCount(5);
        tradesConfig.setBuyLimit(buyLimit);
        SellLimit sellLimit = new SellLimit();
        sellLimit.setMaximumCount(6);
        tradesConfig.setSellLimit(sellLimit);
        TransactionFeesConfig[] transactionFeesConfigs = new TransactionFeesConfig[1];
        TransactionFeesConfig transactionFeesConfig = new TransactionFeesConfig();
        transactionFeesConfig.setPaymentItemId("paymentItemId");
        transactionFeesConfig.setFeePercentage(7);
        transactionFeesConfigs[0] = transactionFeesConfig;
        tradesConfig.setTransactionFeesConfig(transactionFeesConfigs);
        return tradesConfig;
    }
}