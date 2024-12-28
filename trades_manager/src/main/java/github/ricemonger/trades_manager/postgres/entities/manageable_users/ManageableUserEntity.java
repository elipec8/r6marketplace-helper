package github.ricemonger.trades_manager.postgres.entities.manageable_users;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "helper_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManageableUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private ManageableUserUbiAccountEntryEntity ubiAccountEntry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TradeByFiltersManagerEntity> tradeByFiltersManagers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TradeByItemIdManagerEntity> tradeByItemIdManagers = new ArrayList<>();

    private Boolean managingEnabledFlag = true;

    public ManageableUserEntity(Long userId) {
        this.id = userId;
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ManageableUserEntity entity) {
            return Objects.equals(id, entity.id);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ManageableUserEntity entity) {
            boolean tradeByFiltersManagersAreEqual = tradeByFiltersManagers == null && entity.tradeByFiltersManagers == null || (
                    tradeByFiltersManagers != null && entity.tradeByFiltersManagers != null &&
                    tradeByFiltersManagers.size() == entity.tradeByFiltersManagers.size() &&
                    tradeByFiltersManagers.stream().allMatch(tradeByFiltersManager -> entity.tradeByFiltersManagers.stream().anyMatch(tradeByFiltersManager::isEqual)));

            boolean tradeByItemIdManagersAreEqual = tradeByItemIdManagers == null && entity.tradeByItemIdManagers == null || (
                    tradeByItemIdManagers != null && entity.tradeByItemIdManagers != null &&
                    tradeByItemIdManagers.size() == entity.tradeByItemIdManagers.size() &&
                    tradeByItemIdManagers.stream().allMatch(tradeByItemIdManager -> entity.tradeByItemIdManagers.stream().anyMatch(tradeByItemIdManager::isEqual)));
            return isEqual(entity) &&
                   ubiAccountEntry.isEqual(entity.ubiAccountEntry) &&
                   tradeByFiltersManagersAreEqual &&
                   tradeByItemIdManagersAreEqual &&
                   Objects.equals(managingEnabledFlag, entity.managingEnabledFlag);
        }
        return false;
    }
}
