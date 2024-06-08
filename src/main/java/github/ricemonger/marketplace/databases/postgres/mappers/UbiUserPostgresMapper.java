package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntity;
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

    public Collection<TelegramLinkedUbiUserEntity> mapUbiUserEntities(Collection<UbiUser> users) {
        if(users == null || users.isEmpty()) {
            return List.of();
        }
        else {
            return users.stream().map(this::mapUbiUserEntity).toList();
        }
    }

    public TelegramLinkedUbiUserEntity mapUbiUserEntity(UbiUser user) {
        TelegramLinkedUbiUserEntity entity = new TelegramLinkedUbiUserEntity();

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
        entity.setEncodedPassword(user.getEncodedPassword());
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

    public Collection<UbiUser> mapUbiUsers(Collection<TelegramLinkedUbiUserEntity> entities) {
        if(entities == null || entities.isEmpty()) {
            return List.of();
        }
        else {
            return entities.stream().map(this::mapUbiUser).toList();
        }
    }

    public UbiUser mapUbiUser(TelegramLinkedUbiUserEntity telegramLinkedUbiUserEntity) {
        UbiUser ubiUser = new UbiUser();

        List<String> ownedItemsIds = new ArrayList<>();
        if(telegramLinkedUbiUserEntity.getOwnedItemsIds() != null) {
            String[] entityOwnedItemsIds = telegramLinkedUbiUserEntity.getOwnedItemsIds().split(",");
            ownedItemsIds = new ArrayList<>(Arrays.asList(entityOwnedItemsIds));
        }

        ubiUser.setChatId(telegramLinkedUbiUserEntity.getChatId());
        ubiUser.setEmail(telegramLinkedUbiUserEntity.getEmail());
        ubiUser.setEncodedPassword(telegramLinkedUbiUserEntity.getEncodedPassword());
        ubiUser.setUbiProfileId(telegramLinkedUbiUserEntity.getUbiProfileId());
        ubiUser.setUbiSessionId(telegramLinkedUbiUserEntity.getUbiSessionId());
        ubiUser.setUbiSpaceId(telegramLinkedUbiUserEntity.getUbiSpaceId());
        ubiUser.setUbiAuthTicket(telegramLinkedUbiUserEntity.getUbiAuthTicket());
        ubiUser.setUbiTwoFactorAuthTicket(telegramLinkedUbiUserEntity.getUbiTwoFactorAuthTicket());
        ubiUser.setUbiRememberDeviceTicket(telegramLinkedUbiUserEntity.getUbiRememberDeviceTicket());
        ubiUser.setUbiRememberMeTicket(telegramLinkedUbiUserEntity.getUbiRememberMeTicket());
        ubiUser.setOwnedItemsIds(ownedItemsIds);

        return ubiUser;
    }
}
