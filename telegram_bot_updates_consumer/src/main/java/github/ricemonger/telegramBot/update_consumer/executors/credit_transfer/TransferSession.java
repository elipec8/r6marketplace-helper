package github.ricemonger.telegramBot.update_consumer.executors.credit_transfer;

import java.util.ArrayList;
import java.util.List;

public class TransferSession {
    private String contaReceber;
    private String contaEnviar;
    private List<String> contasLimpar = new ArrayList<>();

    public String getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(String contaReceber) {
        this.contaReceber = contaReceber;
    }

    public String getContaEnviar() {
        return contaEnviar;
    }

    public void setContaEnviar(String contaEnviar) {
        this.contaEnviar = contaEnviar;
    }

    public List<String> getContasLimpar() {
        return contasLimpar;
    }

    public void addContaLimpar(String conta) {
        if(!contasLimpar.contains(conta)) {
            contasLimpar.add(conta);
        }
    }
}
