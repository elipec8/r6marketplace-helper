package github.ricemonger.marketplace.authorization;

import lombok.Data;

@Data
public class AuthorizationDTO {

    private String platformType;

    private String ticket;

    private String profileId;

    private String userId;

    private String nameOnPlatform;

    private String environment;

    private String expiration;

    private String spaceId;

    private String clientIp;

    private String clientIpCountry;

    private String serverTime;

    private String sessionId;

    private String sessionKey;

    private String rememberMeTicket;
}
