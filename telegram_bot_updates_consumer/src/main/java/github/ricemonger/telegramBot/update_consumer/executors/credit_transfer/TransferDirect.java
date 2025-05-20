package github.ricemonger.telegramBot.update_consumer.executors.credit_transfer;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import org.springframework.stereotype.Component;

@Component
public class TransferDirect extends AbstractBotCommandExecutor {

    @Override
protected void executeCommand() {
    sendText("🔁 Iniciando processo de transferência de créditos.");

    sendText("1️⃣ Qual conta deve RECEBER os créditos? (Conta 1 - Vendedor)");
    String receiver = receiveTextInput();

    sendText("2️⃣ Qual conta vai ENVIAR os créditos? (Conta 2 - Comprador)");
    String sender = receiveTextInput();

    sendText("3️⃣ Nome exato do item a ser listado?");
    String itemName = receiveTextInput();

    sendText("4️⃣ Valor (em créditos) que será listado?");
    String price = receiveTextInput();

    sendText("5️⃣ Deseja usar contas auxiliares para limpar o mercado? (sim/não)");
    boolean useHelpers = receiveYesOrNo();

    sendText("🚀 Executando etapas...");

    // 🔽 Etapa 1: Conta 1 lista o item
    sendText("📤 Conta 1 (" + receiver + ") listando o item...");
    listItemForSale(receiver, itemName, price);

    // 🔽 Etapa 2: Contas auxiliares limpam concorrência
    if (useHelpers) {
        sendText("🧹 Contas auxiliares limpando ofertas concorrentes...");
        cleanMarketWithHelpers(itemName, price);
    }

    // 🔽 Etapa 3: Conta 2 tenta comprar
    sendText("💰 Conta 2 (" + sender + ") tentando comprar o item...");
    boolean success = attemptToBuyItem(sender, receiver, itemName, price);

    if (success) {
        sendText("✅ Transferência realizada com sucesso!");
    } else {
        sendText("❌ Falha na transferência. A conta 2 não conseguiu comprar o item da conta 1.");
    }
}

private String receiveTextInput() {
    return botInnerService.receiveUserText(getUpdateInfo().getChatId());
}

private boolean receiveYesOrNo() {
    String input = botInnerService.receiveUserText(getUpdateInfo().getChatId()).trim().toLowerCase();
    return input.equals("sim") || input.equals("s");
}

private void listItemForSale(String account, String itemName, String price) {
    // TODO: chamar serviço que loga na conta e lista o item no marketplace
    sendText("📝 " + account + " listou '" + itemName + "' por " + price + " créditos.");
}

private void cleanMarketWithHelpers(String itemName, String price) {
    // TODO: chamar serviço para que contas auxiliares comprem ofertas concorrentes
    sendText("⚙️ Limpeza de mercado para '" + itemName + "' até preço de " + price + ".");
}

private boolean attemptToBuyItem(String buyer, String seller, String itemName, String price) {
    // TODO: lógica real de tentativa de compra do item da conta 1
    sendText("🛒 " + buyer + " está tentando comprar de " + seller + "...");
    return true; // Simulação de sucesso
}


}
