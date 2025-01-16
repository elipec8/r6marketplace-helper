package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.dto_projections.UbiAccountAuthorizationEntryProjection;
import github.ricemonger.marketplace.databases.postgres.repositories.CustomUbiAccountEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserUbiAccountPostgresServiceTest {
    @Autowired
    private TelegramUserUbiAccountPostgresService telegramUserUbiAccountEntryService;
    @MockBean
    private CustomUbiAccountEntryPostgresRepository ubiAccountAuthorizationEntryRepository;
    @MockBean
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void save_should_save_ubi_account_entry_if_exists_same_ubi_acc_for_user() {
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAccountStats(new UbiAccountStatsEntity());
        ubiAccountEntryEntity.getUbiAccountStats().setUbiProfileId("profileId");

        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("profileId");

        UbiAccountEntryEntity entity = Mockito.mock(UbiAccountEntryEntity.class);

        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.of(ubiAccountEntryEntity));
        when(ubiAccountEntryEntityMapper.createEntity("chatId", account)).thenReturn(entity);

        telegramUserUbiAccountEntryService.save("chatId", account);

        Mockito.verify(ubiAccountAuthorizationEntryRepository).save(same(entity));
    }

    @Test
    public void save_should_save_ubi_account_entry_if_user_doesnt_have_ubi_acc_auth_entry() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("profileId");

        UbiAccountEntryEntity entity = Mockito.mock(UbiAccountEntryEntity.class);

        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.empty());
        when(ubiAccountEntryEntityMapper.createEntity("chatId", account)).thenReturn(entity);

        telegramUserUbiAccountEntryService.save("chatId", account);

        Mockito.verify(ubiAccountAuthorizationEntryRepository).save(same(entity));
    }

    @Test
    public void save_should_save_ubi_account_entry_if_user_doesnt_have_ubi_acc_stats() {
        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("profileId");

        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAccountStats(null);

        UbiAccountEntryEntity entity = Mockito.mock(UbiAccountEntryEntity.class);

        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.of(ubiAccountEntryEntity));
        when(ubiAccountEntryEntityMapper.createEntity("chatId", account)).thenReturn(entity);

        telegramUserUbiAccountEntryService.save("chatId", account);

        Mockito.verify(ubiAccountAuthorizationEntryRepository).save(same(entity));
    }

    @Test
    public void save_should_throw_if_user_has_another_ubi_acc_linked() {
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAccountStats(new UbiAccountStatsEntity());
        ubiAccountEntryEntity.getUbiAccountStats().setUbiProfileId("profileId1");

        UbiAccountAuthorizationEntry account = new UbiAccountAuthorizationEntry();
        account.setUbiProfileId("profileId");

        UbiAccountEntryEntity entity = Mockito.mock(UbiAccountEntryEntity.class);

        when(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId("chatId")).thenReturn(Optional.of(ubiAccountEntryEntity));
        when(ubiAccountEntryEntityMapper.createEntity("chatId", account)).thenReturn(entity);

        assertThrows(UbiAccountEntryAlreadyExistsException.class, () -> telegramUserUbiAccountEntryService.save("chatId", account));
    }

    @Test
    public void deleteByChatId_should_handle_to_repository() {
        telegramUserUbiAccountEntryService.deleteByChatId("chatId");

        Mockito.verify(ubiAccountAuthorizationEntryRepository).deleteByUserTelegramUserChatId("chatId");
    }

    @Test
    public void findByChatId_should_return_mapped_ubi_acc_entry() {
        UbiAccountAuthorizationEntryProjection projection = Mockito.mock(UbiAccountAuthorizationEntryProjection.class);

        when(ubiAccountAuthorizationEntryRepository.findUbiAccountAuthorizationEntryByUserTelegramUserChatId("chatId")).thenReturn(Optional.of(projection));

        UbiAccountAuthorizationEntry entry = Mockito.mock(UbiAccountAuthorizationEntry.class);
        when(ubiAccountEntryEntityMapper.createUbiAccountAuthorizationEntry(same(projection))).thenReturn(entry);


        when(ubiAccountAuthorizationEntryRepository.findUbiAccountAuthorizationEntryByUserTelegramUserChatId("chatId")).thenReturn(Optional.of(projection));

        assertSame(entry, telegramUserUbiAccountEntryService.findByChatId("chatId"));
    }
}