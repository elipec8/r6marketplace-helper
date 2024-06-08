package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "item_filter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemFilterEntityId.class)
public class ItemFilterEntity {
    @Id
    private String chatId;

    @Id
    private String name;

    private FilterType filterType;

    private IsOwnedFilter isOwned;

    private String itemNamePatterns;

    private String itemTypes;

    private String rarityTags;

    private String seasonTags;

    private String operatorTags;

    private String weaponTags;

    private String eventTags;

    private String esportsTags;

    private String otherTags;

    private Integer minPrice;

    private Integer maxPrice;

    private Integer minLastSoldPrice;

    private Integer maxLastSoldPrice;
}
