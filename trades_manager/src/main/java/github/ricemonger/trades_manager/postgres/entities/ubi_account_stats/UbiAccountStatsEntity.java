package github.ricemonger.trades_manager.postgres.entities.ubi_account_stats;

import github.ricemonger.trades_manager.postgres.entities.manageable_users.ItemIdEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Entity
@Table(name = "ubi_account_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsEntity {
    @Id
    private String ubiProfileId;

    private Integer creditAmount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ubi_account_current_owned_items",
            joinColumns = {@JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")},
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemid"))
    private List<ItemIdEntity> ownedItems = new ArrayList<>();

    public UbiAccountStatsEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public boolean isEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsEntity entity) {
            return Objects.equals(this.ubiProfileId, entity.ubiProfileId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsEntity entity) {

            boolean ownedItemsIdsAreEqual = ownedItems == null && entity.ownedItems == null || (
                    this.ownedItems != null && entity.ownedItems != null &&
                    this.ownedItems.size() == entity.ownedItems.size() && this.ownedItems.stream().allMatch(item -> entity.ownedItems.stream().anyMatch(item::isEqual)));

            return isEqual(entity) &&
                   Objects.equals(this.creditAmount, entity.creditAmount) &&
                   ownedItemsIdsAreEqual;
        }
        return false;
    }
}
