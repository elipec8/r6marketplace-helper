package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.entities.items.TagEntity;
import github.ricemonger.trades_manager.postgres.entities.manageable_users.ItemFilterEntity;
import github.ricemonger.trades_manager.postgres.services.entity_mappers.item.TagEntityMapper;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemFilterEntityMapperTest {
    @Autowired
    private ItemFilterEntityMapper itemFilterEntityMapper;
    @MockBean
    private TagEntityMapper tagEntityMapper;

    @Test
    public void createDTO_should_properly_map_dto() {
        TagEntity tagEntity = new TagEntity("value", "name", TagGroup.Rarity);

        ItemFilterEntity entity = new ItemFilterEntity();
        entity.setName("name");
        entity.setFilterType(FilterType.ALLOW);
        entity.setIsOwned(IsOwnedFilter.OWNED);
        entity.setItemNamePatterns("pattern1,pattern2");
        entity.setItemTypes("Charm,CharacterHeadgear");
        entity.setTags(Set.of(tagEntity));
        entity.setMinSellPrice(1);
        entity.setMaxBuyPrice(2);

        ItemFilter expected = new ItemFilter();
        expected.setName("name");
        expected.setFilterType(FilterType.ALLOW);
        expected.setIsOwned(IsOwnedFilter.OWNED);
        expected.setItemNamePatterns(List.of("pattern1", "pattern2"));
        expected.setItemTypes(List.of(ItemType.Charm, ItemType.CharacterHeadgear));
        expected.setTags(List.of(new Tag("value", "name", TagGroup.Rarity)));
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);

        when(tagEntityMapper.createDTO(same(tagEntity))).thenReturn(new Tag("value", "name", TagGroup.Rarity));

        ItemFilter result = itemFilterEntityMapper.createDTO(entity);

        System.out.println(result);

        assertEquals(expected, result);
    }
}