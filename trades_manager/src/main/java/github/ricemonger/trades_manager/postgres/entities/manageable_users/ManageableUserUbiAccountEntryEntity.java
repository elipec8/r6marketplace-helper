package github.ricemonger.trades_manager.postgres.entities.manageable_users;

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
@IdClass(ManageableUserUbiAccountEntryEntityId.class)
public class ManageableUserUbiAccountEntryEntity {
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private ManageableUserEntity user;

    @Id
    private String email;

    private String ubiSessionId;
    private String ubiSpaceId;
    @Column(columnDefinition = "TEXT")
    private String ubiAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberDeviceTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberMeTicket;

    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")
    private UbiAccountStatsEntity ubiAccountStats;

    public Long getUserId_() {
        return user.getId();
    }

    public String getProfileId_() {
        return this.ubiAccountStats.getUbiProfileId();
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ManageableUserUbiAccountEntryEntity entity) {
            return user.isEqual(entity.user) &&
                   Objects.equals(email, entity.getEmail());
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ManageableUserUbiAccountEntryEntity entity) {
            return isEqual(entity) &&
                   Objects.equals(ubiSessionId, entity.getUbiSessionId()) &&
                   Objects.equals(ubiSpaceId, entity.getUbiSpaceId()) &&
                   Objects.equals(ubiAuthTicket, entity.getUbiAuthTicket()) &&
                   Objects.equals(ubiRememberDeviceTicket, entity.getUbiRememberDeviceTicket()) &&
                   Objects.equals(ubiRememberMeTicket, entity.getUbiRememberMeTicket()) &&
                   ubiAccountStats.isFullyEqual(entity.getUbiAccountStats());
        }
        return false;
    }
}
