package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityMapperTest {
    @Autowired
    private UserEntityMapper userEntityMapper;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;

    @Test
    public void mapItemShowSettings_should_properly_map_dto() {
        ItemFilterEntity itemFilterEntity1 = Mockito.mock(ItemFilterEntity.class);
        ItemFilterEntity itemFilterEntity2 = Mockito.mock(ItemFilterEntity.class);

        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("filter1");
        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("filter2");

        Mockito.when(itemFilterEntityMapper.createDTO(itemFilterEntity1)).thenReturn(itemFilter1);
        Mockito.when(itemFilterEntityMapper.createDTO(itemFilterEntity2)).thenReturn(itemFilter2);

        ItemShowSettingsProjection itemShowSettingsProjection = new ItemShowSettingsProjection();
        itemShowSettingsProjection.setItemShowMessagesLimit(10);
        itemShowSettingsProjection.setItemShowFewInMessageFlag(true);
        itemShowSettingsProjection.setItemShowNameFlag(false);
        itemShowSettingsProjection.setItemShowItemTypeFlag(true);
        itemShowSettingsProjection.setItemShowMaxBuyPrice(false);
        itemShowSettingsProjection.setItemShowBuyOrdersCountFlag(true);
        itemShowSettingsProjection.setItemShowMinSellPriceFlag(false);
        itemShowSettingsProjection.setItemsShowSellOrdersCountFlag(true);
        itemShowSettingsProjection.setItemShowPictureFlag(false);
        Collection<ItemFilterEntity> itemFilters = new ArrayList<>();

        ItemShowSettings itemShowSettings = userEntityMapper.mapItemShowSettings(itemShowSettingsProjection, itemFilters);

        assertEquals(itemShowSettingsProjection.getItemShowMessagesLimit(), itemShowSettings.getItemShowMessagesLimit());
        assertEquals(itemShowSettingsProjection.getItemShowFewInMessageFlag(), itemShowSettings.getItemShowFewInMessageFlag());
        assertEquals(itemShowSettingsProjection.getItemShowNameFlag(), itemShowSettings.getItemShowNameFlag());
        assertEquals(itemShowSettingsProjection.getItemShowItemTypeFlag(), itemShowSettings.getItemShowItemTypeFlag());
        assertEquals(itemShowSettingsProjection.getItemShowMaxBuyPrice(), itemShowSettings.getItemShowMaxBuyPrice());
        assertEquals(itemShowSettingsProjection.getItemShowBuyOrdersCountFlag(), itemShowSettings.getItemShowBuyOrdersCountFlag());
        assertEquals(itemShowSettingsProjection.getItemShowMinSellPriceFlag(), itemShowSettings.getItemShowMinSellPriceFlag());
        assertEquals(itemShowSettingsProjection.getItemsShowSellOrdersCountFlag(), itemShowSettings.getItemShowSellOrdersCountFlag());
        assertEquals(itemShowSettingsProjection.getItemShowPictureFlag(), itemShowSettings.getItemShowPictureFlag());
        assertEquals(2, itemShowSettings.getItemShowAppliedFilters().size());
        assertTrue(itemShowSettings.getItemShowAppliedFilters().contains(itemFilter1));
        assertTrue(itemShowSettings.getItemShowAppliedFilters().contains(itemFilter2));
    }

}