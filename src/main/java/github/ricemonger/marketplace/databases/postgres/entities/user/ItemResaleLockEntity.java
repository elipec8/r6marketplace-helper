package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity(name = "item_resale_lock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemResaleLockEntityId.class)
public class ItemResaleLockEntity {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")
    private UbiAccountStatsEntity ubiAccount;
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemEntity item;

    private LocalDateTime expiresAt;

    public ItemResaleLockWithUbiAccount toItemResaleLockWithUbiAccount() {
        return new ItemResaleLockWithUbiAccount(
                ubiAccount.getUbiProfileId(),
                item.getItemId(),
                expiresAt
        );
    }
}
