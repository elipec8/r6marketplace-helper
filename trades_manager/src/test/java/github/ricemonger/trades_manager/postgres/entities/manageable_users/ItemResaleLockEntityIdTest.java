package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemResaleLockEntityIdTest {

    @Test
    public void hashCode_should_return_same_for_equal_ids(){
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId1");
        entity1.setBoughtIn24h(1);
        ItemIdEntity item1 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(entity1, item1);

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId1");
        entity2.setBoughtIn24h(2);
        ItemIdEntity item2 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(entity2, item2);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_ids(){
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId1");
        entity1.setBoughtIn24h(1);
        ItemIdEntity item1 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(entity1, item1);

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId1");
        entity2.setBoughtIn24h(2);
        ItemIdEntity item2 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(entity2, item2);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_ubiProfileId(){
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId1");
        ItemIdEntity item1 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(entity1, item1);

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId2");
        ItemIdEntity item2 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(entity2, item2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_items(){
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId1");
        ItemIdEntity item1 = new ItemIdEntity("itemId1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(entity1, item1);

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId1");
        ItemIdEntity item2 = new ItemIdEntity("itemId2");

        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(entity2, item2);

        assertNotEquals(id1, id2);
    }
}