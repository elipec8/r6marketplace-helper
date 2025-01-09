package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Entity
@Table(name = "ubi_account_authorization_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UbiAccountEntryEntityId.class)
public class UbiAccountEntryEntity {
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserUbiAccountEntryEntity user;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "ubi_session_id")
    private String ubiSessionId;
    @Column(name = "ubi_space_id")
    private String ubiSpaceId;
    @Column(columnDefinition = "TEXT", name = "ubi_auth_ticket")
    private String ubiAuthTicket;
    @Column(columnDefinition = "TEXT", name = "ubi_remember_device_ticket")
    private String ubiRememberDeviceTicket;
    @Column(columnDefinition = "TEXT", name = "ubi_remember_me_ticket")
    private String ubiRememberMeTicket;

    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ubi_profile_id", referencedColumnName = "ubi_profile_id")
    private UbiAccountStatsIdCreditAmountEntity ubiAccountStats;

    public String getProfileId_() {
        return this.ubiAccountStats == null ? null : this.ubiAccountStats.getUbiProfileId();
    }

    public Integer getCreditAmount_() {
        return this.ubiAccountStats == null ? null : this.ubiAccountStats.getCreditAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UbiAccountEntryEntity ubiAccountEntryEntity)) {
            return false;
        }
        return Objects.equals(user, ubiAccountEntryEntity.user) &&
               Objects.equals(email, ubiAccountEntryEntity.email);
    }
}
