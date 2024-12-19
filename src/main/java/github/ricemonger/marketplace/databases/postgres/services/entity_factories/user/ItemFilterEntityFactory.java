package github.ricemonger.marketplace.databases.postgres.services.entity_factories.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_factories.item.TagEntityFactory;
import github.ricemonger.utils.DTOs.ItemFilter;
import github.ricemonger.utils.DTOs.items.Tag;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFilterEntityFactory {

    private final UserPostgresRepository userPostgresRepository;

    private final TagEntityFactory tagEntityFactory;


    public ItemFilterEntity createEntityForTelegramUser(String chatId, ItemFilter filter) {
        String name = filter.getName();
        FilterType filterType = filter.getFilterType();
        IsOwnedFilter isOwned = filter.getIsOwned();

        String itemNamePatterns;

        if (filter.getItemNamePatterns() == null || filter.getItemNamePatterns().isEmpty()) {
            itemNamePatterns = "";
        } else {
            itemNamePatterns = String.join(",", filter.getItemNamePatterns());
        }

        String itemTypes;

        if (filter.getItemTypes() == null || filter.getItemTypes().isEmpty()) {
            itemTypes = "";
        } else {
            itemTypes = filter.getItemTypes().stream().map(Enum::name).collect(Collectors.joining(","));
        }

        Set<TagEntity> tags;

        if (filter.getTags() == null || filter.getTags().isEmpty()) {
            tags = Set.of();
        } else {
            tags = filter.getTags().stream().map(tagEntityFactory::createEntity).collect(Collectors.toSet());
        }

        Integer minSellPrice = filter.getMinSellPrice();
        Integer maxBuyPrice = filter.getMaxBuyPrice();

        return new ItemFilterEntity(userPostgresRepository.findByTelegramUserChatId(chatId),
                name,
                filterType,
                isOwned,
                itemNamePatterns,
                itemTypes,
                tags,
                minSellPrice,
                maxBuyPrice);
    }

    public ItemFilter createDTO(ItemFilterEntity entity) {
        String name = entity.getName();
        FilterType filterType = entity.getFilterType();
        IsOwnedFilter isOwned = entity.getIsOwned();

        List<String> namePatterns = new ArrayList<>();
        if (entity.getItemNamePatterns() != null && !entity.getItemNamePatterns().isEmpty()) {
            namePatterns = Arrays.stream(entity.getItemNamePatterns().split("[,|]")).map(String::trim).toList();
        }

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
        }

        List<Tag> tags = new ArrayList<>();
        if (entity.getTags() != null && !entity.getTags().isEmpty()) {
            tags = entity.getTags().stream().map(tagEntityFactory::createDTO).toList();
        }

        Integer minSellPrice = entity.getMinSellPrice();
        Integer maxBuyPrice = entity.getMaxBuyPrice();

        return new ItemFilter(name, filterType, isOwned, namePatterns, itemTypes, tags, minSellPrice, maxBuyPrice);
    }
}
