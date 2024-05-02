package github.ricemonger.telegramBot.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CallbackButtonTests {

    public final static CallbackButton CALLBACK_BUTTON = new CallbackButton("text", "data");

    public final static CallbackButton CALLBACK_BUTTON_SAME = new CallbackButton("text", "data");

    public final static CallbackButton CALLBACK_BUTTON_ALT_TEXT = new CallbackButton("text99", "data");

    public final static CallbackButton CALLBACK_BUTTON_ALT_DATA = new CallbackButton("text", "data99");

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(CALLBACK_BUTTON, CALLBACK_BUTTON_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(CALLBACK_BUTTON, CALLBACK_BUTTON_ALT_TEXT);
        assertNotEquals(CALLBACK_BUTTON, CALLBACK_BUTTON_ALT_DATA);
    }
}
