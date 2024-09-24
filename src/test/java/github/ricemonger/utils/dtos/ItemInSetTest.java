package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemInSetTest {
    @Test
    public void equals_should_compare_only_by_itemId() {
        ItemInSet item1 = new ItemInSet();
        item1.setItemId("itemId");
        item1.setName("name1");
        item1.setTags(List.of("tag1"));
        item1.setMaxBuyPrice(120);
        item1.setMinSellPrice(100);
        item1.setRarity(ItemRarity.UNCOMMON);
        item1.setLastSoldAt(new Date(0));
        item1.setLastSoldPrice(110);
        item1.setType(ItemType.WeaponSkin);
        item1.setAssetUrl("url1");

        ItemInSet item2 = new ItemInSet();
        item2.setItemId("itemId");
        item2.setName("name2");
        item2.setTags(List.of("tag2"));
        item2.setMaxBuyPrice(130);
        item2.setMinSellPrice(110);
        item2.setRarity(ItemRarity.RARE);
        item2.setLastSoldAt(new Date(1000));
        item2.setLastSoldPrice(120);
        item2.setType(ItemType.CharacterUniform);
        item2.setAssetUrl("url2");

        assertEquals(item1, item2);

        item2.setItemId("itemId2");

        assertNotEquals(item1, item2);
    }

    @Test
    public void hashCode_should_return_hashCode_of_itemId() {
        ItemInSet item = new ItemInSet();
        item.setItemId("itemId");

        assertEquals("itemId".hashCode(), item.hashCode());
    }
}