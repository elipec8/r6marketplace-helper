package github.ricemonger.telegramBot.update_consumer.executors.credit_transfer;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class TransferCommandExecutor extends AbstractBotCommandExecutor {

    private static final Map<Long, TransferSession> sessions = new HashMap<>();

    @Override
    protected void executeCommand() {
        Long userId = update.getMessage().getFrom().getId();
        sessions.put(userId, new TransferSession());
        askContaReceber(userId);
    }

    private void askContaReceber(Long userId) {
        List<String> contas = getContasDoUsuario(userId);
        if (contas.isEmpty()) {
            sendText("Você ainda não cadastrou contas. Use /credentials para cadastrar.");
            return;
        }

        sendTextWithMarkup(userId, "Selecione a CONTA que vai RECEBER os créditos (colocar item à venda):", contas, "select_receive_account");
    }

    private void askContaEnviar(Long userId) {
        List<String> contas = getContasDoUsuario(userId);
        sendTextWithMarkup(userId, "Selecione a CONTA que vai ENVIAR os créditos (comprar item):", contas, "select_send_account");
    }

    private void askContasLimpar(Long userId) {
        List<String> contas = getContasDoUsuario(userId);
        sendTextWithMarkup(userId, "Selecione as CONTAS que vão LIMPAR ofertas concorrentes (pode escolher várias):", contas, "select_clear_account");
    }

    private List<String> getContasDoUsuario(Long userId) {
        // TODO: buscar contas do usuário via /credentials (exemplo hardcoded)
        return List.of("conta1", "conta2", "conta3", "conta4");
    }

    private void sendTextWithMarkup(Long userId, String text, List<String> options, String callbackPrefix) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (String option : options) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(option);
            button.setCallbackData(callbackPrefix + ":" + option);
            rows.add(Collections.singletonList(button));
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        sendTextWithMarkup(userId, text, markup);
    }

    private void sendTextWithMarkup(Long userId, String text, InlineKeyboardMarkup markup) {
        // método de AbstractBotCommandExecutor para enviar mensagens com teclado inline
        // deve existir algo como sendTextWithInlineKeyboard(userId, text, markup);
        // você adapta para sua implementação
    }

    public static void handleCallback(Long userId, String callbackData) {
        TransferSession session = sessions.get(userId);
        if (session == null) return;

        if (callbackData.startsWith("select_receive_account:")) {
            String conta = callbackData.split(":")[1];
            session.setContaReceber(conta);
            // Chamar método para perguntar conta enviar, deve ser disparado pelo handler
        } else if (callbackData.startsWith("select_send_account:")) {
            String conta = callbackData.split(":")[1];
            session.setContaEnviar(conta);
            // Perguntar contas limpar
        } else if (callbackData.startsWith("select_clear_account:")) {
            String conta = callbackData.split(":")[1];
            session.addContaLimpar(conta);
            // Perguntar se quer adicionar mais ou finalizar
        }
    }
}
