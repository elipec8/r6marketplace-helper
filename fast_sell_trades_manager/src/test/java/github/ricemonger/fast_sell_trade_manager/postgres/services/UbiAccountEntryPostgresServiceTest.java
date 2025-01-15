package github.ricemonger.fast_sell_trade_manager.postgres.services;

import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.FastSellManagedUserProjection;
import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.ItemMedianPriceAndRarityProjectionI;
import github.ricemonger.fast_sell_trade_manager.postgres.repositories.CustomUbiAccountEntryPostgresRepository;
import github.ricemonger.fast_sell_trade_manager.postgres.repositories.CustomUbiAccountStatsPostgresRepository;
import github.ricemonger.fast_sell_trade_manager.postgres.services.entity_mappers.item.ItemEntityMapper;
import github.ricemonger.fast_sell_trade_manager.postgres.services.entity_mappers.user.UbiAccountStatsEntityMapper;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountEntryPostgresServiceTest {
    @Autowired
    private UbiAccountEntryPostgresService ubiAccountEntryPostgresService;
    @MockBean
    private CustomUbiAccountEntryPostgresRepository customUbiAccountEntryPostgresRepository;
    @MockBean
    private CustomUbiAccountStatsPostgresRepository customUbiAccountStatsPostgresRepository;
    @MockBean
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;
    @MockBean
    private ItemEntityMapper itemEntityMapper;

    @Test
    public void getFastSellManagedUserById_should_return_mapped_repository_result() {
        FastSellManagedUserProjection projection = Mockito.mock(FastSellManagedUserProjection.class);
        when(projection.getUbiProfileId()).thenReturn("ubiProfileId");

        when(customUbiAccountEntryPostgresRepository.findFastSellManagedUserById(1L, "email")).thenReturn(java.util.Optional.of(projection));

        List resaleLocks = Mockito.mock(List.class);
        when(customUbiAccountStatsPostgresRepository.findAllUserResaleLocksItemIds("ubiProfileId")).thenReturn(resaleLocks);

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);

        when(ubiAccountStatsEntityMapper.createFastSellManagedUser(same(projection), same(resaleLocks))).thenReturn(user);

        assertSame(user, ubiAccountEntryPostgresService.getFastSellManagedUserById(1L, "email"));
    }

    @Test
    public void getOwnedItemsMedianPriceAndRarity_should_return_mapped_repository_result() {
        ItemMedianPriceAndRarityProjectionI projection1 = Mockito.mock(ItemMedianPriceAndRarityProjectionI.class);
        ItemMedianPriceAndRarityProjectionI projection2 = Mockito.mock(ItemMedianPriceAndRarityProjectionI.class);

        ItemMedianPriceAndRarity item1 = Mockito.mock(ItemMedianPriceAndRarity.class);
        ItemMedianPriceAndRarity item2 = Mockito.mock(ItemMedianPriceAndRarity.class);

        when(itemEntityMapper.createItemMedianPriceAndRarity(same(projection1))).thenReturn(item1);
        when(itemEntityMapper.createItemMedianPriceAndRarity(same(projection2))).thenReturn(item2);

        when(customUbiAccountStatsPostgresRepository.findOwnedItemsMedianPriceAndRarity("ubiProfileId")).thenReturn(List.of(projection1, projection2));

        List<ItemMedianPriceAndRarity> result = ubiAccountEntryPostgresService.getOwnedItemsMedianPriceAndRarity("ubiProfileId");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(res -> res == item1));
        assertTrue(result.stream().anyMatch(res -> res == item2));
    }
}