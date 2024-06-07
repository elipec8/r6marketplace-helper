package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
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

    private List<String> rarityTags;

    private List<String> seasonTags;

    private List<String> operatorTags;

    private List<String> weaponTags;

    private List<String> eventTags;

    private List<String> esportsTags;

    private List<String> otherTags;

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
            this.itemTypes = Stream.of(itemTypes.split("[,|]")).map(s-> ItemType.valueOf(s.replaceAll("[^a-zA-Z1-9]+",""))).toList();
        }
    }

    public String getRarityTagsAsString() {
        return getListAsString(rarityTags);
    }

    public void setRarityTagsFromString(String rarityTags) {
        this.rarityTags = getListFromString(rarityTags);
    }

    public String getSeasonTagsAsString() {
        return getListAsString(seasonTags);
    }

    public void setSeasonTagsFromString(String seasonTags) {
        this.seasonTags = getListFromString(seasonTags);
    }

    public String getOperatorTagsAsString() {
        return getListAsString(operatorTags);
    }

    public void setOperatorTagsFromString(String operatorTags) {
        this.operatorTags = getListFromString(operatorTags);
    }

    public String getWeaponTagsAsString() {
        return getListAsString(weaponTags);
    }

    public void setWeaponTagsFromString(String weaponTags) {
        this.weaponTags = getListFromString(weaponTags);
    }

    public String getEventTagsAsString() {
        return getListAsString(eventTags);
    }

    public void setEventTagsFromString(String eventTags) {
        this.eventTags = getListFromString(eventTags);
    }

    public String getEsportsTagsAsString() {
        return getListAsString(esportsTags);
    }

    public void setEsportsTagsFromString(String esportsTags) {
        this.esportsTags = getListFromString(esportsTags);
    }

    public String getOtherTagsAsString() {
        return getListAsString(otherTags);
    }

    public void setOtherTagsFromString(String otherTags) {
        this.otherTags = getListFromString(otherTags);
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
}
