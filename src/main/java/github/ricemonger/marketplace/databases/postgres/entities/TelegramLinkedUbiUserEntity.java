package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.utils.dtos.UbiUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Entity(name = "ubi_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramLinkedUbiUserEntityId.class)
public class TelegramLinkedUbiUserEntity {
    @Id
    private String chatId;

    @Id
    private String email;
    private String encodedPassword;

    private String ubiProfileId;
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

    @Column(columnDefinition = "TEXT")
    private String ownedItemsIds;

    public TelegramLinkedUbiUserEntity(UbiUser user) {
        this.chatId = user.getChatId();
        this.email = user.getEmail();
        this.encodedPassword = user.getEncodedPassword();
        this.ubiProfileId = user.getUbiProfileId();
        this.ubiSessionId = user.getUbiSessionId();
        this.ubiSpaceId = user.getUbiSpaceId();
        this.ubiAuthTicket = user.getUbiAuthTicket();
        this.ubiTwoFactorAuthTicket = user.getUbiTwoFactorAuthTicket();
        this.ubiRememberDeviceTicket = user.getUbiRememberDeviceTicket();
        this.ubiRememberMeTicket = user.getUbiRememberMeTicket();
        StringBuilder itemsIds = new StringBuilder();
        if (user.getOwnedItemsIds() != null) {
            for (String id : user.getOwnedItemsIds()) {
                itemsIds.append(id).append(",");
            }
            try {
                itemsIds.deleteCharAt(itemsIds.length() - 1);
            } catch (StringIndexOutOfBoundsException e) {
                log.error("Owned items ids list is empty");
            }
        }
        this.ownedItemsIds = itemsIds.toString();
    }

    public UbiUser toUbiUser() {
        UbiUser ubiUser = new UbiUser();
        ubiUser.setChatId(this.chatId);
        ubiUser.setEmail(this.email);
        ubiUser.setEncodedPassword(this.encodedPassword);
        ubiUser.setUbiProfileId(this.ubiProfileId);
        ubiUser.setUbiSessionId(this.ubiSessionId);
        ubiUser.setUbiSpaceId(this.ubiSpaceId);
        ubiUser.setUbiAuthTicket(this.ubiAuthTicket);
        ubiUser.setUbiTwoFactorAuthTicket(this.ubiTwoFactorAuthTicket);
        ubiUser.setUbiRememberDeviceTicket(this.ubiRememberDeviceTicket);
        ubiUser.setUbiRememberMeTicket(this.ubiRememberMeTicket);
        List<String> itemIds = new ArrayList<>();
        if (this.getOwnedItemsIds() != null) {
            String[] thisOwnedItemsIds = this.getOwnedItemsIds().split(",");
            itemIds = new ArrayList<>(Arrays.asList(thisOwnedItemsIds));
        }
        ubiUser.setOwnedItemsIds(itemIds);
        return ubiUser;
    }
}
