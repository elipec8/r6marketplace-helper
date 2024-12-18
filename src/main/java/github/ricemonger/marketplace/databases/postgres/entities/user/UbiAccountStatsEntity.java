package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity(name = "ubi_account_stats")
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
    private List<ItemEntity> ownedItems = new ArrayList<>();

    public UbiAccountStatsEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public UbiAccountStatsEntity(UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO, List<ItemEntity> existingItems) {
        this.ubiProfileId = ubiAccountStatsEntityDTO.getUbiProfileId();
        this.creditAmount = ubiAccountStatsEntityDTO.getCreditAmount();
        this.ownedItems = existingItems.stream().filter(item -> ubiAccountStatsEntityDTO.getOwnedItemsIds().contains(item.getItemId())).toList();
    }

    public UbiAccountStatsEntityDTO toUbiAccountStatsEntityDTO() {
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId(this.ubiProfileId);
        ubiAccountStatsEntityDTO.setCreditAmount(this.creditAmount);
        ubiAccountStatsEntityDTO.setOwnedItemsIds(this.ownedItems.stream().map(ItemEntity::getItemId).toList());
        return ubiAccountStatsEntityDTO;
    }
}
