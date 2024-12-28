package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Entity
@Table(name = "ubi_account_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsIdEntity {
    @Id
    private String ubiProfileId;

    public boolean isEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsIdEntity entity) {
            return Objects.equals(this.ubiProfileId, entity.ubiProfileId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        return isEqual(o);
    }
}
