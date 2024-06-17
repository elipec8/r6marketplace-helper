package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilter {
    private String chatId;

    private String name;

    private FilterType filterType;

    private IsOwnedFilter isOwned;

    private List<String> itemNamePatterns;

    private List<ItemType> itemTypes;

    private List<Tag> tags = new ArrayList<>();

    private Integer minPrice;
    private Integer maxPrice;

    private Integer minLastSoldPrice;
    private Integer maxLastSoldPrice;

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
            return String.join(",", itemTypes.stream().map(ItemType::name).toList());
        }
    }

    public void setItemTypesFromString(String itemTypes) {
        if (itemTypes == null || itemTypes.isEmpty()) {
            this.itemTypes = List.of();
        } else {
            this.itemTypes = Stream.of(itemTypes.split("[,|]")).map(s -> ItemType.valueOf(s.replaceAll("[^a-zA-Z1-9]+", ""))).toList();
        }
    }

    private String getListAsString(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        } else {
            return String.join(",", tags.stream().map(String::trim).toList());
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

    public String toString() {
        String name = this.getName();
        String filterType = this.getFilterType().name();
        String isOwned = String.valueOf(this.getIsOwned());
        String itemNamePatterns = this.getItemNamePatternsAsString();
        String itemTypes = this.getItemTypesAsString();
        String rarityTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Rarity)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String seasonTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Season)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String operatorTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Operator)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String weaponTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Weapon)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String eventTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Event)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String esportsTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Esports_Team)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String otherTags = this.tags.stream().filter(tag -> tag.getTagGroup().equals(TagGroup.Other)).map(Tag::getName).reduce((s, s2) -> s + "," + s2).orElse("");
        String minPrice = String.valueOf(this.getMinPrice());
        String maxPrice = String.valueOf(this.getMaxPrice());
        String minLastSoldPrice = String.valueOf(this.getMinLastSoldPrice());
        String maxLastSoldPrice = String.valueOf(this.getMaxLastSoldPrice());

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Filter type: ").append(filterType).append("\n")
                .append("Is owned: ").append(isOwned).append("\n")
                .append("Item name pattern: ").append(itemNamePatterns).append("\n")
                .append("Item types: ").append(itemTypes).append("\n")
                .append("Rarity tags: ").append(rarityTags).append("\n")
                .append("Season tags: ").append(seasonTags).append("\n")
                .append("Operator tags: ").append(operatorTags).append("\n")
                .append("Weapon tags: ").append(weaponTags).append("\n")
                .append("Event tags: ").append(eventTags).append("\n")
                .append("Esports tags: ").append(esportsTags).append("\n")
                .append("Other tags: ").append(otherTags).append("\n")
                .append("Min price: ").append(minPrice).append("\n")
                .append("Max price: ").append(maxPrice).append("\n")
                .append("Min last sold price: ").append(minLastSoldPrice).append("\n")
                .append("Max last sold price: ").append(maxLastSoldPrice).append("\n");
        return sb.toString();
    }

    public void addTags(Collection<Tag> tagsFromNames) {
        this.tags.addAll(tagsFromNames);
    }
}
