package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "helper_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUbiAccountEntryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private UbiAccountEntryEntity ubiAccountEntry;

    public UserUbiAccountEntryEntity(Long userId) {
        this.id = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserUbiAccountEntryEntity userEntity)) {
            return false;
        }
        return Objects.equals(id, userEntity.id);
    }
}
