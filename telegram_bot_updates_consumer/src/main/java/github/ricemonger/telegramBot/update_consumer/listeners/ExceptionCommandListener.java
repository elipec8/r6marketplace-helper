package github.ricemonger.telegramBot.update_consumer.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.executors.ExecutorsService;
import github.ricemonger.telegramBot.update_consumer.executors.exceptions.client.*;
import github.ricemonger.telegramBot.update_consumer.executors.exceptions.server.ServerOrUnsupportedExceptionExecutor;
import github.ricemonger.telegramBot.update_consumer.executors.exceptions.server.TelegramApiRuntimeExceptionExecutor;
import github.ricemonger.telegramBot.update_consumer.executors.exceptions.server.UbiUserAuthorizationServerErrorExceptionExecutor;
import github.ricemonger.utils.exceptions.ClientAbstractException;
import github.ricemonger.utils.exceptions.ServerAbstractException;
import github.ricemonger.utils.exceptions.client.*;
import github.ricemonger.utils.exceptions.server.TelegramApiRuntimeException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionCommandListener {

    private final ExecutorsService executorsService;

    public void handleException(Exception e, UpdateInfo updateInfo) {
        if (e instanceof ClientAbstractException clientError) {
            handleClientException(clientError, updateInfo);
        } else if (e instanceof ServerAbstractException serverError) {
            handleServerException(serverError, updateInfo);
        } else {
            handleUnsupportedException(e, updateInfo);
        }
    }

    private void handleClientException(ClientAbstractException e, UpdateInfo updateInfo) {
        if (e instanceof ItemDoesntExistException) {
            executorsService.execute(ItemDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof ItemFilterDoesntExistException) {
            executorsService.execute(ItemFilterDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof TagDoesntExistException) {
            executorsService.execute(TagDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof TelegramUserAlreadyExistsException) {
            executorsService.execute(TelegramUserAlreadyExistsExceptionExecutor.class, updateInfo);
        } else if (e instanceof TelegramUserDoesntExistException) {
            executorsService.execute(TelegramUserDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof TradeByFiltersManagerDoesntExistException) {
            executorsService.execute(TradeByFilterManagerDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof TradeByItemIdManagerDoesntExistException) {
            executorsService.execute(TradeByItemIdManagerDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof UbiAccountEntryAlreadyExistsException) {
            executorsService.execute(UbiAccountEntryAlreadyExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof UbiAccountEntryDoesntExistException) {
            executorsService.execute(UbiAccountEntryDoesntExistExceptionExecutor.class, updateInfo);
        } else if (e instanceof UbiUserAuthorizationClientErrorException) {
            executorsService.execute(UbiUserAuthorizationClientErrorExceptionExecutor.class, updateInfo);
        } else if (e instanceof UnexpectedDirectCommandException) {
            executorsService.execute(UnexpectedDirectCommandExceptionExecutor.class, updateInfo);
        } else {
            executorsService.execute(ServerOrUnsupportedExceptionExecutor.class, updateInfo);
            log.error("Unhandled client exception: " + e);
            e.printStackTrace();
        }
    }

    private void handleServerException(ServerAbstractException e, UpdateInfo updateInfo) {
        if (e instanceof UbiUserAuthorizationServerErrorException) {
            executorsService.execute(UbiUserAuthorizationServerErrorExceptionExecutor.class, updateInfo);
        } else if (e instanceof TelegramApiRuntimeException) {
            executorsService.execute(TelegramApiRuntimeExceptionExecutor.class, updateInfo);
        } else {
            executorsService.execute(ServerOrUnsupportedExceptionExecutor.class, updateInfo);
        }
    }

    private void handleUnsupportedException(Exception e, UpdateInfo updateInfo) {
        executorsService.execute(ServerOrUnsupportedExceptionExecutor.class, updateInfo);
        log.error("Unhandled exception: " + e);
        e.printStackTrace();
    }
}
