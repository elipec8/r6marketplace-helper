package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

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
public class UbiAccountStatsIdCreditAmountEntity {
    @Id
    private String ubiProfileId;

    private Integer creditAmount;

    public UbiAccountStatsIdCreditAmountEntity(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public boolean isEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsIdCreditAmountEntity entity) {
            return Objects.equals(this.ubiProfileId, entity.ubiProfileId);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UbiAccountStatsIdCreditAmountEntity entity) {

            return isEqual(entity) &&
                   Objects.equals(this.creditAmount, entity.creditAmount);
        }
        return false;
    }
}
