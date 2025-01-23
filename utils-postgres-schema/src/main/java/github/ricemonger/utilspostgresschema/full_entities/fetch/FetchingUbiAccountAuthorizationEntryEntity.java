package github.ricemonger.utilspostgresschema.full_entities.fetch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "fetching_ubi_acount_authorization_entry")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FetchingUbiAccountAuthorizationEntryEntity {
    @Id
    @Column(name = "ubi_profile_id")
    private String ubiProfileId;

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

    @Override
    public int hashCode() {
        return Objects.hash(ubiProfileId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FetchingUbiAccountAuthorizationEntryEntity entity) {
            return Objects.equals(ubiProfileId, entity.ubiProfileId);
        }
        return false;
    }
}
