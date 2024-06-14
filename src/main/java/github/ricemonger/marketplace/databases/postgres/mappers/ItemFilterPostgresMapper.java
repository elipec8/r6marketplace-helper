package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.enums.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFilterPostgresMapper {

    private final TagPostgresMapper tagPostgresMapper;

    public Collection<ItemFilterEntity> mapItemFilterEntities(Collection<ItemFilter> filters) {
        if (filters == null || filters.isEmpty()) {
            return List.of();
        } else {
            return filters.stream().map(this::mapItemFilterEntity).collect(Collectors.toList());
        }
    }

    public ItemFilterEntity mapItemFilterEntity(ItemFilter filter) {
        ItemFilterEntity entity = new ItemFilterEntity();

        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId(filter.getChatId());
        entity.setChatId(filter.getChatId());

        entity.setName(filter.getName());
        entity.setFilterType(filter.getFilterType());
        entity.setIsOwned(filter.getIsOwned());
        entity.setItemNamePatterns(String.join(",", filter.getItemNamePatterns()));
        entity.setItemTypes(filter.getItemTypes().stream().map(Enum::name).collect(Collectors.joining(",")));
        entity.setTags(new HashSet<>(tagPostgresMapper.mapTagEntities(filter.getTags())));
        entity.setMinPrice(filter.getMinPrice());
        entity.setMaxPrice(filter.getMaxPrice());
        entity.setMinLastSoldPrice(filter.getMinLastSoldPrice());
        entity.setMaxLastSoldPrice(filter.getMaxLastSoldPrice());

        return entity;
    }

    public Collection<ItemFilter> mapItemFilters(Collection<ItemFilterEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        } else {
            return entities.stream().map(this::mapItemFilter).collect(Collectors.toList());
        }
    }

    public ItemFilter mapItemFilter(ItemFilterEntity entity) {
        ItemFilter filter = new ItemFilter();

        filter.setChatId(entity.getChatId());
        filter.setName(entity.getName());
        filter.setFilterType(entity.getFilterType());
        filter.setIsOwned(entity.getIsOwned());
        filter.setItemNamePatterns(mapStringToList(entity.getItemNamePatterns()));

        List<ItemType> itemTypes = new ArrayList<>();
        if (entity.getItemTypes() != null && !entity.getItemTypes().isEmpty()) {

            String[] split = entity.getItemTypes().split("[,|]");

            for (String s : split) {
                try {
                    itemTypes.add(ItemType.valueOf(s));
                } catch (IllegalArgumentException e) {
                    log.error("Unknown item type: " + s);
                }
            }

        } else {
            itemTypes = new ArrayList<>();
        }
        filter.setItemTypes(itemTypes);

        filter.setTags(new ArrayList<>(tagPostgresMapper.mapTags(entity.getTags())));
        filter.setMinPrice(entity.getMinPrice());
        filter.setMaxPrice(entity.getMaxPrice());
        filter.setMinLastSoldPrice(entity.getMinLastSoldPrice());
        filter.setMaxLastSoldPrice(entity.getMaxLastSoldPrice());

        return filter;
    }

    private List<String> mapStringToList(String tags) {
        if (tags != null) {
            return Arrays.stream(tags.split("[,|]")).toList();
        } else {
            return new ArrayList<>();
        }
    }
}
