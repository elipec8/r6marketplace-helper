package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.UserForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManager;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserEntityTest {

    @Test
    public void toUserForCentralTradeManagerDTO_should_properly_map_dto() {
        ItemFilterEntity itemFilterEntity1 = new ItemFilterEntity();
        itemFilterEntity1.setName("itemFilterName1");
        itemFilterEntity1.setFilterType(FilterType.ALLOW);

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity1 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity1.setPriority(1);
        tradeByFiltersManagerEntity1.setTradeOperationType(TradeOperationType.BUY);
        tradeByFiltersManagerEntity1.setAppliedFilters(List.of(itemFilterEntity1));

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity1 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity1.setPriority(2);
        tradeByItemIdManagerEntity1.setTradeOperationType(TradeOperationType.BUY);
        tradeByItemIdManagerEntity1.setItem(new ItemEntity("itemId2"));

        UserEntity userEntity = new UserEntity();
        userEntity.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));
        userEntity.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        userEntity.setId(1L);
        userEntity.setPrivateNotificationsEnabledFlag(true);

        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chatId");
        userEntity.setTelegramUser(telegramUserEntity);

        UbiAccountStatsEntity ubiAccountStatsEntity = new UbiAccountStatsEntity();
        ubiAccountStatsEntity.setUbiProfileId("ubiProfileId");
        ubiAccountStatsEntity.setSoldIn24h(1);
        ubiAccountStatsEntity.setBoughtIn24h(2);

        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAccountStats(ubiAccountStatsEntity);
        ubiAccountEntryEntity.setUbiSessionId("ubiSessionId");
        ubiAccountEntryEntity.setUbiSpaceId("ubiSpaceId");
        ubiAccountEntryEntity.setUbiAuthTicket("ubiAuthTicket");
        ubiAccountEntryEntity.setUbiTwoFactorAuthTicket("ubiTwoFactorAuthTicket");
        ubiAccountEntryEntity.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        ubiAccountEntryEntity.setUbiRememberMeTicket("ubiRememberMeTicket");
        userEntity.setUbiAccountAuthorizationEntry(ubiAccountEntryEntity);

        Collection<Item> existingItems = List.of(new Item("itemId1"), new Item("itemId2"));

        UserForCentralTradeManager userForCentralTradeManager = userEntity.toUserForCentralTradeManagerDTO(existingItems);

        ItemForCentralTradeManager itemForCentralTradeManager1 = new ItemForCentralTradeManager();
        itemForCentralTradeManager1.setItem(new Item("itemId1"));
        itemForCentralTradeManager1.setTradeOperationType(TradeOperationType.BUY);
        itemForCentralTradeManager1.setPriority(1);

        ItemForCentralTradeManager itemForCentralTradeManager2 = new ItemForCentralTradeManager();
        itemForCentralTradeManager2.setItem(new Item("itemId2"));
        itemForCentralTradeManager2.setTradeOperationType(TradeOperationType.BUY);
        itemForCentralTradeManager2.setPriority(2);


        List<ItemForCentralTradeManager> expectedItems = List.of(itemForCentralTradeManager1, itemForCentralTradeManager2);

        assertEquals(userEntity.getId(), userForCentralTradeManager.getId());
        assertEquals(userEntity.getPrivateNotificationsEnabledFlag(), userForCentralTradeManager.getPrivateNotificationsEnabledFlag());
        assertEquals(userEntity.getTelegramUser().getChatId(), userForCentralTradeManager.getChatId());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiAccountStats().toUbiAccountStats(), userForCentralTradeManager.getUbiAccountStats());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiSessionId(), userForCentralTradeManager.getUbiSessionId());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiSpaceId(), userForCentralTradeManager.getUbiSpaceId());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiAuthTicket(), userForCentralTradeManager.getUbiAuthTicket());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiTwoFactorAuthTicket(), userForCentralTradeManager.getUbiTwoFactorAuthTicket());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiRememberDeviceTicket(), userForCentralTradeManager.getUbiRememberDeviceTicket());
        assertEquals(userEntity.getUbiAccountAuthorizationEntry().getUbiRememberMeTicket(), userForCentralTradeManager.getUbiRememberMeTicket());

        assertTrue(userForCentralTradeManager.getItemsForCentralTradeManager().containsAll(expectedItems) && userForCentralTradeManager.getItemsForCentralTradeManager().size() == expectedItems.size());
    }
}