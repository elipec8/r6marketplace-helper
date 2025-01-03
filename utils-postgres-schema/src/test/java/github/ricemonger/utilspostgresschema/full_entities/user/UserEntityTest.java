package github.ricemonger.utilspostgresschema.full_entities.user;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(new TelegramUserEntity());
        user1.getTelegramUser().setChatId("chatId");
        user1.setUbiAccountEntry(new UbiAccountEntryEntity());
        user1.getUbiAccountEntry().setEmail("email");
        user1.setItemFilters(List.of(new ItemFilterEntity()));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user1.setPublicNotificationsEnabledFlag(false);
        user1.setPrivateNotificationsEnabledFlag(false);
        user1.setItemShowNameFlag(false);
        user1.setItemShowItemTypeFlag(false);
        user1.setItemShowMaxBuyPrice(false);
        user1.setItemShowBuyOrdersCountFlag(false);
        user1.setItemShowMinSellPriceFlag(false);
        user1.setItemsShowSellOrdersCountFlag(false);
        user1.setItemShowPictureFlag(false);
        user1.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user1.setNewManagersAreActiveFlag(false);
        user1.setManagingEnabledFlag(false);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        UserEntity user = new UserEntity();
        assertEquals(user, user);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setTelegramUser(new TelegramUserEntity());
        user1.getTelegramUser().setChatId("chatId");
        user1.setUbiAccountEntry(new UbiAccountEntryEntity());
        user1.getUbiAccountEntry().setEmail("email");
        user1.setItemFilters(List.of(new ItemFilterEntity()));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));
        user1.setPublicNotificationsEnabledFlag(false);
        user1.setPrivateNotificationsEnabledFlag(false);
        user1.setItemShowNameFlag(false);
        user1.setItemShowItemTypeFlag(false);
        user1.setItemShowMaxBuyPrice(false);
        user1.setItemShowBuyOrdersCountFlag(false);
        user1.setItemShowMinSellPriceFlag(false);
        user1.setItemsShowSellOrdersCountFlag(false);
        user1.setItemShowPictureFlag(false);
        user1.setItemShowAppliedFilters(List.of(new ItemFilterEntity()));
        user1.setNewManagersAreActiveFlag(false);
        user1.setManagingEnabledFlag(false);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);

        assertEquals(user1, user2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        UserEntity user = new UserEntity();
        assertNotEquals(null, user);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);

        UserEntity user2 = new UserEntity();
        user1.setId(2L);

        assertNotEquals(user1, user2);
    }
}