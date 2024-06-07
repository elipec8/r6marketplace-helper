package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUbiUserEntity;
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

    public Collection<TelegramUbiUserEntity> mapUbiUserEntities(Collection<UbiUser> users) {
        if(users == null || users.isEmpty()) {
            return List.of();
        }
        else {
            return users.stream().map(this::mapUbiUserEntity).toList();
        }
    }

    public TelegramUbiUserEntity mapUbiUserEntity(UbiUser user) {
        TelegramUbiUserEntity entity = new TelegramUbiUserEntity();

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

    public Collection<UbiUser> mapUbiUsers(Collection<TelegramUbiUserEntity> entities) {
        if(entities == null || entities.isEmpty()) {
            return List.of();
        }
        else {
            return entities.stream().map(this::mapUbiUser).toList();
        }
    }

    public UbiUser mapUbiUser(TelegramUbiUserEntity telegramUbiUserEntity) {
        UbiUser ubiUser = new UbiUser();

        List<String> ownedItemsIds = new ArrayList<>();
        if(telegramUbiUserEntity.getOwnedItemsIds() != null) {
            String[] entityOwnedItemsIds = telegramUbiUserEntity.getOwnedItemsIds().split(",");
            ownedItemsIds = new ArrayList<>(Arrays.asList(entityOwnedItemsIds));
        }

        ubiUser.setChatId(telegramUbiUserEntity.getChatId());
        ubiUser.setEmail(telegramUbiUserEntity.getEmail());
        ubiUser.setEncodedPassword(telegramUbiUserEntity.getEncodedPassword());
        ubiUser.setUbiProfileId(telegramUbiUserEntity.getUbiProfileId());
        ubiUser.setUbiSessionId(telegramUbiUserEntity.getUbiSessionId());
        ubiUser.setUbiSpaceId(telegramUbiUserEntity.getUbiSpaceId());
        ubiUser.setUbiAuthTicket(telegramUbiUserEntity.getUbiAuthTicket());
        ubiUser.setUbiTwoFactorAuthTicket(telegramUbiUserEntity.getUbiTwoFactorAuthTicket());
        ubiUser.setUbiRememberDeviceTicket(telegramUbiUserEntity.getUbiRememberDeviceTicket());
        ubiUser.setUbiRememberMeTicket(telegramUbiUserEntity.getUbiRememberMeTicket());
        ubiUser.setOwnedItemsIds(ownedItemsIds);

        return ubiUser;
    }
}
