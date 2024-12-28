package github.ricemonger.trades_manager.postgres.entities.user_ubi_account_entry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "helper_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUbiAccountEntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private UbiAccountEntryEntity ubiAccountEntry;

    public UserUbiAccountEntryEntity(Long userId) {
        this.id = userId;
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof UserUbiAccountEntryEntity entity) {
            return Objects.equals(id, entity.id);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof UserUbiAccountEntryEntity entity) {
            return isEqual(entity) &&
                   ubiAccountEntry.isEqual(entity.ubiAccountEntry);
        }
        return false;
    }
}
