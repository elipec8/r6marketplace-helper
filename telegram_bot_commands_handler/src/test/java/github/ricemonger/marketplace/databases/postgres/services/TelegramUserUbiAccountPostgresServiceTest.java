package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsEntityPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserUbiAccountPostgresServiceTest {
    @Autowired
    private TelegramUserUbiAccountPostgresService telegramUserUbiAccountEntryService;
    @MockBean
    private UbiAccountAuthorizationEntryPostgresRepository ubiAccountAuthorizationEntryRepository;
    @MockBean
    private UbiAccountStatsEntityPostgresRepository ubiAccountStatsRepository;
    @MockBean
    private TelegramUserPostgresRepository telegramUserRepository;
    @MockBean
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void saveAuthorizationInfo_should_map_and_save_ubi_account_if_doesnt_exists_other_ubiAccountAuthorizationEntry_for_user() {
        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.empty());

        String chatId = "chatId";
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("ubiProfileId");
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));
        when(ubiAccountEntryEntityMapper.createEntityForTelegramUser(same(chatId), same(account))).thenReturn(entity);

        telegramUserUbiAccountEntryService.save(chatId, account);

        verify(ubiAccountAuthorizationEntryRepository).save(same(entity));
    }

    @Test
    public void saveAuthorizationInfo_should_throw_exception_if_ubiAccountAuthorizationEntry_for_user_already_exists() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUser(new UserEntity(1L));
        entity.setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));
        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.of(entity));

        String chatId = "chatId";
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("anotherUbiProfileId");

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> telegramUserUbiAccountEntryService.save(chatId, account));
    }

    @Test
    public void deleteAuthorizationInfoByChatId_should_delete_ubiAccountAuthorizationEntry_by_chatId() {
        String chatId = "chatId";
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(telegramUserRepository.findById(chatId)).thenReturn(Optional.of(telegramUserEntity));

        when(telegramUserEntity.getUser()).thenReturn(userEntity);

        telegramUserUbiAccountEntryService.deleteByChatId(chatId);

        verify(userEntity).setUbiAccountEntry(null);
        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void deleteByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        String chatId = "chatId";
        when(telegramUserRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.deleteByChatId(chatId));
    }

    @Test
    public void findAuthorizationInfoByChatId_should_return_ubiAccountAuthorizationEntry_by_chatId() {
        String chatId = "chatId";
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();

        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(chatId)).thenReturn(Optional.of(entity));

        UbiAccountAuthorizationEntry authorizationEntry = new UbiAccountAuthorizationEntry();
        when(ubiAccountEntryEntityMapper.createUbiAccountAuthorizationEntry(same(entity))).thenReturn(authorizationEntry);

        UbiAccountAuthorizationEntry result = telegramUserUbiAccountEntryService.findByChatId(chatId);

        assertSame(authorizationEntry, result);
    }

    @Test
    public void findByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        String chatId = "chatId";
        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(chatId)).thenReturn(Optional.empty());

        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findByChatId(chatId));
    }
}