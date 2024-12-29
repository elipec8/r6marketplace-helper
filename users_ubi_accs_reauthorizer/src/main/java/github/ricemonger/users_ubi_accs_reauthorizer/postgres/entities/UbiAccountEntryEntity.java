package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Table(name = "ubi_account_authorization_entry")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UbiAccountEntryEntityId.class)
public class UbiAccountEntryEntity {
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserIdEntity user;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "encoded_password")
    private String encodedPassword;

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
    private UbiAccountStatsIdEntity ubiAccountStats;

    public String getUbiProfileId_() {
        return ubiAccountStats == null ? null : ubiAccountStats.getUbiProfileId();
    }

    public void setAuthorizationDTOFields(@NotNull AuthorizationDTO authDTO) {
        this.ubiSessionId = authDTO.getSessionId();
        this.ubiSpaceId = authDTO.getSpaceId();
        this.ubiAuthTicket = authDTO.getTicket();
        this.ubiRememberDeviceTicket = authDTO.getRememberDeviceTicket();
        this.ubiRememberMeTicket = authDTO.getRememberMeTicket();
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
