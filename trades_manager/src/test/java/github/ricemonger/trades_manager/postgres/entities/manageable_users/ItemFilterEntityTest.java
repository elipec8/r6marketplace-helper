package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.TagEntity;
import github.ricemonger.utils.enums.FilterType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemFilterEntityTest {
    @Test
    public void equals_should_return_true_if_same() {
        ItemFilterEntity filter = new ItemFilterEntity();
        assertEquals(filter, filter);
    }

    @Test
    public void equals_should_return_true_if_equal_id_fields() {
        ItemFilterEntity filter1 = new ItemFilterEntity();
        filter1.setUser(new ManageableUserEntity(1L));
        filter1.setName("filterName");
        filter1.setFilterType(FilterType.ALLOW);
        filter1.setItemNamePatterns("pattern1");
        filter1.setItemTypes("type1");
        filter1.setTags(Set.of(new TagEntity()));
        filter1.setMinSellPrice(100);
        filter1.setMaxBuyPrice(200);

        ItemFilterEntity filter2 = new ItemFilterEntity();
        filter2.setUser(new ManageableUserEntity(1L));
        filter2.setName("filterName");
        filter2.setFilterType(FilterType.DENY);
        filter2.setItemNamePatterns("pattern2");
        filter2.setItemTypes("type2");
        filter2.setTags(null);
        filter2.setMinSellPrice(1000);
        filter2.setMaxBuyPrice(2000);

        assertEquals(filter1, filter2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemFilterEntity filter1 = new ItemFilterEntity();
        assertNotEquals(null, filter1);
    }

    @Test
    public void equals_should_return_false_if_different_id_fields() {
        ItemFilterEntity filter1 = new ItemFilterEntity();
        filter1.setUser(new ManageableUserEntity(1L));
        filter1.setName("filterName");

        ItemFilterEntity filter2 = new ItemFilterEntity();
        filter2.setUser(new ManageableUserEntity(1L));
        filter2.setName("filterName");

        filter1.setUser(new ManageableUserEntity(2L));
        assertNotEquals(filter1, filter2);
        filter1.setUser(new ManageableUserEntity(1L));
        filter1.setName("filterName2");
        assertNotEquals(filter1, filter2);
    }

    @Test
    public void getUserId_should_return_user_id() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        ItemFilterEntity filter = new ItemFilterEntity();
        filter.setUser(user);
        assertEquals(1L, filter.getUserId_());
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_equal_() {
        ItemFilterEntity filter1 = new ItemFilterEntity();
        filter1.setUser(new ManageableUserEntity(1L));
        filter1.setName("filterName");
        filter1.setFilterType(FilterType.ALLOW);
        filter1.setItemNamePatterns("pattern1");
        filter1.setItemTypes("type1");
        filter1.setTags(new HashSet<>(Set.of(new TagEntity())));
        filter1.setMinSellPrice(100);
        filter1.setMaxBuyPrice(200);

        ItemFilterEntity filter2 = new ItemFilterEntity();
        filter2.setUser(new ManageableUserEntity(1L));
        filter2.setName("filterName");
        filter2.setFilterType(FilterType.ALLOW);
        filter2.setItemNamePatterns("pattern1");
        filter2.setItemTypes("type1");
        filter2.setTags(new HashSet<>(Set.of(new TagEntity())));
        filter2.setMinSellPrice(100);
        filter2.setMaxBuyPrice(200);

        assertTrue(filter1.isFullyEqual(filter2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_false_if_not_equal_() {
        ItemFilterEntity filter1 = new ItemFilterEntity();
        filter1.setUser(new ManageableUserEntity(1L));
        filter1.setName("filterName1");
        filter1.setFilterType(FilterType.ALLOW);
        filter1.setItemNamePatterns("pattern1");
        filter1.setItemTypes("type1");
        filter1.setTags(new HashSet<>(Set.of(new TagEntity())));
        filter1.setMinSellPrice(100);
        filter1.setMaxBuyPrice(200);

        ItemFilterEntity filter2 = new ItemFilterEntity();
        filter2.setUser(new ManageableUserEntity(1L));
        filter2.setName("filterName1");
        filter2.setFilterType(FilterType.ALLOW);
        filter2.setItemNamePatterns("pattern1");
        filter2.setItemTypes("type1");
        filter2.setTags(new HashSet<>(Set.of(new TagEntity())));
        filter2.setMinSellPrice(100);
        filter2.setMaxBuyPrice(200);

        filter1.setName("filterName2");
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setName("filterName1");
        filter1.setFilterType(FilterType.DENY);
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setFilterType(FilterType.ALLOW);
        filter1.setItemNamePatterns("pattern2");
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setItemNamePatterns("pattern1");
        filter1.setItemTypes("type2");
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setItemTypes("type1");
        filter1.setTags(new HashSet<>());
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setTags(null);
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setTags(Set.of(new TagEntity()));
        filter1.setMinSellPrice(101);
        assertFalse(filter1.isFullyEqual(filter2));
        filter1.setMinSellPrice(100);
        filter1.setMaxBuyPrice(201);
        assertFalse(filter1.isFullyEqual(filter2));
    }
}