package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomTelegramCustomUserPostgresRepositoryTest {
    @Autowired
    private CustomTelegramUserPostgresRepository customTelegramUserPostgresRepository;
    @Autowired
    private CustomUserPostgresRepository customUserPostgresRepository;

    @BeforeEach
    void setUp() {
        customTelegramUserPostgresRepository.deleteAll();
        customUserPostgresRepository.deleteAll();
    }

    @Test
    public void updateInputState_should_update_existing_user_input_group() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setInputState(InputState.BASE);
        telegramUserEntity1.setInputGroup(InputGroup.BASE);
        telegramUserEntity1 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2.setInputState(InputState.BASE);
        telegramUserEntity2.setInputGroup(InputGroup.BASE);
        telegramUserEntity2 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);

        customTelegramUserPostgresRepository.updateInputState("chatId1", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        customTelegramUserPostgresRepository.flush();

        List<TelegramUserEntity> tgUsers = customTelegramUserPostgresRepository.findAll();
        ;

        assertEquals(2, tgUsers.size());
        assertEquals(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId1")).findFirst().get().getInputState());
        assertEquals(InputState.BASE, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId2")).findFirst().get().getInputState());
    }

    @Test
    public void updateInputStateAndGroup_should_update_existing_user_input_group_and_state() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setInputState(InputState.BASE);
        telegramUserEntity1.setInputGroup(InputGroup.BASE);
        telegramUserEntity1 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2.setInputState(InputState.BASE);
        telegramUserEntity2.setInputGroup(InputGroup.BASE);
        telegramUserEntity2 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);

        customTelegramUserPostgresRepository.updateInputStateAndGroup("chatId1", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, InputGroup.ITEM_FILTER_EDIT);
        customTelegramUserPostgresRepository.flush();

        List<TelegramUserEntity> tgUsers = customTelegramUserPostgresRepository.findAll();
        ;

        assertEquals(2, tgUsers.size());
        assertEquals(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId1")).findFirst().get().getInputState());
        assertEquals(InputGroup.ITEM_FILTER_EDIT, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId1")).findFirst().get().getInputGroup());
        assertEquals(InputState.BASE, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId2")).findFirst().get().getInputState());
        assertEquals(InputGroup.BASE, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId2")).findFirst().get().getInputGroup());
    }

    @Test
    public void updateItemShowFewItemsInMessageFlag_should_update_existing_user_item_show_few_items_in_message_flag() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setItemShowFewInMessageFlag(true);
        telegramUserEntity1 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2.setItemShowFewInMessageFlag(true);
        telegramUserEntity2 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);

        customTelegramUserPostgresRepository.updateItemShowFewItemsInMessageFlag("chatId1", false);
        customTelegramUserPostgresRepository.flush();

        List<TelegramUserEntity> tgUsers = customTelegramUserPostgresRepository.findAll();
        ;

        assertEquals(2, tgUsers.size());
        assertEquals(false, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId1")).findFirst().get().getItemShowFewInMessageFlag());
        assertEquals(true, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId2")).findFirst().get().getItemShowFewInMessageFlag());
    }

    @Test
    public void updateItemShowMessagesLimit_should_update_existing_user_item_show_messages_limit() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setItemShowMessagesLimit(5);
        telegramUserEntity1 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2.setItemShowMessagesLimit(5);
        telegramUserEntity2 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);

        customTelegramUserPostgresRepository.updateItemShowMessagesLimit("chatId1", 10);
        customTelegramUserPostgresRepository.flush();

        List<TelegramUserEntity> tgUsers = customTelegramUserPostgresRepository.findAll();
        ;

        assertEquals(2, tgUsers.size());
        assertEquals(10, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId1")).findFirst().get().getItemShowMessagesLimit());
        assertEquals(5, tgUsers.stream().filter(tgUser -> tgUser.getChatId().equals("chatId2")).findFirst().get().getItemShowMessagesLimit());
    }

    @Test
    public void findTelegramUserInputStateAndGroupByChatId_should_return_dto_for_user() {
        UserEntity userEntity1 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity1 = new TelegramUserEntity();
        telegramUserEntity1.setChatId("chatId1");
        telegramUserEntity1.setUser(userEntity1);
        telegramUserEntity1.setInputState(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        telegramUserEntity1.setInputGroup(InputGroup.ITEM_FILTER_EDIT);
        telegramUserEntity1 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity1);

        UserEntity userEntity2 = customUserPostgresRepository.save(new UserEntity());
        TelegramUserEntity telegramUserEntity2 = new TelegramUserEntity();
        telegramUserEntity2.setChatId("chatId2");
        telegramUserEntity2.setUser(userEntity2);
        telegramUserEntity2.setInputState(InputState.BASE);
        telegramUserEntity2.setInputGroup(InputGroup.BASE);
        telegramUserEntity2 = customTelegramUserPostgresRepository.saveAndFlush(telegramUserEntity2);

        assertEquals(new TelegramUserInputStateAndGroup("chatId1", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, InputGroup.ITEM_FILTER_EDIT), customTelegramUserPostgresRepository.findTelegramUserInputStateAndGroupByChatId("chatId1").get());
        assertEquals(new TelegramUserInputStateAndGroup("chatId2", InputState.BASE, InputGroup.BASE), customTelegramUserPostgresRepository.findTelegramUserInputStateAndGroupByChatId("chatId2").get());
    }
}