package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsEntityDTO {
    private String ubiProfileId;

    private Integer creditAmount;

    private List<String> ownedItemsIds = new ArrayList<>();

    public UbiAccountStatsEntityDTO(UbiAccountStats ubiAccountStats) {
        this.ubiProfileId = ubiAccountStats.getUbiProfileId();
        this.creditAmount = ubiAccountStats.getCreditAmount();
        this.ownedItemsIds = ubiAccountStats.getOwnedItemsIds();
    }

    public UbiAccountStatsEntityDTO(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public int hashCode() {
        return ubiProfileId.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO)) {
            return false;
        }
        return ubiAccountStatsEntityDTO.ubiProfileId.equals(ubiProfileId);
    }

    public boolean isFullyEqual(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO)) {
            return false;
        }

        boolean ownedItemsIdsEqual = ownedItemsIds == null && ubiAccountStatsEntityDTO.ownedItemsIds == null || (
                ownedItemsIds != null && ubiAccountStatsEntityDTO.ownedItemsIds != null &&
                ownedItemsIds.size() == ubiAccountStatsEntityDTO.ownedItemsIds.size() &&
                new HashSet<>(ownedItemsIds).containsAll(ubiAccountStatsEntityDTO.ownedItemsIds));

        return ubiAccountStatsEntityDTO.ubiProfileId.equals(ubiProfileId) &&
               ubiAccountStatsEntityDTO.creditAmount.equals(creditAmount) &&
               ownedItemsIdsEqual;
    }
}
