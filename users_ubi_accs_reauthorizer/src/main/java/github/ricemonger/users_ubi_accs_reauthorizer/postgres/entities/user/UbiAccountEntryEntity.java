package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.user;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

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
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserIdEntity user;

    @Id
    private String email;

    private String encodedPassword;

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
}
