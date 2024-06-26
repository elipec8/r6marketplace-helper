package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputEntityId {
    private TelegramUserEntity telegramUser;
    private InputState inputState;

    public int hashCode(){
        return telegramUser.getChatId().hashCode() + inputState.hashCode();
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof TelegramUserInputEntityId telegramUserInputEntityId)){
            return false;
        }
        return telegramUserInputEntityId.telegramUser.getChatId().equals(telegramUser.getChatId()) && telegramUserInputEntityId.inputState.equals(inputState);
    }
}
