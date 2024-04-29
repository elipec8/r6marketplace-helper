package github.ricemonger.marketplace.authorization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationBodyValueTests {

    private static final AuthorizationBodyValue TRUE_VALUE_1 = new AuthorizationBodyValue(true);

    private static final AuthorizationBodyValue TRUE_VALUE_2 = new AuthorizationBodyValue(true);

    private static final AuthorizationBodyValue FALSE_VALUE = new AuthorizationBodyValue(false);

    @Test
    public void equalsShouldReturnTrueIfSame(){
        assertEquals(TRUE_VALUE_1, TRUE_VALUE_2);
    }

    @Test
    public void equalsShouldReturnFalseIfNotSame(){
        assertNotEquals(TRUE_VALUE_1, FALSE_VALUE);
    }
}
