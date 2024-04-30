package github.ricemonger.telegramBot;

import github.ricemonger.telegramBot.client.executors.InputGroup;
import github.ricemonger.telegramBot.client.executors.InputState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateInfoTests {

    public final static UpdateInfo UPDATE_INFO = new UpdateInfo(1,2L,true,"3",true,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_SAME = new UpdateInfo(1,2L,true,"3",true,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_UPDATE_ID = new UpdateInfo(99,2L,true,"3",true,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_CHAT_ID = new UpdateInfo(1,99L,true,"3",true,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_HAS_MESSAGE = new UpdateInfo(1,2L,false,"3",true,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_MESSAGE_TEXT = new UpdateInfo(1,2L,true,"99",true,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_HAS_CALLBACK_QUERY = new UpdateInfo(1,2L,true,"3",false,"4", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_CALLBACK_QUERY_DATA = new UpdateInfo(1,2L,true,"3",true,"99", InputState.BASE, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_INPUT_STATE = new UpdateInfo(1,2L,true,"3",true,"4", InputState.CREDENTIALS_PASSWORD, InputGroup.BASE);

    public final static UpdateInfo UPDATE_INFO_ALT_INPUT_GROUP = new UpdateInfo(1,2L,true,"3",true,"4", InputState.BASE, InputGroup.CREDENTIALS_ADD);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(UPDATE_INFO, UPDATE_INFO_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_UPDATE_ID);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_CHAT_ID);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_HAS_MESSAGE);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_MESSAGE_TEXT);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_HAS_CALLBACK_QUERY);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_CALLBACK_QUERY_DATA);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_INPUT_STATE);
        assertNotEquals(UPDATE_INFO, UPDATE_INFO_ALT_INPUT_GROUP);
    }
}
