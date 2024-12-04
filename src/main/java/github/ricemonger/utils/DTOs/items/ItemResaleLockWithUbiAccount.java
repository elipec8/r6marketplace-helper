package github.ricemonger.utils.DTOs.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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
        return super.hashCode() + Objects.hashCode(ubiProfileId);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemResaleLockWithUbiAccount other)) {
            return false;
        }
        return super.equals(obj) && Objects.equals(ubiProfileId, other.ubiProfileId);
    }

    public String toString() {
        return "ItemResaleLockWithUbiAccount(ubiProfileId=" + ubiProfileId + ", itemId=" + getItemId() + ", expiresAt=" + getExpiresAt() + ")";
    }
}
