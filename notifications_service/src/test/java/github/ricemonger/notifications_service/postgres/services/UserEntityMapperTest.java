package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.dto_projections.ToBeNotifiedUserProjection;
import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserEntityMapperTest {
    @Autowired
    private UserEntityMapper userEntityMapper;

    @Test
    public void createToBeNotifiedUser_should_return_expected_result() {
        ToBeNotifiedUserProjection projection = new ToBeNotifiedUserProjection();
        projection.setChatId("chatId");
        projection.setPrivateNotificationsEnabledFlag(false);
        projection.setPublicNotificationsEnabledFlag(true);
        projection.setUbiStatsUpdatedNotificationsEnabledFlag(false);
        projection.setTradeManagerNotificationsEnabledFlag(true);
        projection.setAuthorizationNotificationsEnabledFlag(false);

        ToBeNotifiedUser result = userEntityMapper.createToBeNotifiedUser(projection);

        assertEquals(projection.getChatId(), result.chatId());
        assertEquals(projection.getPrivateNotificationsEnabledFlag(), result.privateNotificationsEnabled());
        assertEquals(projection.getPublicNotificationsEnabledFlag(), result.publicNotificationsEnabled());
        assertEquals(projection.getUbiStatsUpdatedNotificationsEnabledFlag(), result.ubiStatsUpdateNotificationsEnabled());
        assertEquals(projection.getTradeManagerNotificationsEnabledFlag(), result.tradeManagerNotificationsEnabled());
        assertEquals(projection.getAuthorizationNotificationsEnabledFlag(), result.authorizationNotificationsEnabled());
    }
}