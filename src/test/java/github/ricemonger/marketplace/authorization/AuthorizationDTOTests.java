package github.ricemonger.marketplace.authorization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationDTOTests {

    private static final AuthorizationDTO AUTHORIZATION_DTO = new AuthorizationDTO("1","2","3","4","5","6","7");

    private static final AuthorizationDTO AUTHORIZATION_DTO_SAME = new AuthorizationDTO("1","2","3","4","5","6","7");

    private static final AuthorizationDTO ALT_TICKET = new AuthorizationDTO("alt","2","3","4","5","6","7");

    private static final AuthorizationDTO ALT_PROFILE = new AuthorizationDTO("1","alt","3","4","5","6","7");

    private static final AuthorizationDTO ALT_SPACE = new AuthorizationDTO("1","2","alt","4","5","6","7");

    private static final AuthorizationDTO ALT_SESSION = new AuthorizationDTO("1","2","3","alt","5","6","7");

    private static final AuthorizationDTO ALT_TWO_FACTOR = new AuthorizationDTO("1","2","3","4","alt","6","7");

    private static final AuthorizationDTO ALT_REMEMBER_DEVICE = new AuthorizationDTO("1","2","3","4","5","alt","7");

    private static final AuthorizationDTO ALT_REMEMBER_ME = new AuthorizationDTO("1","2","3","4","5","6","alt");

    @Test
    public void equalsShouldReturnTrueIfSame(){
        assertEquals(AUTHORIZATION_DTO_SAME, AUTHORIZATION_DTO);
    }

    @Test
    public void equalsShouldReturnFalseIfNotSame(){
        assertEquals(ALT_TICKET, AUTHORIZATION_DTO);
        assertEquals(ALT_PROFILE, AUTHORIZATION_DTO);
        assertEquals(ALT_SPACE, AUTHORIZATION_DTO);
        assertEquals(ALT_SESSION, AUTHORIZATION_DTO);
        assertEquals(ALT_TWO_FACTOR, AUTHORIZATION_DTO);
        assertEquals(ALT_REMEMBER_DEVICE, AUTHORIZATION_DTO);
        assertEquals(ALT_REMEMBER_ME, AUTHORIZATION_DTO);
    }
}
