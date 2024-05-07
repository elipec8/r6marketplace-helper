package github.ricemonger.marketplace.databases.neo4j.services;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramInputValuesEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.neo4j.repositories.TelegramLinkedUserRepository;
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
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramLinkedUserServiceTests {

    @SpyBean
    private TelegramLinkedUserRepository telegramLinkedUserRepository;


    @Autowired
    private TelegramLinkedUserService telegramLinkedUserService;

    @SpyBean
    private AesPasswordEncoder aesPasswordEncoder;

    @BeforeEach
    public void setUp() {
        telegramLinkedUserRepository.deleteAll();
    }

    @AfterEach
    public void cleanUp() {
        telegramLinkedUserRepository.deleteAll();
    }

    @Test
    void isTelegramUserRegisteredShouldReturnTrueIfExists() {
        when(telegramLinkedUserRepository.existsById("123")).thenReturn(true);

        assertTrue(telegramLinkedUserService.isTelegramUserRegistered(123L));

        verify(telegramLinkedUserRepository).existsById("123");
    }

    @Test
    void isTelegramUserRegisteredFalseShouldReturnFalseIfDoesntExist() {
        when(telegramLinkedUserRepository.existsById("123")).thenReturn(false);

        assertFalse(telegramLinkedUserService.isTelegramUserRegistered(123L));

        verify(telegramLinkedUserRepository).existsById("123");
    }

    @Test
    void registerTelegramUserShouldSaveInRepositoryIfDoesntExist() {
        when(telegramLinkedUserRepository.existsById("123")).thenReturn(false);

        telegramLinkedUserService.registerTelegramUser(123L);

        verify(telegramLinkedUserRepository).save(any());
    }

    @Test
    public void registerTelegramUserShouldThrowExceptionIfAlreadyExists() {
        when(telegramLinkedUserRepository.existsById("123")).thenReturn(true);

        assertThrows(TelegramUserAlreadyExistsException.class, () -> telegramLinkedUserService.registerTelegramUser(123L));
    }

    @Test
    public void setUserNextInputOrThrowShouldSetInputStateIfUserExistsState() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.setUserNextInputState(123L, InputState.CREDENTIALS_FULL_OR_EMAIL);

        assertEquals(InputState.CREDENTIALS_FULL_OR_EMAIL, telegramLinkedUserEntity.getInputState());
        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void setUserNextInputOrThrowShouldThrowExceptionIfUserDoesntExistState() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.setUserNextInputState(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void setUserNextInputGroupOrThrowShouldSetInputGroupIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.setUserNextInputGroup(123L, InputGroup.CREDENTIALS_ADD);

        assertEquals(InputGroup.CREDENTIALS_ADD, telegramLinkedUserEntity.getInputGroup());
        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void setUserNextInputGroupOrThrowShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.setUserNextInputGroup(123L, InputGroup.CREDENTIALS_ADD));
    }

    @Test
    public void getUserInputStateShouldReturnStateIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setInputState(InputState.CREDENTIALS_FULL_OR_EMAIL);
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputState.CREDENTIALS_FULL_OR_EMAIL, telegramLinkedUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputStateShouldReturnBaseIfUserExistsAndStateIsNull() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputState.BASE, telegramLinkedUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputStateShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getUserInputState(123L));
    }

    @Test
    public void getUserInputGroupShouldReturnGroupIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setInputGroup(InputGroup.CREDENTIALS_ADD);
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputGroup.CREDENTIALS_ADD, telegramLinkedUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroupShouldReturnBaseIfUserExistsAndGroupIsNull() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(InputGroup.BASE, telegramLinkedUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputGroupShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getUserInputGroup(123L));
    }

    @Test
    public void getUserInputByStateOrNullShouldReturnInputIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setInputValues(List.of(new TelegramInputValuesEntity(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals("email", telegramLinkedUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByStateOrNullShouldReturnNullIfUserExistsAndInputIsNull() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertNull(telegramLinkedUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void getUserInputByStateOrNullShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getUserInputByStateOrNull(123L, InputState.CREDENTIALS_FULL_OR_EMAIL));
    }

    @Test
    public void addCredentialsShouldAddCredentialsAndEncodePasswordIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.addCredentials(123L, "email", "password");

        assertEquals("email", telegramLinkedUserEntity.getLinkedUbisoftAccounts().getFirst().getEmail());
        assertEquals(aesPasswordEncoder.getEncodedPassword("password"),
                telegramLinkedUserEntity.getLinkedUbisoftAccounts().getFirst().getPassword());
        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void addCredentialsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.addCredentials(123L, "email", "password"));
    }

    @Test
    public void saveUserInputShouldSaveInputIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.saveUserInput(123L, InputState.CREDENTIALS_FULL_OR_EMAIL, "email");

        assertEquals("email", telegramLinkedUserEntity.getInputValues().get(0).getValue());
        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void saveUserInputShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.saveUserInput(123L, InputState.CREDENTIALS_FULL_OR_EMAIL, "email"));
    }

    @Test
    public void clearUserInputsShouldClearInputsIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setInputValues(List.of(new TelegramInputValuesEntity(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.clearUserInputs(123L);

        assertTrue(telegramLinkedUserEntity.getInputValues().isEmpty());
        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void clearUserInputsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.clearUserInputs(123L));
    }

    @Test
    public void removeCredentialsByUserInputsShouldRemoveCredentialsIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setLinkedUbisoftAccounts(List.of(new UbiUserEntity("email", "password")));
        telegramLinkedUserEntity.setInputValues(List.of(new TelegramInputValuesEntity(InputState.CREDENTIALS_FULL_OR_EMAIL, "email")));
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.removeCredentialsByUserInputs(123L);

        assertTrue(telegramLinkedUserEntity.getLinkedUbisoftAccounts().isEmpty());

        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void removeCredentialsByUserInputsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.removeCredentialsByUserInputs(123L));
    }

    @Test
    public void removeAllCredentialsShouldRemoveAllCredentialsIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setLinkedUbisoftAccounts(List.of(new UbiUserEntity("email", "password")));
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        telegramLinkedUserService.removeAllCredentials(123L);

        assertTrue(telegramLinkedUserEntity.getLinkedUbisoftAccounts().isEmpty());

        verify(telegramLinkedUserRepository).save(telegramLinkedUserEntity);
    }

    @Test
    public void removeAllCredentialsShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.removeAllCredentials(123L));
    }

    @Test
    public void getCredentialsEmailsListShouldReturnEmailsListIfUserExists() {
        TelegramLinkedUserEntity telegramLinkedUserEntity = new TelegramLinkedUserEntity();
        telegramLinkedUserEntity.setLinkedUbisoftAccounts(List.of(new UbiUserEntity("email", "password")));
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.of(telegramLinkedUserEntity));

        assertEquals(List.of("email"), telegramLinkedUserService.getCredentialsEmailsList(123L));
    }

    @Test
    public void getCredentialsEmailsListShouldThrowExceptionIfUserDoesntExist() {
        when(telegramLinkedUserRepository.findById("123")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramLinkedUserService.getCredentialsEmailsList(123L));
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
        telegramLinkedUserRepository.save(telegramLinkedUserEntity1);
        telegramLinkedUserRepository.save(telegramLinkedUserEntity2);
        telegramLinkedUserRepository.save(telegramLinkedUserEntity3);

        List<String> expected = List.of("1", "2");
        List<String> result = telegramLinkedUserService.getAllChatIdsForNotifiableUsers();

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }
}