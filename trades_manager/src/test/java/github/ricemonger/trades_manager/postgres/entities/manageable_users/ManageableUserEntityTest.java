package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManageableManageableUserEntityTest {
    @Test
    public void equals_should_return_true_if_same() {
        ManageableUserEntity user = new ManageableUserEntity();
        assertEquals(user, user);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setUbiAccountEntry(new ManageableUserUbiAccountEntryEntity());
        user1.getUbiAccountEntry().setUser(new ManageableUserEntity(1L));
        user1.getUbiAccountEntry().setEmail("email");
        user1.getUbiAccountEntry().setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));
        user1.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));
        user1.setTradeByItemIdManagers(List.of(new TradeByItemIdManagerEntity()));

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);

        assertEquals(user1, user2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ManageableUserEntity user = new ManageableUserEntity();
        assertNotEquals(null, user);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        ManageableUserEntity user1 = new ManageableUserEntity(1L);

        ManageableUserEntity user2 = new ManageableUserEntity(2L);

        assertNotEquals(user1, user2);
    }


    @Test
    public void isFullyEqual_should_return_true_if_same() {
        ManageableUserEntity user = new ManageableUserEntity();
        assertTrue(user.isFullyEqual(user));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity1 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity1.setUser(new ManageableUserEntity(1L));
        tradeByFiltersManagerEntity1.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity1 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity1.setUser(new ManageableUserEntity(1L));
        tradeByItemIdManagerEntity1.setItem(new ItemIdEntity("itemId1"));

        ItemFilterEntity itemFilterEntity1 = new ItemFilterEntity();
        itemFilterEntity1.setUser(new ManageableUserEntity(1L));
        itemFilterEntity1.setName("name");

        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setUbiAccountEntry(new ManageableUserUbiAccountEntryEntity());
        user1.getUbiAccountEntry().setUser(new ManageableUserEntity(1L));
        user1.getUbiAccountEntry().setEmail("email");
        user1.getUbiAccountEntry().setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity2 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity2.setUser(new ManageableUserEntity(1L));
        tradeByFiltersManagerEntity2.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity2 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity2.setUser(new ManageableUserEntity(1L));
        tradeByItemIdManagerEntity2.setItem(new ItemIdEntity("itemId1"));

        ItemFilterEntity itemFilterEntity2 = new ItemFilterEntity();
        itemFilterEntity2.setUser(new ManageableUserEntity(1L));
        itemFilterEntity2.setName("name");

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setUbiAccountEntry(new ManageableUserUbiAccountEntryEntity());
        user2.getUbiAccountEntry().setUser(new ManageableUserEntity(1L));
        user2.getUbiAccountEntry().setEmail("email");
        user2.getUbiAccountEntry().setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));
        user2.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity2));
        user2.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity2));

        assertTrue(user1.isFullyEqual(user2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        TradeByFiltersManagerEntity tradeByFiltersManagerEntity1 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity1.setUser(new ManageableUserEntity(1L));
        tradeByFiltersManagerEntity1.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity1 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity1.setUser(new ManageableUserEntity(1L));
        tradeByItemIdManagerEntity1.setItem(new ItemIdEntity("itemId1"));

        ItemFilterEntity itemFilterEntity1 = new ItemFilterEntity();
        itemFilterEntity1.setUser(new ManageableUserEntity(1L));
        itemFilterEntity1.setName("name");

        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity1 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity1.setUser(new ManageableUserEntity(1L));
        ubiAccountEntryEntity1.setEmail("email");
        ubiAccountEntryEntity1.setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity2 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity2.setUser(new ManageableUserEntity(2L));
        tradeByFiltersManagerEntity2.setName("name");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity2 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity2.setUser(new ManageableUserEntity(2L));
        tradeByItemIdManagerEntity2.setItem(new ItemIdEntity("itemId1"));

        ItemFilterEntity itemFilterEntity2 = new ItemFilterEntity();
        itemFilterEntity2.setUser(new ManageableUserEntity(2L));
        itemFilterEntity2.setName("name");


        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity2 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity2.setUser(new ManageableUserEntity(2L));
        ubiAccountEntryEntity2.setEmail("email");
        ubiAccountEntryEntity2.setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));

        TradeByFiltersManagerEntity tradeByFiltersManagerEntity3 = new TradeByFiltersManagerEntity();
        tradeByFiltersManagerEntity3.setUser(new ManageableUserEntity(1L));
        tradeByFiltersManagerEntity3.setName("name1");

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity3 = new TradeByItemIdManagerEntity();
        tradeByItemIdManagerEntity3.setUser(new ManageableUserEntity(1L));
        tradeByItemIdManagerEntity3.setItem(new ItemIdEntity("itemId2"));

        ItemFilterEntity itemFilterEntity3 = new ItemFilterEntity();
        itemFilterEntity3.setUser(new ManageableUserEntity(1L));
        itemFilterEntity3.setName("name2");

        ManageableUserUbiAccountEntryEntity ubiAccountEntryEntity3 = new ManageableUserUbiAccountEntryEntity();
        ubiAccountEntryEntity3.setUser(new ManageableUserEntity(1L));
        ubiAccountEntryEntity3.setEmail("email1");
        ubiAccountEntryEntity3.setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId1"));

        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setUbiAccountEntry(ubiAccountEntryEntity1);
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setUbiAccountEntry(ubiAccountEntryEntity1);
        user2.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user2.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));

        user1.setId(2L);
        assertFalse(user1.isFullyEqual(user2));
        user1.setId(1L);
        user1.setUbiAccountEntry(ubiAccountEntryEntity2);
        assertFalse(user1.isFullyEqual(user2));
        user1.setUbiAccountEntry(ubiAccountEntryEntity3);
        assertFalse(user1.isFullyEqual(user2));
        user1.setUbiAccountEntry(ubiAccountEntryEntity1);
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity2));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity3));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity2));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity3));
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of());
        assertFalse(user1.isFullyEqual(user2));
        user1.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1));
    }
}