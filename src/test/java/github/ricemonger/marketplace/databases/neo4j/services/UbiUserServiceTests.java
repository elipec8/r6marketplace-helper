package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserNode;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
import github.ricemonger.marketplace.databases.neo4j.repositories.UbiUserNodeRepository;
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
    private UbiUserNodeRepository ubiUserNodeRepository;

    @MockBean
    private AesPasswordEncoder aesPasswordEncoder;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private UbiUserService ubiUserService;

    @BeforeEach
    public void setUp() {
        ubiUserNodeRepository.deleteAll();
    }

    @AfterEach
    public void cleanUp() {
        ubiUserNodeRepository.deleteAll();
    }

    @Test
    public void deleteByLinkedTelegramUserChatIdAndEmailShouldCallRepository() {
        String s = "s";

        String emailToRemove = "emailToRemove";

        ubiUserService.deleteByLinkedTelegramUserChatIdAndEmail(s, emailToRemove);

        verify(ubiUserNodeRepository).deleteByLinkedTelegramUserChatIdAndEmail(s, emailToRemove);
    }

    @Test
    public void deleteAllByLinkedTelegramUserChatIdShouldCallRepository() {
        String s = "s";

        ubiUserService.deleteAllByLinkedTelegramUserChatId(s);

        verify(ubiUserNodeRepository).deleteAllByLinkedTelegramUserChatId(s);
    }

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedListShouldGetDtoForEveryUserAndReturnEmptyListIfAllUsersValid() {
        List<UbiUserNode> entities = new ArrayList<>();

        UbiUserNode ubiUserNode = new UbiUserNode();
        ubiUserNode.setEmail("email");
        ubiUserNode.setPassword("password");

        entities.add(ubiUserNode);
        entities.add(ubiUserNode);
        entities.add(ubiUserNode);

        when(ubiUserNodeRepository.findAll()).thenReturn(entities);

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenReturn(new AuthorizationDTO());

        List<UbiUserNode> result = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertEquals(0, result.size());

        verify(ubiUserNodeRepository).findAll();

        verify(authorizationService,times(3)).getUserAuthorizationDTO(any(),any());
    }

    @Test
    public void reauthorizeAllUbiUsersAndGetUnauthorizedListShouldGetDtoForEveryUserAndReturnInvalidUsers(){
        List<UbiUserNode> entities = new ArrayList<>();

        UbiUserNode ubiUserNode = new UbiUserNode();
        ubiUserNode.setEmail("email");
        ubiUserNode.setPassword("password");

        entities.add(ubiUserNode);
        entities.add(ubiUserNode);
        entities.add(ubiUserNode);

        when(ubiUserNodeRepository.findAll()).thenReturn(entities);

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenThrow(new UbiUserAuthorizationClientErrorException());

        List<UbiUserNode> result = ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList();

        assertEquals(3, result.size());

        verify(ubiUserNodeRepository).findAll();

        verify(authorizationService,times(3)).getUserAuthorizationDTO(any(),any());
    }

    @Test
    public void reauthorizeUserOrThrowShouldSetFieldsAndSaveEntity(){
        UbiUserNode ubiUserNode = new UbiUserNode();
        ubiUserNode.setEmail("email");
        ubiUserNode.setPassword(aesPasswordEncoder.encode("password"));

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");
        authorizationDTO.setSessionId("sessionId");
        authorizationDTO.setTicket("ticket");
        authorizationDTO.setSpaceId("spaceId");
        authorizationDTO.setRememberMeTicket("rememberMeTicket");
        authorizationDTO.setRememberDeviceTicket("rememberDeviceTicket");
        authorizationDTO.setTwoFactorAuthenticationTicket("twoFactorAuthenticationTicket");

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenReturn(authorizationDTO);

        ubiUserService.reauthorizeUserOrThrow(ubiUserNode);

        verify(authorizationService).getUserAuthorizationDTO(any(),any());

        verify(ubiUserNodeRepository).save(ubiUserNode);

        assertEquals("profileId", ubiUserNode.getUbiProfileId());
        assertEquals("sessionId", ubiUserNode.getUbiSessionId());
        assertEquals("ticket", ubiUserNode.getUbiAuthTicket());
        assertEquals("spaceId", ubiUserNode.getUbiSpaceId());
        assertEquals("rememberMeTicket", ubiUserNode.getUbiRememberMeTicket());
        assertEquals("rememberDeviceTicket", ubiUserNode.getUbiRememberDeviceTicket());
        assertEquals("twoFactorAuthenticationTicket", ubiUserNode.getUbiTwoFactorAuthTicket());
    }

    @Test
    public void reauthorizeUserOrThrowShouldThrowIfInvalidUser(){
        UbiUserNode ubiUserNode = new UbiUserNode();
        ubiUserNode.setEmail("email");
        ubiUserNode.setPassword(aesPasswordEncoder.encode("password"));

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenThrow(new UbiUserAuthorizationClientErrorException());

        Executable executable = () -> ubiUserService.reauthorizeUserOrThrow(ubiUserNode);

        assertThrows(UbiUserAuthorizationClientErrorException.class, executable);
    }

    @Test
    public void createAndAuthorizeOrThrowForTelegramUserShouldCreateAndSaveUbiUser(){
        String email = "email";
        String password = "password";

        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenReturn(authorizationDTO);

        ubiUserService.createAndAuthorizeOrThrowForTelegramUser(telegramLinkedUserNode, email, password);

        verify(authorizationService).getUserAuthorizationDTO(email, password);

        verify(ubiUserNodeRepository).save(any());
    }

    @Test
    public void createAndAuthorizeOrThrowForTelegramUserShouldThrowForInvalidUser(){
        String email = "email";
        String password = "password";

        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();

        UbiUserNode ubiUserNode = new UbiUserNode();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setProfileId("profileId");
        authorizationDTO.setSessionId("sessionId");
        authorizationDTO.setTicket("ticket");
        authorizationDTO.setSpaceId("spaceId");
        authorizationDTO.setRememberMeTicket("rememberMeTicket");
        authorizationDTO.setRememberDeviceTicket("rememberDeviceTicket");
        authorizationDTO.setTwoFactorAuthenticationTicket("twoFactorAuthenticationTicket");

        when(authorizationService.getUserAuthorizationDTO(any(),any())).thenThrow(UbiUserAuthorizationClientErrorException.class);

        Executable executable = () -> ubiUserService.createAndAuthorizeOrThrowForTelegramUser(telegramLinkedUserNode, email, password);

        assertThrows(UbiUserAuthorizationClientErrorException.class, executable);

        verify(authorizationService).getUserAuthorizationDTO(email, password);

        verify(ubiUserNodeRepository,times(0)).save(ubiUserNode);
    }
}