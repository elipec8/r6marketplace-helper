package github.ricemonger.fast_sell_trade_manager.services;

import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.abstractions.UbiAccountEntryDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountEntryServiceTest {
    @Autowired
    private UbiAccountEntryService ubiAccountEntryService;
    @MockBean
    private UbiAccountEntryDatabaseService ubiAccountEntryDatabaseService;

    @Test
    public void getFastSellManagedUserById_should_return_db_service_result() {
        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);

        when(ubiAccountEntryDatabaseService.getFastSellManagedUserById(1L, "email")).thenReturn(user);

        assertSame(user, ubiAccountEntryService.getFastSellManagedUserById(1L, "email"));
    }

    @Test
    public void getOwnedItemsMedianPriceAndRarity_should_return_db_service_result() {
        List list = Mockito.mock(List.class);

        when(ubiAccountEntryDatabaseService.getOwnedItemsMedianPriceAndRarity("ubiProfileId")).thenReturn(list);

        assertSame(list, ubiAccountEntryService.getOwnedItemsMedianPriceAndRarity("ubiProfileId"));
    }
}