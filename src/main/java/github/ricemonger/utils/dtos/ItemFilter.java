package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilter {
    private String name;

    private FilterType filterType;

    private IsOwnedFilter isOwned;

    private List<String> itemNamePatterns;

    private List<ItemType> itemTypes;

    private List<Tag> tags = new ArrayList<>();

    private Integer minSellPrice;
    private Integer maxBuyPrice;

    private Integer minLastSoldPrice;
    private Integer maxLastSoldPrice;

    public static Collection<Item> filterItems(Collection<Item> items, Collection<ItemFilter> filters) {
        if (filters == null || filters.isEmpty()) {
            return items;
        } else {
            List<ItemFilter> allowedFilters =
                    filters.stream().filter(filter -> filter.getFilterType() != null && filter.getFilterType().equals(FilterType.ALLOW)).toList();
            List<ItemFilter> deniedFilters =
                    filters.stream().filter(filter -> filter.getFilterType() != null && filter.getFilterType().equals(FilterType.DENY)).toList();

            Set<Item> result;

            if (allowedFilters.isEmpty()) {
                result = new HashSet<>(items);
            } else {
                result = new HashSet<>();
                for (ItemFilter filter : allowedFilters) {
                    result.addAll(filter.filterItems(items));
                }
            }

            if (deniedFilters.isEmpty()) {
                return result;
            } else {
                List<Item> deniedItems = new ArrayList<>();
                for (ItemFilter filter : deniedFilters) {
                    deniedItems.addAll(filter.filterItems(items));
                }
                deniedItems.forEach(result::remove);
            }
            return result;
        }
    }

    public Collection<Item> filterItems(Collection<Item> items) {
        return items.stream()
                .filter(item -> this.itemNamePatterns == null || this.itemNamePatterns.isEmpty() || this.itemNamePatterns.stream().anyMatch(s -> item.getName().toLowerCase().contains(s.toLowerCase())))
                .filter(item -> this.itemTypes == null || this.itemTypes.isEmpty() || this.itemTypes.contains(item.getType()))
                .filter(item -> this.tags == null || this.tags.isEmpty() || this.tags.stream().anyMatch(tag -> item.getTags().contains(tag.getValue())))
                .filter(item -> this.minSellPrice == null || item.getMinSellPrice() >= this.minSellPrice)
                .filter(item -> this.maxBuyPrice == null || item.getMaxBuyPrice() <= this.maxBuyPrice)
                .filter(item -> this.minLastSoldPrice == null || item.getLastSoldPrice() >= this.minLastSoldPrice)
                .filter(item -> this.maxLastSoldPrice == null || item.getLastSoldPrice() <= this.maxLastSoldPrice)
                .toList();
    }

    public String getItemNamePatternsAsString() {
        return getListAsString(itemNamePatterns);
    }

    public void setItemNamePatternsFromString(String itemNamePatterns) {
        this.itemNamePatterns = getListFromString(itemNamePatterns);
    }

    public String getItemTypesAsString() {
        if (itemTypes == null || itemTypes.isEmpty()) {
            return "";
        } else {
            return String.join(", ", itemTypes.stream().map(ItemType::name).toList());
        }
    }

    public void setItemTypesFromString(String itemTypes) {
        if (itemTypes == null || itemTypes.isEmpty()) {
            this.itemTypes = List.of();
        } else {
            this.itemTypes = Stream.of(itemTypes.split("[,|]")).map(s -> ItemType.valueOf(s.replaceAll("[^a-zA-Z1-9]+", "").trim())).toList();
        }
    }

    public void addTags(Collection<Tag> tagsFromNames) {
        this.tags.addAll(tagsFromNames);
    }

    public String toString() {
        String name = this.name;
        String filterType = this.filterType == null ? null : this.filterType.name();
        String isOwned = String.valueOf(this.isOwned);
        String itemNamePatterns = this.getItemNamePatternsAsString();
        String itemTypes = this.getItemTypesAsString();
        List<Tag> tagsList = tags;
        if (tags == null) {
            tagsList = new ArrayList<>();
        }
        String rarityTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Rarity)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String seasonTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Season)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String operatorTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Operator)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String weaponTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Weapon)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String eventTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Event)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String esportsTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Esports_Team)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String otherTags = tagsList.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Other)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String minPrice = String.valueOf(this.minSellPrice);
        String maxPrice = String.valueOf(this.maxBuyPrice);
        String minLastSoldPrice = String.valueOf(this.minLastSoldPrice);
        String maxLastSoldPrice = String.valueOf(this.maxLastSoldPrice);

        String sb = "Name: " + name + "\n" +
                    "Filter type: " + filterType + "\n" +
                    "Is owned: " + isOwned + "\n" +
                    "Item name pattern: " + itemNamePatterns + "\n" +
                    "Item types: " + itemTypes + "\n" +
                    "Rarity tags: " + rarityTags + "\n" +
                    "Season tags: " + seasonTags + "\n" +
                    "Operator tags: " + operatorTags + "\n" +
                    "Weapon tags: " + weaponTags + "\n" +
                    "Event tags: " + eventTags + "\n" +
                    "Esports tags: " + esportsTags + "\n" +
                    "Other tags: " + otherTags + "\n" +
                    "Min price: " + minPrice + "\n" +
                    "Max price: " + maxPrice + "\n" +
                    "Min last sold price: " + minLastSoldPrice + "\n" +
                    "Max last sold price: " + maxLastSoldPrice + "\n";
        return sb;
    }

    private String getListAsString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        } else {
            return String.join(", ", list.stream().map(String::trim).toList());
        }
    }

    private List<String> getListFromString(String string) {
        if (string == null || string.isEmpty()) {
            return List.of();
        } else {
            String[] split = string.trim().split("[,|]");

            return Arrays.stream(split).map(String::trim).toList();
        }
    }
}
