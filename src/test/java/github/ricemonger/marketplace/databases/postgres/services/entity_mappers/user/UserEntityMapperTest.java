package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.utils.DTOs.ManageableUser;
import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserEntityMapperTest {
    @Autowired
    private UserEntityMapper userEntityMapper;
    @MockBean
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;
    @MockBean
    private TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;
    @MockBean
    private TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    @Test
    public void createManageableUser_should_properly_map_user() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUbiAccountEntry(new UbiAccountEntryEntity(1L, "email","ubiProfileId"));
        entity.getUbiAccountEntry().setUbiSessionId("ubiSessionId");
        entity.getUbiAccountEntry().setUbiSpaceId("ubiSpaceId");
        entity.getUbiAccountEntry().setUbiAuthTicket("ubiAuthTicket");
        entity.getUbiAccountEntry().setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        entity.getUbiAccountEntry().setUbiRememberMeTicket("ubiRememberMeTicket");
        entity.setTelegramUser(new TelegramUserEntity("chatId",1L));
        entity.setPrivateNotificationsEnabledFlag(true);
        entity.setTradeByItemIdManagers(List.of());
        entity.setTradeByFiltersManagers(List.of());

        when(tradeByFiltersManagerEntityMapper.createDTO(any())).thenReturn(new TradeByFiltersManager());
        when(tradeByItemIdManagerEntityMapper.createDTO(any())).thenReturn(new TradeByItemIdManager());
        UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO = new UbiAccountStatsEntityDTO();
        ubiAccountStatsEntityDTO.setUbiProfileId("ubiProfileId");
        when(ubiAccountStatsEntityMapper.createDTO(any())).thenReturn(ubiAccountStatsEntityDTO);

        ManageableUser expected = new ManageableUser();
        expected.setId(1L);
        expected.setUbiAccountStatsEntityDTO(ubiAccountStatsEntityDTO);
        expected.setUbiSessionId("ubiSessionId");
        expected.setUbiSpaceId("ubiSpaceId");
        expected.setUbiAuthTicket("ubiAuthTicket");
        expected.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        expected.setUbiRememberMeTicket("ubiRememberMeTicket");
        expected.setChatId("chatId");
        expected.setPrivateNotificationsEnabledFlag(true);
        expected.setTradeByFiltersManagers(List.of(new TradeByFiltersManager()));
        expected.setTradeByItemIdManagers(List.of(new TradeByItemIdManager()));

        assertEquals(expected, userEntityMapper.createManageableUser(entity));
    }
}