package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShownFieldsSettingsProjection;
import github.ricemonger.marketplace.databases.postgres.dto_projections.NotificationsSettingsProjection;
import github.ricemonger.marketplace.databases.postgres.dto_projections.TradeManagersSettingsProjection;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.NotificationsSettings;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;

@SpringBootTest
class UserEntityMapperTest {
    @Autowired
    private UserEntityMapper userEntityMapper;
    @MockBean
    private ItemFilterEntityMapper itemFilterEntityMapper;

    @Test
    public void createItemShowSettings_should_properly_create_dto() {
        ItemFilterEntity itemFilterEntity1 = Mockito.mock(ItemFilterEntity.class);
        ItemFilterEntity itemFilterEntity2 = Mockito.mock(ItemFilterEntity.class);

        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("filter1");
        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("filter2");

        Mockito.when(itemFilterEntityMapper.createDTO(same(itemFilterEntity1))).thenReturn(itemFilter1);
        Mockito.when(itemFilterEntityMapper.createDTO(same(itemFilterEntity2))).thenReturn(itemFilter2);

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
        Collection<ItemFilterEntity> itemFilters = List.of(itemFilterEntity1, itemFilterEntity2);

        ItemShowSettings itemShowSettings = userEntityMapper.createItemShowSettings(itemShowSettingsProjection, itemFilters);

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

    @Test
    public void createTradeManagersSettings_should_create_dto_by_projection() {
        TradeManagersSettingsProjection tradeManagersSettingsProjection = new TradeManagersSettingsProjection();
        tradeManagersSettingsProjection.setNewManagersAreActiveFlag(true);
        tradeManagersSettingsProjection.setManagingEnabledFlag(false);
        tradeManagersSettingsProjection.setSellTradesManagingEnabledFlag(true);
        tradeManagersSettingsProjection.setBuyTradesManagingEnabledFlag(false);

        TradeManagersSettings tradeManagersSettings = userEntityMapper.createTradeManagersSettings(tradeManagersSettingsProjection);

        assertEquals(tradeManagersSettingsProjection.getNewManagersAreActiveFlag(), tradeManagersSettings.getNewManagersAreActiveFlag());
        assertEquals(tradeManagersSettingsProjection.getManagingEnabledFlag(), tradeManagersSettings.getManagingEnabledFlag());
        assertEquals(tradeManagersSettingsProjection.getSellTradesManagingEnabledFlag(), tradeManagersSettings.getSellTradesManagingEnabledFlag());
        assertEquals(tradeManagersSettingsProjection.getBuyTradesManagingEnabledFlag(), tradeManagersSettings.getBuyTradesManagingEnabledFlag());
    }

    @Test
    public void createNotificationsSettings_should_create_dto_by_projection() {
        NotificationsSettingsProjection notificationsSettingsProjection = new NotificationsSettingsProjection();
        notificationsSettingsProjection.setPublicNotificationsEnabledFlag(true);
        notificationsSettingsProjection.setPrivateNotificationsEnabledFlag(false);
        notificationsSettingsProjection.setUbiStatsUpdatedNotificationsEnabledFlag(true);
        notificationsSettingsProjection.setTradeManagerNotificationsEnabledFlag(false);
        notificationsSettingsProjection.setAuthorizationNotificationsEnabledFlag(true);

        NotificationsSettings notificationsSettings = userEntityMapper.createNotificationsSettings(notificationsSettingsProjection);

        assertEquals(notificationsSettingsProjection.getPublicNotificationsEnabledFlag(), notificationsSettings.getPublicNotificationsEnabledFlag());
        assertEquals(notificationsSettingsProjection.getPrivateNotificationsEnabledFlag(), notificationsSettings.getPrivateNotificationsEnabledFlag());
        assertEquals(notificationsSettingsProjection.getUbiStatsUpdatedNotificationsEnabledFlag(), notificationsSettings.getUbiStatsUpdatedNotificationsEnabledFlag());
        assertEquals(notificationsSettingsProjection.getTradeManagerNotificationsEnabledFlag(), notificationsSettings.getTradeManagerNotificationsEnabledFlag());
        assertEquals(notificationsSettingsProjection.getAuthorizationNotificationsEnabledFlag(), notificationsSettings.getAuthorizationNotificationsEnabledFlag());
    }

    @Test
    public void createItemShownFieldsSettingsProjection_should_create_projection_by_dto() {
        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(true);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(true);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(true);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(true);

        ItemShownFieldsSettingsProjection itemShownFieldsSettingsProjection = userEntityMapper.createItemShownFieldsSettingsProjection(settings);

        assertEquals(settings.getItemShowNameFlag(), itemShownFieldsSettingsProjection.getItemShowNameFlag());
        assertEquals(settings.getItemShowItemTypeFlag(), itemShownFieldsSettingsProjection.getItemShowItemTypeFlag());
        assertEquals(settings.getItemShowMaxBuyPrice(), itemShownFieldsSettingsProjection.getItemShowMaxBuyPrice());
        assertEquals(settings.getItemShowBuyOrdersCountFlag(), itemShownFieldsSettingsProjection.getItemShowBuyOrdersCountFlag());
        assertEquals(settings.getItemShowMinSellPriceFlag(), itemShownFieldsSettingsProjection.getItemShowMinSellPriceFlag());
        assertEquals(settings.getItemsShowSellOrdersCountFlag(), itemShownFieldsSettingsProjection.getItemsShowSellOrdersCountFlag());
        assertEquals(settings.getItemShowPictureFlag(), itemShownFieldsSettingsProjection.getItemShowPictureFlag());
    }
}