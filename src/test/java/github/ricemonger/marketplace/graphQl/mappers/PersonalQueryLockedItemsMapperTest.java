package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.TradeLimitations;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations.Buy;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations.Sell;
import github.ricemonger.marketplace.graphQl.dtos.personal_query_locked_items.tradeLimitations.sell.ResaleLocks;
import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.dtos.LockedItem;
import github.ricemonger.utils.dtos.UserTransactionsCount;
import github.ricemonger.utils.exceptions.GraphQlPersonalLockedItemsMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PersonalQueryLockedItemsMapperTest {
    @SpyBean
    private PersonalQueryLockedItemsMapper personalQueryLockedItemsMapper;
    @Autowired
    private CommonValuesService commonValuesService;

    @Test
    public void mapLockedItems_should_map_each_item() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        TradeLimitations tradeLimitations = new TradeLimitations();
        tradeLimitations.setSell(new Sell());
        tradeLimitations.getSell().setResaleLocks(new ArrayList<>());
        tradeLimitations.getSell().getResaleLocks().add(new ResaleLocks("1", sdf.format(date)));
        tradeLimitations.getSell().getResaleLocks().add(new ResaleLocks("2", sdf.format(date.getTime() + 1000)));

        LockedItem expected1 = new LockedItem("1", date);
        LockedItem expected2 = new LockedItem("2", new Date(date.getTime() + 1000));

        List<LockedItem> result = personalQueryLockedItemsMapper.mapLockedItems(tradeLimitations);

        assertTrue(result.contains(expected1) && result.contains(expected2));
        verify(personalQueryLockedItemsMapper, times(2)).mapLockedItem(any());
    }

    @Test
    public void mapLockedItems_should_throw_exception_when_tradeLimitations_is_null() {
        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapLockedItems(null);
        });
    }

    @Test
    public void mapLockedItems_should_throw_exception_when_sell_is_null() {
        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapLockedItems(new TradeLimitations());
        });
    }

    @Test
    public void mapLockedItems_should_throw_exception_when_resaleLocks_is_null() {
        TradeLimitations tradeLimitations = new TradeLimitations();
        tradeLimitations.setSell(new Sell());

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapLockedItems(tradeLimitations);
        });
    }

    @Test
    public void mapLockedItem_should_map_item_with_valid_fields() {
        SimpleDateFormat sdf = new SimpleDateFormat(commonValuesService.getDateFormat());
        Date date = new Date();

        ResaleLocks resaleLocks = new ResaleLocks("1", sdf.format(date));

        LockedItem expected = new LockedItem("1", date);

        assertEquals(expected, personalQueryLockedItemsMapper.mapLockedItem(resaleLocks));
    }

    @Test
    public void mapLockedItem_should_map_item_with_invalid_expiresAt() {
        ResaleLocks resaleLocks = new ResaleLocks("1", "invalid date");

        LockedItem expected = new LockedItem("1", new Date(0));

        assertEquals(expected, personalQueryLockedItemsMapper.mapLockedItem(resaleLocks));
    }

    @Test
    public void mapLockedItem_should_throw_exception_when_resaleLocks_is_null() {
        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapLockedItem(null);
        });
    }

    @Test
    public void mapLockedItem_should_throw_exception_when_itemId_is_null() {
        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapLockedItem(new ResaleLocks(null, "2021-01-01"));
        });
    }

    @Test
    public void mapLockedItem_should_throw_exception_when_expiresAt_is_null() {
        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapLockedItem(new ResaleLocks("1", null));
        });
    }

    @Test
    public void mapUserTransactionsCount_should_map_user_transactions_count() {
        TradeLimitations tradeLimitations = createTradeLimitations();

        UserTransactionsCount expected = new UserTransactionsCount(1, 2, 3, 4);

        assertEquals(expected, personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations));
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_tradeLimitations_is_null() {
        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(null);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_buy_is_null() {
        TradeLimitations tradeLimitations = createTradeLimitations();
        tradeLimitations.setBuy(null);

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_sell_is_null() {
        TradeLimitations tradeLimitations = createTradeLimitations();
        tradeLimitations.setSell(null);

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_buy_activeTransactionCount_is_null() {
        TradeLimitations tradeLimitations = createTradeLimitations();
        tradeLimitations.getBuy().setActiveTransactionCount(null);

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_buy_resolvedTransactionCount_is_null() {
        TradeLimitations tradeLimitations = createTradeLimitations();
        tradeLimitations.getBuy().setResolvedTransactionCount(null);

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_sell_activeTransactionCount_is_null() {
        TradeLimitations tradeLimitations = createTradeLimitations();
        tradeLimitations.getSell().setActiveTransactionCount(null);

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations);
        });
    }

    @Test
    public void mapUserTransactionsCount_should_throw_exception_when_sell_resolvedTransactionCount_is_null() {
        TradeLimitations tradeLimitations = createTradeLimitations();
        tradeLimitations.getSell().setResolvedTransactionCount(null);

        assertThrows(GraphQlPersonalLockedItemsMappingException.class, () -> {
            personalQueryLockedItemsMapper.mapUserTransactionsCount(tradeLimitations);
        });
    }

    private TradeLimitations createTradeLimitations() {
        TradeLimitations tradeLimitations = new TradeLimitations();
        tradeLimitations.setBuy(new Buy());
        tradeLimitations.setSell(new Sell());
        tradeLimitations.getBuy().setResolvedTransactionCount(1);
        tradeLimitations.getBuy().setActiveTransactionCount(2);
        tradeLimitations.getSell().setResaleLocks(new ArrayList<>());
        tradeLimitations.getSell().setResolvedTransactionCount(3);
        tradeLimitations.getSell().setActiveTransactionCount(4);
        return tradeLimitations;
    }
}