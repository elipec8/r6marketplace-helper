package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockWithUbiAccount extends ItemResaleLock {
    private String ubiProfileId;

    public ItemResaleLockWithUbiAccount(String ubiProfileId, ItemResaleLock itemResaleLock) {
        super(itemResaleLock.getItemId(), itemResaleLock.getExpiresAt());
        this.ubiProfileId = ubiProfileId;
    }

    public ItemResaleLockWithUbiAccount(String ubiProfileId, String itemId, LocalDateTime expiresAt) {
        super(itemId, expiresAt);
        this.ubiProfileId = ubiProfileId;
    }

    public int hashCode() {
        return super.hashCode() + ubiProfileId.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ItemResaleLockWithUbiAccount other = (ItemResaleLockWithUbiAccount) obj;
        return super.equals(obj) && ubiProfileId.equals(other.ubiProfileId);
    }

    public String toString() {
        return "ItemResaleLockWithUbiAccount(ubiProfileId=" + ubiProfileId + ", itemId=" + getItemId() + ", expiresAt=" + getExpiresAt() + ")";
    }
}
