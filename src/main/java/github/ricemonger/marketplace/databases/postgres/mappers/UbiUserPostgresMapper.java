package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.utils.dtos.UbiUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class UbiUserPostgresMapper {

    public UbiUserEntity toUbiUserEntity(UbiUser user) {
        UbiUserEntity entity = new UbiUserEntity();

        StringBuilder ownedItemsIds = new StringBuilder();

        if(user.getOwnedItemsIds()!=null) {

            for (String id : user.getOwnedItemsIds()) {
                ownedItemsIds.append(id).append(",");
            }
            try {
                ownedItemsIds.deleteCharAt(ownedItemsIds.length() - 1);
            } catch (StringIndexOutOfBoundsException e) {
                log.error("Owned items ids list is empty");
            }
        }

        entity.setChatId(user.getChatId());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setUbiProfileId(user.getUbiProfileId());
        entity.setUbiSessionId(user.getUbiSessionId());
        entity.setUbiSpaceId(user.getUbiSpaceId());
        entity.setUbiAuthTicket(user.getUbiAuthTicket());
        entity.setUbiTwoFactorAuthTicket(user.getUbiTwoFactorAuthTicket());
        entity.setUbiRememberDeviceTicket(user.getUbiRememberDeviceTicket());
        entity.setUbiRememberMeTicket(user.getUbiRememberMeTicket());
        entity.setOwnedItemsIds(ownedItemsIds.toString());

        return entity;
    }

    public Collection<UbiUser> toUbiUsers(Collection<UbiUserEntity> entities) {
        return entities.stream().map(this::toUbiUser).toList();
    }

    public UbiUser toUbiUser(UbiUserEntity ubiUserEntity) {
        UbiUser ubiUser = new UbiUser();

        List<String> ownedItemsIds = new ArrayList<>();
        if(ubiUserEntity.getOwnedItemsIds() != null) {
            String[] entityOwnedItemsIds = ubiUserEntity.getOwnedItemsIds().split(",");
            ownedItemsIds = new ArrayList<>(Arrays.asList(entityOwnedItemsIds));
        }

        ubiUser.setChatId(ubiUserEntity.getChatId());
        ubiUser.setEmail(ubiUserEntity.getEmail());
        ubiUser.setPassword(ubiUserEntity.getPassword());
        ubiUser.setUbiProfileId(ubiUserEntity.getUbiProfileId());
        ubiUser.setUbiSessionId(ubiUserEntity.getUbiSessionId());
        ubiUser.setUbiSpaceId(ubiUserEntity.getUbiSpaceId());
        ubiUser.setUbiAuthTicket(ubiUserEntity.getUbiAuthTicket());
        ubiUser.setUbiTwoFactorAuthTicket(ubiUserEntity.getUbiTwoFactorAuthTicket());
        ubiUser.setUbiRememberDeviceTicket(ubiUserEntity.getUbiRememberDeviceTicket());
        ubiUser.setUbiRememberMeTicket(ubiUserEntity.getUbiRememberMeTicket());
        ubiUser.setOwnedItemsIds(ownedItemsIds);

        return ubiUser;
    }
}
