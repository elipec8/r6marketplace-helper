package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "item_resale_lock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemResaleLockEntityId.class)
public class ItemResaleLockEntity {
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")
    private UbiAccountStatsEntity ubiAccount;

    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemIdEntity item;

    private LocalDateTime expiresAt;

    public String getItemId_() {
        return item.getItemId();
    }

    public boolean isEqual(ItemResaleLockEntity itemResaleLockEntity) {
        if (this == itemResaleLockEntity) return true;

        boolean ubiAccountIsEqual =
                ubiAccount == null && itemResaleLockEntity.ubiAccount == null || (
                        ubiAccount != null && ubiAccount.isEqual(itemResaleLockEntity.ubiAccount));

        boolean itemIsEqual =
                item == null && itemResaleLockEntity.item == null || (
                        item != null && item.isEqual(itemResaleLockEntity.item));

        return ubiAccountIsEqual && itemIsEqual;
    }
}
