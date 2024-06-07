package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUbiUserEntity;
import github.ricemonger.utils.dtos.UbiUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiUserPostgresMapperTest {

    private final UbiUser UBI_USER = new UbiUser(
            "chatId",
            "email",
            "password",
            "ubiProfileId",
            "ubiSessionId",
            "ubiSpaceId",
            "ubiAuthToken",
            "ubiTwoFactorAuthenticationToken",
            "ubiRememberDeviceToken",
            "ubiRememberMeToken",
            new ArrayList<>(List.of("1", "2", "3")));

    private final TelegramUbiUserEntity UBI_USER_ENTITY = new TelegramUbiUserEntity(
            "chatId",
            "email",
            "password",
            "ubiProfileId",
            "ubiSessionId",
            "ubiSpaceId",
            "ubiAuthToken",
            "ubiTwoFactorAuthenticationToken",
            "ubiRememberDeviceToken",
            "ubiRememberMeToken",
            "1,2,3");

    @SpyBean
    private UbiUserPostgresMapper ubiUserPostgresMapper;

    @Test
    public void mapUbiUserEntity_should_throw_if_null(){
        assertThrows(NullPointerException.class, () -> ubiUserPostgresMapper.mapUbiUserEntity(null));
    }

    @Test
    public void mapUbiUserEntity_should_not_throw_if_empty(){
        assertDoesNotThrow(() -> ubiUserPostgresMapper.mapUbiUserEntity(new UbiUser()));
    }

    @Test
    public void mapUbiUserEntity_should_map_ubi_users_entity(){
        assertTrue(ubiUserEntitiesAreEqual(UBI_USER_ENTITY, ubiUserPostgresMapper.mapUbiUserEntity(UBI_USER)));
    }

    @Test
    public void mapUbiUser_should_throw_if_null(){
        assertThrows(NullPointerException.class, () -> ubiUserPostgresMapper.mapUbiUser(null));
    }

    @Test
    public void mapUbiUser_should_return_empty_list_if_empty(){
        assertEquals(List.of(), ubiUserPostgresMapper.mapUbiUsers(List.of()));
    }

    @Test
    public void mapUbiUsers_should_map_ubi_user_and_call_map_for_each_object(){
        assertTrue(ubiUsersAreEqual(UBI_USER,new ArrayList<>(ubiUserPostgresMapper.mapUbiUsers(List.of(UBI_USER_ENTITY))).get(0)));

        verify(ubiUserPostgresMapper).mapUbiUser(UBI_USER_ENTITY);
    }

    @Test
    public void mapUbiUser_should_throw_if_null(){
        assertThrows(NullPointerException.class, () -> ubiUserPostgresMapper.mapUbiUser(null));
    }

    @Test
    public void mapUbiUser_should_not_throw_if_empty(){
        assertDoesNotThrow(() -> ubiUserPostgresMapper.mapUbiUser(new TelegramUbiUserEntity()));
    }

    @Test
    public void mapUbiUser_should_map_ubi_user(){
        assertTrue(ubiUsersAreEqual(UBI_USER, ubiUserPostgresMapper.mapUbiUser(UBI_USER_ENTITY)));
    }

    private boolean ubiUserEntitiesAreEqual(TelegramUbiUserEntity entity1, TelegramUbiUserEntity entity2){
        return entity1.getChatId().equals(entity2.getChatId()) &&
               entity1.getEmail().equals(entity2.getEmail()) &&
               entity1.getEncodedPassword().equals(entity2.getEncodedPassword()) &&
               entity1.getUbiProfileId().equals(entity2.getUbiProfileId()) &&
               entity1.getUbiSessionId().equals(entity2.getUbiSessionId()) &&
               entity1.getUbiSpaceId().equals(entity2.getUbiSpaceId()) &&
               entity1.getUbiAuthTicket().equals(entity2.getUbiAuthTicket()) &&
               entity1.getUbiRememberDeviceTicket().equals(entity2.getUbiRememberDeviceTicket()) &&
               entity1.getUbiRememberMeTicket().equals(entity2.getUbiRememberMeTicket()) &&
               entity1.getOwnedItemsIds().equals(entity2.getOwnedItemsIds());
    }

    private boolean ubiUsersAreEqual(UbiUser user1, UbiUser user2){
        return user1.getChatId().equals(user2.getChatId()) &&
               user1.getEmail().equals(user2.getEmail()) &&
               user1.getEncodedPassword().equals(user2.getEncodedPassword()) &&
               user1.getUbiProfileId().equals(user2.getUbiProfileId()) &&
               user1.getUbiSessionId().equals(user2.getUbiSessionId()) &&
               user1.getUbiSpaceId().equals(user2.getUbiSpaceId()) &&
               user1.getUbiAuthTicket().equals(user2.getUbiAuthTicket()) &&
               user1.getUbiRememberDeviceTicket().equals(user2.getUbiRememberDeviceTicket()) &&
               user1.getUbiRememberMeTicket().equals(user2.getUbiRememberMeTicket()) &&
               user1.getOwnedItemsIds().equals(user2.getOwnedItemsIds());
    }
}