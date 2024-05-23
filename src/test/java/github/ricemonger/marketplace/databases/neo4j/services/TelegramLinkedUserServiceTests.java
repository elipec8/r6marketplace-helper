package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramInputValuesNode;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserNode;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramInputValuesNodeRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramLinkedUserNodeRepository;
import github.ricemonger.marketplace.databases.neo4j.repositories.UbiUserNodeRepository;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.exceptions.AesPasswordEncoder;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramLinkedUserServiceTests {

    @SpyBean
    private TelegramLinkedUserNodeRepository telegramLinkedUserNodeRepository;

    @SpyBean
    private TelegramInputValuesNodeRepository telegramInputValuesNodeRepository;

    @Autowired
    private TelegramLinkedUserService telegramLinkedUserService;

    @SpyBean
    private UbiUserService ubiUserService;

    @Autowired
    private UbiUserNodeRepository ubiUserNodeRepository;

    @SpyBean
    private AesPasswordEncoder aesPasswordEncoder;

    @MockBean
    private AuthorizationService authorizationService;

    @BeforeEach
    public void setUp() {
        telegramLinkedUserNodeRepository.deleteAll();
        telegramInputValuesNodeRepository.deleteAll();
        ubiUserNodeRepository.deleteAll();
    }

    @AfterEach
    public void cleanUp() {
        telegramLinkedUserNodeRepository.deleteAll();
        telegramInputValuesNodeRepository.deleteAll();
        ubiUserNodeRepository.deleteAll();
    }

    @Test
    void isTelegramUserRegisteredShouldReturnTrueIfExists() {
        when(telegramLinkedUserNodeRepository.existsById("123")).thenReturn(true);

        assertTrue(telegramLinkedUserService.isTelegramUserRegistered(123L));

        verify(telegramLinkedUserNodeRepository).existsById("123");
    }

    @Test
    void isTelegramUserRegisteredFalseShouldReturnFalseIfDoesntExist() {
        when(telegramLinkedUserNodeRepository.existsById("123")).thenReturn(false);

        assertFalse(telegramLinkedUserService.isTelegramUserRegistered(123L));

        verify(telegramLinkedUserNodeRepository).existsById("123");
    }

    @Test
    void registerTelegramUserShouldSaveInRepositoryIfDoesntExist() {
        when(telegramLinkedUserNodeRepository.existsById("123")).thenReturn(false);

        telegramLinkedUserService.registerTelegramUser(123L);

        verify(telegramLinkedUserNodeRepository).save(any());
    }

    @Test
    public void registerTelegramUserShouldThrowExceptionIfAlreadyExists() {
        when(telegramLinkedUserNodeRepository.existsById("123")).thenReturn(true);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramLinkedUserService.registerTelegramUser(123L));
    }

    @Test
    public void setUserNextInputOrThrowShouldSetInputStateIfUserExistsState() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        telegramLinkedUserService.setUserNextInputState(123L, InputState.CREDENTIALS_FULL_OR_EMAIL);

        assertEquals(InputState.CREDENTIALS_FULL_OR_EMAIL, telegramLinkedUserNode.getInputState());
        verify(telegramLinkedUserNodeRepository).save(telegramLinkedUserNode);
    }

    @Test
    public void setUserNextInputOrThrowShouldThrowExceptionIfUserDoesntExistState() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.setUserNextInputState(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void setUserNextInputGroupOrThrowShouldSetInputGroupIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        telegramLinkedUserService.setUserNextInputGroup(123L, InputGroup.CREDENTIALS_ADD);

        assertEquals(InputGroup.CREDENTIALS_ADD, telegramLinkedUserNode.getInputGroup());
        verify(telegramLinkedUserNodeRepository).save(telegramLinkedUserNode);
    }

    @Test
    public void setUserNextInputGroupOrThrowShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.setUserNextInputGroup(123L, InputGroup.CREDENTIALS_ADD));
    }

    @Test
    public void getUserInputStateShouldReturnStateIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertEquals(InputState.CREDENTIALS_FULL_OR_EMAIL, telegramLinkedUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputStateShouldReturnBaseIfUserExistsAndStateIsNull() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertEquals(InputState.BASE, telegramLinkedUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputStateShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputGroupShouldReturnGroupIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setInputGroup(InputGroup.CREDENTIALS_ADD);
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertEquals(InputGroup.CREDENTIALS_ADD, telegramLinkedUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroupShouldReturnBaseIfUserExistsAndGroupIsNull() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertEquals(InputGroup.BASE, telegramLinkedUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroupShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputByStateOrNullShouldReturnInputIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setInputValues(List.of(new TelegramInputValuesNode(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertEquals("email", telegramLinkedUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByStateOrNullShouldReturnNullIfUserExistsAndInputIsNull() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertNull(telegramLinkedUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByStateOrNullShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void addCredentialsShouldCallUbiUserServiceMethod() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        when(authorizationService.getUserAuthorizationDTO("email","password")).thenReturn(new AuthorizationDTO());
        when(aesPasswordEncoder.encode("password")).thenReturn("encodedPassword");

        telegramLinkedUserService.addCredentials(123L, "email", "password");

        verify(ubiUserService).createAndAuthorizeOrThrowForTelegramUser(telegramLinkedUserNode, "email", "password");
    }

    @Test
    public void addCredentialsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.addCredentials(123L, "email", "password"));
    }

    @Test
    public void saveUserInputShouldSaveInputIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        telegramLinkedUserService.saveUserInput(123L, InputState.CREDENTIALS_FULL_OR_EMAIL, "email");

        assertEquals("email", telegramLinkedUserNode.getInputValues().get(0).getValue());
        verify(telegramLinkedUserNodeRepository).save(telegramLinkedUserNode);
    }

    @Test
    public void saveUserInputShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.saveUserInput(123L, InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));
    }

    @Test
    public void clearUserInputsShouldClearInputsIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setChatId("123");
        telegramLinkedUserNode.setInputValues(List.of(new TelegramInputValuesNode(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode);

        telegramLinkedUserService.clearUserInputs(123L);

        TelegramLinkedUserNode resultEntity = telegramLinkedUserNodeRepository.findById("123").get();

        assertTrue(resultEntity.getInputValues().isEmpty());
        verify(telegramInputValuesNodeRepository).deleteAllByOwnerChatId("123");
    }

    @Test
    public void clearUserInputsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.clearUserInputs(123L));
    }

    @Test
    public void removeCredentialsByUserInputsShouldRemoveCredentialsIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setChatId("123");
        telegramLinkedUserNode.setLinkedUbisoftAccounts(List.of(new UbiUserNode("email", "password")));
        telegramLinkedUserNode.setInputValues(List.of(new TelegramInputValuesNode(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode);

        telegramLinkedUserService.removeCredentialsByUserInputs(123L);

        TelegramLinkedUserNode resultEntity = telegramLinkedUserNodeRepository.findById("123").get();

        assertTrue(resultEntity.getLinkedUbisoftAccounts().isEmpty());

        verify(ubiUserService).deleteByLinkedTelegramUserChatIdAndEmail("123", "email");
    }

    @Test
    public void removeCredentialsByUserInputsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.removeCredentialsByUserInputs(123L));
    }

    @Test
    public void removeAllCredentialsShouldRemoveAllCredentialsIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setChatId("123");
        telegramLinkedUserNode.setLinkedUbisoftAccounts(List.of(new UbiUserNode("email", "password")));
        telegramLinkedUserNode.setInputValues(List.of(new TelegramInputValuesNode(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode);

        telegramLinkedUserService.removeAllCredentials(123L);

        TelegramLinkedUserNode resultEntity = telegramLinkedUserNodeRepository.findById("123").get();

        assertTrue(resultEntity.getLinkedUbisoftAccounts().isEmpty());

        verify(ubiUserService).deleteAllByLinkedTelegramUserChatId("123");
    }

    @Test
    public void removeAllCredentialsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.removeAllCredentials(123L));
    }

    @Test
    public void getCredentialsEmailsListShouldReturnEmailsListIfUserExists() {
        TelegramLinkedUserNode telegramLinkedUserNode = new TelegramLinkedUserNode();
        telegramLinkedUserNode.setLinkedUbisoftAccounts(List.of(new UbiUserNode("email", "password")));
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserNode));

        assertEquals(List.of("email"), telegramLinkedUserService.getCredentialsEmailsList(123L));
    }

    @Test
    public void getCredentialsEmailsListShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserNodeRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getCredentialsEmailsList(123L));
    }

    @Test
    void getAllChatIdsForNotifiableUsersShouldReturnOnlyUsersWithFlag() {
        TelegramLinkedUserNode telegramLinkedUserNode1 = new TelegramLinkedUserNode();
        telegramLinkedUserNode1.setPublicNotificationsEnabledFlag(true);
        telegramLinkedUserNode1.setChatId("1");
        TelegramLinkedUserNode telegramLinkedUserNode2 = new TelegramLinkedUserNode();
        telegramLinkedUserNode2.setPublicNotificationsEnabledFlag(true);
        telegramLinkedUserNode2.setChatId("2");
        TelegramLinkedUserNode telegramLinkedUserNode3 = new TelegramLinkedUserNode();
        telegramLinkedUserNode3.setPublicNotificationsEnabledFlag(false);
        telegramLinkedUserNode3.setChatId("3");
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode1);
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode2);
        telegramLinkedUserNodeRepository.save(telegramLinkedUserNode3);

        List<String> expected = List.of("1", "2");
        List<String> result = telegramLinkedUserService.getAllChatIdsForNotifiableUsers();

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }
}