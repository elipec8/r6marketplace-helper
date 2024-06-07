package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.enums.ItemType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemFilterPostgresMapper {

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
        entity.setRarityTags(String.join(",", filter.getRarityTags()));
        entity.setSeasonTags(String.join(",", filter.getSeasonTags()));
        entity.setOperatorTags(String.join(",", filter.getOperatorTags()));
        entity.setWeaponTags(String.join(",", filter.getWeaponTags()));
        entity.setEventTags(String.join(",", filter.getEventTags()));
        entity.setEsportsTags(String.join(",", filter.getEsportsTags()));
        entity.setOtherTags(String.join(",", filter.getOtherTags()));
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

        List<ItemType> itemTypes;
        if (entity.getItemTypes() != null) {
            itemTypes = Arrays.stream(entity.getItemTypes().split("[,|]")).map(ItemType::valueOf).toList();
        } else {
            itemTypes = new ArrayList<>();
        }
        filter.setItemTypes(itemTypes);

        filter.setRarityTags(mapStringToList(entity.getRarityTags()));
        filter.setSeasonTags(mapStringToList(entity.getSeasonTags()));
        filter.setOperatorTags(mapStringToList(entity.getOperatorTags()));
        filter.setWeaponTags(mapStringToList(entity.getWeaponTags()));
        filter.setEventTags(mapStringToList(entity.getEventTags()));
        filter.setEsportsTags(mapStringToList(entity.getEsportsTags()));
        filter.setOtherTags(mapStringToList(entity.getOtherTags()));
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
