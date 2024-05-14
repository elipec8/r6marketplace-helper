package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.UbiUserRepository;
import github.ricemonger.utils.exceptions.AesPasswordEncoder;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UbiUserServiceTests {

    @SpyBean
    private UbiUserRepository ubiUserRepository;

    @MockBean
    private AesPasswordEncoder aesPasswordEncoder;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private UbiUserService ubiUserService;

    @BeforeEach
    public void setUp() {
        ubiUserRepository.deleteAll();
    }

    @AfterEach
    public void cleanUp() {
        ubiUserRepository.deleteAll();
    }

    @Test
    public void deleteByLinkedTelegramUserChatIdAndEmailShouldCallRepository() {
        String s = "s";

        String emailToRemove = "emailToRemove";

        ubiUserService.deleteByLinkedTelegramUserChatIdAndEmail(s, emailToRemove);

        verify(ubiUserRepository).deleteByLinkedTelegramUserChatIdAndEmail(s, emailToRemove);
    }

    @Test
    public void deleteAllByLinkedTelegramUserChatIdShouldCallRepository() {
        String s = "s";

        ubiUserService.deleteAllByLinkedTelegramUserChatId(s);

        verify(ubiUserRepository).deleteAllByLinkedTelegramUserChatId(s);
    }

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedListShouldGetDtoForEveryUserAndReturnEmptyListIfAllUsersValid() {
        List<UbiUserEntity> entities = new ArrayList<>();

        UbiUserEntity ubiUserEntity = new UbiUserEntity();
        ubiUserEntity.setEmail("email");
        ubiUserEntity.setPassword("password");

        entities.add(ubiUserEntity);
        entities.add(ubiUserEntity);
        entities.add(ubiUserEntity);

        when(ubiUserRepository.findAll()).thenReturn(entities);

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenReturn(new AuthorizationDTO());

        List<UbiUserEntity> result = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertEquals(0, result.size());

        verify(ubiUserRepository).findAll();

        verify(authorizationService,times(3)).getUserAuthorizationDTO(any(),any());
    }

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedListShouldGetDtoForEveryUserAndReturnInvalidUsers(){
        List<UbiUserEntity> entities = new ArrayList<>();

        UbiUserEntity ubiUserEntity = new UbiUserEntity();
        ubiUserEntity.setEmail("email");
        ubiUserEntity.setPassword("password");

        entities.add(ubiUserEntity);
        entities.add(ubiUserEntity);
        entities.add(ubiUserEntity);

        when(ubiUserRepository.findAll()).thenReturn(entities);

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenThrow(new UbiUserAuthorizationClientErrorException());

        List<UbiUserEntity> result = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertEquals(3, result.size());

        verify(ubiUserRepository).findAll();

        verify(authorizationService,times(3)).getUserAuthorizationDTO(any(),any());
    }

    @Test
    public void reauthorizeUserOrThrowShouldSetFieldsAndSaveEntity(){
        UbiUserEntity ubiUserEntity = new UbiUserEntity();
        ubiUserEntity.setEmail("email");
        ubiUserEntity.setPassword(aesPasswordEncoder.encode("password"));

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");
        authorizationDTO.setSessionId("sessionId");
        authorizationDTO.setTicket("ticket");
        authorizationDTO.setSpaceId("spaceId");
        authorizationDTO.setRememberMeTicket("rememberMeTicket");
        authorizationDTO.setRememberDeviceTicket("rememberDeviceTicket");
        authorizationDTO.setTwoFactorAuthenticationTicket("twoFactorAuthenticationTicket");

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenReturn(authorizationDTO);

        ubiUserService.reauthorizeUserOrThrow(ubiUserEntity);

        verify(authorizationService).getUserAuthorizationDTO(any(),any());

        verify(ubiUserRepository).save(ubiUserEntity);

        assertEquals("profileId", ubiUserEntity.getUbiProfileId());
        assertEquals("sessionId", ubiUserEntity.getUbiSessionId());
        assertEquals("ticket", ubiUserEntity.getUbiAuthTicket());
        assertEquals("spaceId", ubiUserEntity.getUbiSpaceId());
        assertEquals("rememberMeTicket", ubiUserEntity.getUbiRememberMeTicket());
        assertEquals("rememberDeviceTicket", ubiUserEntity.getUbiRememberDeviceTicket());
        assertEquals("twoFactorAuthenticationTicket", ubiUserEntity.getUbiTwoFactorAuthTicket());
    }

    @Test
    public void reauthorizeUserOrThrowShouldThrowIfInvalidUser(){
        UbiUserEntity ubiUserEntity = new UbiUserEntity();
        ubiUserEntity.setEmail("email");
        ubiUserEntity.setPassword(aesPasswordEncoder.encode("password"));

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenThrow(new UbiUserAuthorizationClientErrorException());

        Executable executable = () -> ubiUserService.reauthorizeUserOrThrow(ubiUserEntity);

        assertThrows(UbiUserAuthorizationClientErrorException.class, executable);
    }

    @Test
    public void createAndAuthorizeOrThrowForTelegramUserShouldCreateAndSaveUbiUser(){
        String email = "email";
        String password = "password";

        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenReturn(authorizationDTO);

        ubiUserService.createAndAuthorizeOrThrowForTelegramUser(telegramLinkedUserEntity, email, password);

        verify(authorizationService).getUserAuthorizationDTO(email, password);

        verify(ubiUserRepository).save(any());
    }

    @Test
    public void createAndAuthorizeOrThrowForTelegramUserShouldThrowForInvalidUser(){
        String email = "email";
        String password = "password";

        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();

        UbiUserEntity ubiUserEntity = new UbiUserEntity();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");
        authorizationDTO.setSessionId("sessionId");
        authorizationDTO.setTicket("ticket");
        authorizationDTO.setSpaceId("spaceId");
        authorizationDTO.setRememberMeTicket("rememberMeTicket");
        authorizationDTO.setRememberDeviceTicket("rememberDeviceTicket");
        authorizationDTO.setTwoFactorAuthenticationTicket("twoFactorAuthenticationTicket");

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenThrow(UbiUserAuthorizationClientErrorException.class);

        Executable executable = () -> ubiUserService.createAndAuthorizeOrThrowForTelegramUser(telegramLinkedUserEntity, email, password);

        assertThrows(UbiUserAuthorizationClientErrorException.class, executable);

        verify(authorizationService).getUserAuthorizationDTO(email, password);

        verify(ubiUserRepository,times(0)).save(ubiUserEntity);
    }
}