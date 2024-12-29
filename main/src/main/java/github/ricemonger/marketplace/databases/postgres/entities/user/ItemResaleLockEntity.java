package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "item_resale_lock")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemResaleLockEntityId.class)
public class ItemResaleLockEntity {
    @Id
    @OneToOne
    @JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")
    private UbiAccountStatsEntity ubiAccount;

    @Id
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private ItemEntity item;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Override
    public int hashCode() {
        return Objects.hash(ubiAccount, item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemResaleLockEntity itemResaleLockEntity)) {
            return false;
        }
        return Objects.equals(ubiAccount, itemResaleLockEntity.ubiAccount) &&
               Objects.equals(item, itemResaleLockEntity.item);
    }
}
