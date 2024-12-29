package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ubi_account_stats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStatsIdCreditAmountEntity {
    @Id
    @Column(name = "ubi_profile_id")
    private String ubiProfileId;

    @Column(name = "credit_amount")
    private Integer creditAmount;
}
