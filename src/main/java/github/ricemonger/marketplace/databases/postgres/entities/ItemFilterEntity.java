package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "item_filter_tags",
            joinColumns = {@JoinColumn(name = "item_filter_chat_id", referencedColumnName="chatId"),
                    @JoinColumn(name = "item_filter_name", referencedColumnName="name")},
            inverseJoinColumns = @JoinColumn(name = "tag_name", referencedColumnName="name"))
    private Set<TagEntity> tags = new HashSet<>();

    private Integer minPrice;

    private Integer maxPrice;

    private Integer minLastSoldPrice;

    private Integer maxLastSoldPrice;

    public ItemFilterEntity(String chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }
}
