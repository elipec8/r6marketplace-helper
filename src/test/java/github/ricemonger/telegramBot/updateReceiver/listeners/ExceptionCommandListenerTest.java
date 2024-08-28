package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.exceptions.client.*;
import github.ricemonger.telegramBot.executors.exceptions.server.ServerOrUnsupportedExceptionExecutor;
import github.ricemonger.telegramBot.executors.exceptions.server.TelegramApiRuntimeExceptionExecutor;
import github.ricemonger.telegramBot.executors.exceptions.server.UbiUserAuthorizationServerErrorExceptionExecutor;
import github.ricemonger.utils.exceptions.ClientAbstractException;
import github.ricemonger.utils.exceptions.ServerAbstractException;
import github.ricemonger.utils.exceptions.client.*;
import github.ricemonger.utils.exceptions.server.TelegramApiRuntimeException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ExceptionCommandListenerTest {
    @Autowired
    private ExceptionCommandListener exceptionCommandListener;
    @MockBean
    private ExecutorsService executorsService;

    @Test
    public void handleException_should_item_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new ItemDoesntExistException(""), updateInfo);
        verify(executorsService).execute(ItemDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_item_filter_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new ItemFilterDoesntExistException(""), updateInfo);
        verify(executorsService).execute(ItemFilterDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_tag_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new TagDoesntExistException(""), updateInfo);
        verify(executorsService).execute(TagDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_telegram_user_already_exists_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new TelegramUserAlreadyExistsException(""), updateInfo);
        verify(executorsService).execute(TelegramUserAlreadyExistsExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_telegram_user_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new TelegramUserDoesntExistException(""), updateInfo);
        verify(executorsService).execute(TelegramUserDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_trade_by_filters_manager_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new TradeByFiltersManagerDoesntExistException(""), updateInfo);
        verify(executorsService).execute(TradeByFilterManagerDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_trade_by_item_id_manager_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new TradeByItemIdManagerDoesntExistException(""), updateInfo);
        verify(executorsService).execute(TradeByItemIdManagerDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_ubi_account_entry_already_exists_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new UbiAccountEntryAlreadyExistsException(""), updateInfo);
        verify(executorsService).execute(UbiAccountEntryAlreadyExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_ubi_account_entry_doesnt_exist_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new UbiAccountEntryDoesntExistException(""), updateInfo);
        verify(executorsService).execute(UbiAccountEntryDoesntExistExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_ubi_user_authorization_client_error_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new UbiUserAuthorizationClientErrorException(""), updateInfo);
        verify(executorsService).execute(UbiUserAuthorizationClientErrorExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_unexpected_direct_command_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new UnexpectedDirectCommandException(""), updateInfo);
        verify(executorsService).execute(UnexpectedDirectCommandExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_unhandled_client_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new ClientAbstractException("") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        }, updateInfo);
        verify(executorsService).execute(ServerOrUnsupportedExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_ubi_user_authorization_server_error_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new UbiUserAuthorizationServerErrorException(""), updateInfo);
        verify(executorsService).execute(UbiUserAuthorizationServerErrorExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_telegram_api_runtime_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new TelegramApiRuntimeException(""), updateInfo);
        verify(executorsService).execute(TelegramApiRuntimeExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_server_or_unsupported_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new ServerAbstractException("") {
        }, updateInfo);
        verify(executorsService).execute(ServerOrUnsupportedExceptionExecutor.class, updateInfo);
    }

    @Test
    public void handleException_should_unhandled_exception() {
        UpdateInfo updateInfo = new UpdateInfo();
        exceptionCommandListener.handleException(new Exception(""), updateInfo);
        verify(executorsService).execute(ServerOrUnsupportedExceptionExecutor.class, updateInfo);
    }
}