package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

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

    public boolean isEqual(ItemResaleLockEntity itemResaleLockEntity) {
        if (this == itemResaleLockEntity) return true;
        return ubiAccount.isEqual(itemResaleLockEntity.ubiAccount) &&
               item.isEqual(itemResaleLockEntity.item);
    }
}
