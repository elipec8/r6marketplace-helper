package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsEntityPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UbiAccountStatsEntityMapper;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntryWithTelegram;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @MockBean
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    @Test
    public void saveAuthorizationInfo_should_map_and_save_ubi_account_if_doesnt_exists_other_ubiAccountAuthorizationEntry_for_user() {
        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.empty());

        String chatId = "chatId";
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("ubiProfileId");
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(new UbiAccountStatsEntity("ubiProfileId"));
        when(ubiAccountEntryEntityMapper.createEntityForTelegramUser(same(chatId), same(account))).thenReturn(entity);

        telegramUserUbiAccountEntryService.saveAuthorizationInfo(chatId, account);

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

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> telegramUserUbiAccountEntryService.saveAuthorizationInfo(chatId, account));
    }

    @Test
    public void saveAllUbiAccountStats_should_map_and_save_all_ubiAccountStats() {
        List<UbiAccountStatsEntity> entities = List.of();
        List<UbiAccountStatsEntityDTO> ubiAccountStats = List.of();
        when(ubiAccountStatsEntityMapper.createEntities(same(ubiAccountStats))).thenReturn(entities);

        telegramUserUbiAccountEntryService.saveAllUbiAccountStats(ubiAccountStats);

        verify(ubiAccountStatsRepository).saveAll(same(entities));
    }

    @Test
    public void deleteAuthorizationInfoByChatId_should_delete_ubiAccountAuthorizationEntry_by_chatId() {
        String chatId = "chatId";
        TelegramUserEntity telegramUserEntity = Mockito.mock(TelegramUserEntity.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(telegramUserRepository.findById(chatId)).thenReturn(Optional.of(telegramUserEntity));

        when(telegramUserEntity.getUser()).thenReturn(userEntity);

        telegramUserUbiAccountEntryService.deleteAuthorizationInfoByChatId(chatId);

        verify(userEntity).setUbiAccountEntry(null);
        verify(telegramUserRepository).save(same(telegramUserEntity));
    }

    @Test
    public void deleteAuthorizationInfoByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        String chatId = "chatId";
        when(telegramUserRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserUbiAccountEntryService.deleteAuthorizationInfoByChatId(chatId));
    }

    @Test
    public void findAuthorizationInfoByChatId_should_return_ubiAccountAuthorizationEntry_by_chatId() {
        String chatId = "chatId";
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();

        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(chatId)).thenReturn(Optional.of(entity));

        UbiAccountAuthorizationEntry authorizationEntry = new UbiAccountAuthorizationEntry();
        when(ubiAccountEntryEntityMapper.createUbiAccountAuthorizationEntry(same(entity))).thenReturn(authorizationEntry);

        UbiAccountAuthorizationEntry result = telegramUserUbiAccountEntryService.findAuthorizationInfoByChatId(chatId);

        assertSame(authorizationEntry, result);
    }

    @Test
    public void findAuthorizationInfoByChatId_should_throw_exception_if_telegram_user_doesnt_exist() {
        String chatId = "chatId";
        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(chatId)).thenReturn(Optional.empty());

        assertThrows(UbiAccountEntryDoesntExistException.class, () -> telegramUserUbiAccountEntryService.findAuthorizationInfoByChatId(chatId));
    }

    @Test
    public void findAllAuthorizationInfoForTelegram_should_return_all_ubiAccountAuthorizationEntries() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        List<UbiAccountEntryEntity> entities = List.of(entity1, entity2);
        when(ubiAccountAuthorizationEntryRepository.findAll()).thenReturn(entities);

        UbiAccountAuthorizationEntryWithTelegram authorizationEntry1 = new UbiAccountAuthorizationEntryWithTelegram();
        UbiAccountAuthorizationEntryWithTelegram authorizationEntry2 = new UbiAccountAuthorizationEntryWithTelegram();
        when(ubiAccountEntryEntityMapper.createUbiAccountAuthorizationEntryWithTelegram(same(entity1))).thenReturn(authorizationEntry1);
        when(ubiAccountEntryEntityMapper.createUbiAccountAuthorizationEntryWithTelegram(same(entity2))).thenReturn(authorizationEntry2);

        List<UbiAccountAuthorizationEntryWithTelegram> result = telegramUserUbiAccountEntryService.findAllAuthorizationInfoForTelegram();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(res -> res == authorizationEntry1));
        assertTrue(result.stream().anyMatch(res -> res == authorizationEntry2));
    }

    @Test
    public void findAllForTelegram_should_return_all_ubiAccountEntries() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        List<UbiAccountEntryEntity> entities = List.of(entity1, entity2);
        when(ubiAccountAuthorizationEntryRepository.findAll()).thenReturn(entities);

        UbiAccountEntryWithTelegram ubiAccountEntry1 = new UbiAccountEntryWithTelegram();
        UbiAccountEntryWithTelegram ubiAccountEntry2 = new UbiAccountEntryWithTelegram();
        when(ubiAccountEntryEntityMapper.createUbiAccountEntryWithTelegram(same(entity1))).thenReturn(ubiAccountEntry1);
        when(ubiAccountEntryEntityMapper.createUbiAccountEntryWithTelegram(same(entity2))).thenReturn(ubiAccountEntry2);

        List<UbiAccountEntryWithTelegram> result = telegramUserUbiAccountEntryService.findAllForTelegram();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(res -> res == ubiAccountEntry1));
        assertTrue(result.stream().anyMatch(res -> res == ubiAccountEntry2));
    }
}