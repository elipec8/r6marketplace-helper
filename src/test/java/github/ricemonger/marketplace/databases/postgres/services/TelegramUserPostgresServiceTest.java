package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserPostgresServiceTest {
    @MockBean
    private TelegramUserPostgresRepository repository;

    @Autowired
    private TelegramUserPostgresService service;

    @Test
    public void save_should_handle_to_repository() {
        TelegramUser user = new TelegramUser(1L);

        service.save(user);

        verify(repository).save(argThat(entity -> entity.getChatId().equals(user.getChatId())));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_throw_if_doesnt_exist() {
        TelegramUser user = new TelegramUser(1L);

        when(repository.findById(user.getChatId())).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> service.setItemShowFewItemsInMessageFlag(user.getChatId(), true));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_handle_to_repository_true() {
        TelegramUser user = new TelegramUser(1L);

        TelegramUserEntity entity = new TelegramUserEntity(user);

        when(repository.findById(user.getChatId())).thenReturn(Optional.of(entity));

        service.setItemShowFewItemsInMessageFlag(user.getChatId(), true);

        verify(repository).save(argThat(e -> e.getChatId().equals(user.getChatId()) && e.isItemShowFewInMessageFlag()));
    }

    @Test
    public void setItemShowFewItemsInMessageFlag_should_handle_to_repository_false() {
        TelegramUser user = new TelegramUser(1L);

        TelegramUserEntity entity = new TelegramUserEntity(user);

        when(repository.findById(user.getChatId())).thenReturn(Optional.of(entity));

        service.setItemShowFewItemsInMessageFlag(user.getChatId(), false);

        verify(repository).save(argThat(e -> e.getChatId().equals(user.getChatId()) && !e.isItemShowFewInMessageFlag()));
    }

    @Test
    public void setItemShowMessagesLimit_should_throw_if_doesnt_exist() {
        TelegramUser user = new TelegramUser(1L);

        when(repository.findById(user.getChatId())).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> service.setItemShowMessagesLimit(user.getChatId(), 1));
    }

    @Test
    public void setItemShowMessagesLimit_should_handle_to_repository() {
        TelegramUser user = new TelegramUser(1L);

        TelegramUserEntity entity = new TelegramUserEntity(user);

        when(repository.findById(user.getChatId())).thenReturn(Optional.of(entity));

        service.setItemShowMessagesLimit(user.getChatId(), 1);

        verify(repository).save(argThat(e -> e.getChatId().equals(user.getChatId()) && e.getItemShowMessagesLimit() == 1));
    }

    @Test
    public void setItemShowFieldsSettings_should_throw_if_doesnt_exist() {
        TelegramUser user = new TelegramUser(1L);

        when(repository.findById(user.getChatId())).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> service.setItemShowFieldsSettings(user.getChatId(), null));
    }

    @Test
    public void setItemShowFieldsSettings_should_handle_to_repository() {
        TelegramUser user = new TelegramUser(1L);

        TelegramUserEntity entity = new TelegramUserEntity(user);

        when(repository.findById(user.getChatId())).thenReturn(Optional.of(entity));

        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowItemTypeFlag(true);
        settings.setItemShowNameFlag(true);
        settings.setItemShowMaxBuyPrice(true);
        settings.setItemShowBuyOrdersCountFlag(true);
        settings.setItemShowMinSellPriceFlag(true);
        settings.setItemsShowSellOrdersCountFlag(true);
        settings.setItemShowPictureFlag(true);

        service.setItemShowFieldsSettings(user.getChatId(), settings);

        verify(repository).save(argThat(e ->
                e.getChatId().equals(user.getChatId()) &&
                e.isItemShowItemTypeFlag() == settings.isItemShowItemTypeFlag() &&
                e.isItemShowNameFlag() == settings.isItemShowNameFlag() &&
                e.isItemShowMaxBuyPrice() == settings.isItemShowMaxBuyPrice() &&
                e.isItemShowBuyOrdersCountFlag() == settings.isItemShowBuyOrdersCountFlag() &&
                e.isItemShowMinSellPriceFlag() == settings.isItemShowMinSellPriceFlag() &&
                e.isItemsShowSellOrdersCountFlag() == settings.isItemsShowSellOrdersCountFlag() &&
                e.isItemShowPictureFlag() == settings.isItemShowPictureFlag()
        ));
    }

    @Test
    public void addItemShowAppliedFilter_should_throw_if_doesnt_exist() {
        TelegramUser user = new TelegramUser(1L);

        when(repository.findById(user.getChatId())).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> service.addItemShowAppliedFilter(user.getChatId(), null));
    }

    @Test
    public void addItemShowAppliedFilter_should_save_changed_filter() {
        TelegramUser user = new TelegramUser(1L);
        TelegramUserEntity entity = new TelegramUserEntity(user);

        when(repository.findById(user.getChatId())).thenReturn(Optional.of(entity));

        ItemFilter filter = new ItemFilter();
        filter.setName("1");

        service.addItemShowAppliedFilter("1", filter);

        verify(repository).save(argThat(e -> e.getChatId().equals(user.getChatId()) && e.getItemShowAppliedFilters().size() == 1 && e.getItemShowAppliedFilters().get(0).getName().equals(
                "1")));
    }

    @Test
    public void removeItemShowAppliedFilter_should_throw_if_doesnt_exist() {
        TelegramUser user = new TelegramUser(1L);

        when(repository.findById(user.getChatId())).thenReturn(Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> service.removeItemShowAppliedFilter(user.getChatId(), null));
    }

    @Test
    public void removeItemShowAppliedFilter_should_save_changed_filter() {
        ItemFilter filter = new ItemFilter();
        filter.setName("1");

        TelegramUser user = new TelegramUser(1L);
        TelegramUserEntity entity = new TelegramUserEntity(user);

        List<ItemFilterEntity> filters = new ArrayList<>();
        filters.add(new ItemFilterEntity(filter));

        entity.setItemShowAppliedFilters(filters);

        when(repository.findById(user.getChatId())).thenReturn(Optional.of(entity));

        service.removeItemShowAppliedFilter("1", filter.getName());

        verify(repository).save(argThat(e -> e.getChatId().equals(user.getChatId()) && e.getItemShowAppliedFilters().isEmpty()));
    }

    @Test
    public void existsById_should_handle_to_repository(){
        when(repository.existsById(any())).thenReturn(true);
        assertTrue(service.existsById("1"));

        when(repository.existsById(any())).thenReturn(false);
        assertFalse(service.existsById("1"));
    }

    @Test
    public void findUserById_should_throw_if_doesnt_exist(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(TelegramUserDoesntExistException.class, () -> service.findUserById("1"));
    }

    @Test
    public void findUserById_should_return_(){
        TelegramUser user = new TelegramUser(1L);
        when(repository.findById(any())).thenReturn(Optional.of(new TelegramUserEntity(user)));
        assertEquals(user, service.findUserById("1"));
    }

    @Test
    public void findUserSettingsById_should_throw_if_doesnt_exist(){
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(TelegramUserDoesntExistException.class, () -> service.findUserSettingsById("1"));
    }

    @Test
    public void findUserSettingsById_should_return_settings(){

        ItemShowSettings settings = new ItemShowSettings();
        settings.setItemShowPictureFlag(true);

        TelegramUser user = new TelegramUser(1L);
        user.setItemShowPictureFlag(true);
        when(repository.findById(any())).thenReturn(Optional.of(new TelegramUserEntity(user)));
        assertEquals(settings.isItemShowPictureFlag(), service.findUserSettingsById("1").isItemShowPictureFlag());
    }

    @Test
    public void findAllUsersUsers_should_handle_to_repository(){
        when(repository.findAll()).thenReturn(List.of(new TelegramUserEntity(), new TelegramUserEntity()));

        assertEquals(2, service.findAllUsers().size());
        verify(repository).findAll();
    }
}