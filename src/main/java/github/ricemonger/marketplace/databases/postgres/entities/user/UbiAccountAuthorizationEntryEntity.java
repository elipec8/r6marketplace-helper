package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.DTOs.UbiAccountAuthorizationDTO;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity(name = "ubi_account_authorization_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UbiAccountAuthorizationEntryEntityId.class)
public class UbiAccountAuthorizationEntryEntity {

    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    @Id
    private String email;

    private String encodedPassword;

    private String ubiSessionId;
    private String ubiSpaceId;
    @Column(columnDefinition = "TEXT")
    private String ubiAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiTwoFactorAuthTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberDeviceTicket;
    @Column(columnDefinition = "TEXT")
    private String ubiRememberMeTicket;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ubiProfileId", referencedColumnName = "ubiProfileId")
    private UbiAccountEntity ubiAccountStats;

    public UbiAccountAuthorizationEntryEntity(UserEntity user, UbiAccountEntity ubiAccountStats, UbiAccountAuthorizationDTO account) {
        this.user = user;
        this.email = account.getEmail();
        this.encodedPassword = account.getEncodedPassword();
        this.ubiAccountStats = ubiAccountStats;
        this.ubiSessionId = account.getUbiSessionId();
        this.ubiSpaceId = account.getUbiSpaceId();
        this.ubiAuthTicket = account.getUbiAuthTicket();
        this.ubiTwoFactorAuthTicket = account.getUbiTwoFactorAuthTicket();
        this.ubiRememberDeviceTicket = account.getUbiRememberDeviceTicket();
        this.ubiRememberMeTicket = account.getUbiRememberMeTicket();
    }

    public UbiAccountAuthorizationEntryWithTelegram toUbiAccountAuthorizationEntryWithTelegram() {
        UbiAccountAuthorizationEntryWithTelegram ubiAccountWithTelegram = new UbiAccountAuthorizationEntryWithTelegram();
        ubiAccountWithTelegram.setUbiAccountEntry(this.toUbiAccountAuthorizationEntry());
        ubiAccountWithTelegram.setChatId(this.user.getTelegramUser().getChatId());
        return ubiAccountWithTelegram;
    }

    public UbiAccountAuthorizationDTO toUbiAccountAuthorizationEntry() {
        UbiAccountAuthorizationDTO ubiAccountEntry = new UbiAccountAuthorizationDTO();
        ubiAccountEntry.setEmail(this.email);
        ubiAccountEntry.setEncodedPassword(this.encodedPassword);
        ubiAccountEntry.setUbiProfileId(this.ubiAccountStats.getUbiProfileId());
        ubiAccountEntry.setUbiSessionId(this.ubiSessionId);
        ubiAccountEntry.setUbiSpaceId(this.ubiSpaceId);
        ubiAccountEntry.setUbiAuthTicket(this.ubiAuthTicket);
        ubiAccountEntry.setUbiTwoFactorAuthTicket(this.ubiTwoFactorAuthTicket);
        ubiAccountEntry.setUbiRememberDeviceTicket(this.ubiRememberDeviceTicket);
        ubiAccountEntry.setUbiRememberMeTicket(this.ubiRememberMeTicket);
        return ubiAccountEntry;
    }
}
