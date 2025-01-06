package github.ricemonger.utilspostgresschema.ids.user;

import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TradeByFiltersManagerEntityIdTest {
    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowItemTypeFlag(true);
        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowItemTypeFlag(false);


        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user1, "filterName");
        TradeByFiltersManagerEntityId id2 = new TradeByFiltersManagerEntityId(user2, "filterName");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user1, "filterName");
        TradeByFiltersManagerEntityId id2 = new TradeByFiltersManagerEntityId(user2, "filterName");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_names() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user, "filterName1");
        TradeByFiltersManagerEntityId id2 = new TradeByFiltersManagerEntityId(user, "filterName2");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowItemTypeFlag(true);
        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowItemTypeFlag(false);


        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user1, "filterName");
        TradeByFiltersManagerEntityId id2 = new TradeByFiltersManagerEntityId(user2, "filterName");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user1, "filterName");
        TradeByFiltersManagerEntityId id2 = new TradeByFiltersManagerEntityId(user2, "filterName");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_names() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user, "filterName1");
        TradeByFiltersManagerEntityId id2 = new TradeByFiltersManagerEntityId(user, "filterName2");

        assertNotEquals(id1, id2);
    }


    @Test
    public void equals_should_return_false_for_null() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user, "filterName");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        TradeByFiltersManagerEntityId id1 = new TradeByFiltersManagerEntityId(user, "filterName");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}