package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.authorization.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramInputValueEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramInputValueEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramLinkedUserEntityRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiUserEntityRepository;
import github.ricemonger.marketplace.databases.postgres.services.TelegramUserService;
import github.ricemonger.marketplace.databases.postgres.services.UbiUserService;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.marketplace.databases.postgres.services.AesPasswordEncoder;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
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
class TelegramUserServiceTests {

    @SpyBean
    private UbiUserEntityRepository ubiUserRepository;

    @SpyBean
    private AesPasswordEncoder aesPasswordEncoder;

    @MockBean
    private AuthorizationService authorizationService;

    @SpyBean
    private TelegramLinkedUserEntityRepository telegramUserRepository;

    @SpyBean
    private TelegramInputValueEntityRepository telegramInputValuesRepository;

    @MockBean
    private UbiUserService ubiUserService;

    @Autowired
    private TelegramUserService telegramUserService;

    @BeforeEach
    public void setUp() {
        telegramUserRepository.deleteAll();
        telegramInputValuesRepository.deleteAll();
        ubiUserRepository.deleteAll();
    }

    @Test
    void isTelegramUserRegisteredShouldReturnTrueIfExists() {
        when(telegramUserRepository.existsById("123")).thenReturn(true);

        assertTrue(telegramUserService.isTelegramUserRegistered(123L));

        verify(telegramUserRepository).existsById("123");
    }

    @Test
    void isTelegramUserRegisteredFalseShouldReturnFalseIfDoesntExist() {
        when(telegramUserRepository.existsById("123")).thenReturn(false);

        assertFalse(telegramUserService.isTelegramUserRegistered(123L));

        verify(telegramUserRepository).existsById("123");
    }

    @Test
    void registerTelegramUserShouldSaveInRepositoryIfDoesntExist() {
        when(telegramUserRepository.existsById("123")).thenReturn(false);

        telegramUserService.registerTelegramUser(123L);

        verify(telegramUserRepository).save(any());
    }

    @Test
    public void registerTelegramUserShouldThrowExceptionIfAlreadyExists() {
        when(telegramUserRepository.existsById("123")).thenReturn(true);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramUserService.registerTelegramUser(123L));
    }

    @Test
    public void getUserInputStateShouldReturnStateIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputState.CREDENTIALS_FULL_OR_EMAIL, telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputStateShouldReturnBaseIfUserExistsAndStateIsNull() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputState.BASE, telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputStateShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputGroupShouldReturnGroupIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setInputGroup(InputGroup.CREDENTIALS_ADD);
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputGroup.CREDENTIALS_ADD, telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroupShouldReturnBaseIfUserExistsAndGroupIsNull() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputGroup.BASE, telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroupShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputByStateOrNullShouldReturnInputIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramInputValuesRepository.save(new TelegramInputValueEntity("123",InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals("email", telegramUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByStateOrNullShouldReturnNullIfUserExistsAndInputIsNull() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertNull(telegramUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByStateOrNullShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void addCredentialsIfValidOrThrowExceptionShouldCallUbiUserServiceMethod() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        when(authorizationService.getUserAuthorizationDTO("email","password")).thenReturn(new AuthorizationDTO());
        when(aesPasswordEncoder.encode("password")).thenReturn("encodedPassword");

        telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password");

        verify(ubiUserService).createAndAuthorizeOrThrowForTelegramUser("123", "email", "password");
    }

    @Test
    public void addCredentialsIfValidOrThrowExceptionShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.addCredentialsIfValidOrThrowException(123L, "email", "password"));
    }

    @Test
    public void saveUserInputShouldSaveInputIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramUserService.saveUserInput(123L, InputState.CREDENTIALS_FULL_OR_EMAIL, "email");

        verify(telegramInputValuesRepository).save(any());
    }

    @Test
    public void saveUserInputShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.saveUserInput(123L, InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));
    }

    @Test
    public void clearUserInputsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.clearUserInputs(123L));
    }

    @Test
    public void getCredentialsEmailsListShouldThrowExceptionIfUserDoesntExist() {
        when(telegramUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserService.getCredentialsEmailsList(123L));
    }

    @Test
    void getAllChatIdsForNotifiableUsersShouldReturnOnlyUsersWithFlag() {
        TelegramLinkedUserEntity telegramLinkedUserEntity1 = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity1.setPublicNotificationsEnabledFlag(true);
        telegramLinkedUserEntity1.setChatId("1");
        TelegramLinkedUserEntity telegramLinkedUserEntity2 = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity2.setPublicNotificationsEnabledFlag(true);
        telegramLinkedUserEntity2.setChatId("2");
        TelegramLinkedUserEntity telegramLinkedUserEntity3 = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity3.setPublicNotificationsEnabledFlag(false);
        telegramLinkedUserEntity3.setChatId("3");
        telegramUserRepository.save(telegramLinkedUserEntity1);
        telegramUserRepository.save(telegramLinkedUserEntity2);
        telegramUserRepository.save(telegramLinkedUserEntity3);

        List<String> expected = List.of("1", "2");
        List<String> result = telegramUserService.getAllChatIdsForNotifiableUsers();

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }
}