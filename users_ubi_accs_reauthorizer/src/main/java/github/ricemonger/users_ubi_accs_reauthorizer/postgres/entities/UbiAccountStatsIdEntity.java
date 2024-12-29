package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "ubi_account_stats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsIdEntity {
    @Id
    @Column(name = "ubi_profile_id")
    private String ubiProfileId;

    @Override
    public int hashCode() {
        return Objects.hash(ubiProfileId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountStatsIdEntity ubiAccountStatsEntity)) {
            return false;
        }
        return Objects.equals(this.ubiProfileId, ubiAccountStatsEntity.ubiProfileId);
    }
}
