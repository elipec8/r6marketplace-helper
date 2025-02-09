package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.dto_projections.ManageableUserProjection;
import github.ricemonger.trades_manager.services.DTOs.ManageableUser;
import github.ricemonger.trades_manager.services.DTOs.TradeByFiltersManager;
import github.ricemonger.trades_manager.services.DTOs.UbiAccountStats;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityMapperTest {
    @Autowired
    private UserEntityMapper userEntityMapper;
    @MockBean
    private TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;
    @MockBean
    private TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;
    @MockBean
    private UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    @Test
    public void createManageableUser_should_return_expected_result() {
        TradeByFiltersManagerEntity tradeByFiltersManagerEntity1 = Mockito.mock(TradeByFiltersManagerEntity.class);
        TradeByFiltersManagerEntity tradeByFiltersManagerEntity2 = Mockito.mock(TradeByFiltersManagerEntity.class);

        TradeByFiltersManager tradeByFiltersManager1 = Mockito.mock(TradeByFiltersManager.class);
        TradeByFiltersManager tradeByFiltersManager2 = Mockito.mock(TradeByFiltersManager.class);

        Mockito.when(tradeByFiltersManagerEntityMapper.createDTO(tradeByFiltersManagerEntity1)).thenReturn(tradeByFiltersManager1);
        Mockito.when(tradeByFiltersManagerEntityMapper.createDTO(tradeByFiltersManagerEntity2)).thenReturn(tradeByFiltersManager2);

        TradeByItemIdManagerEntity tradeByItemIdManagerEntity1 = Mockito.mock(TradeByItemIdManagerEntity.class);
        TradeByItemIdManagerEntity tradeByItemIdManagerEntity2 = Mockito.mock(TradeByItemIdManagerEntity.class);

        TradeByItemIdManager tradeByItemIdManager1 = Mockito.mock(TradeByItemIdManager.class);
        TradeByItemIdManager tradeByItemIdManager2 = Mockito.mock(TradeByItemIdManager.class);

        Mockito.when(tradeByItemIdManagerEntityMapper.createDTO(tradeByItemIdManagerEntity1)).thenReturn(tradeByItemIdManager1);
        Mockito.when(tradeByItemIdManagerEntityMapper.createDTO(tradeByItemIdManagerEntity2)).thenReturn(tradeByItemIdManager2);

        UbiAccountStatsEntity ubiAccountStatsEntity = Mockito.mock(UbiAccountStatsEntity.class);

        UbiAccountStats ubiAccountStats = Mockito.mock(UbiAccountStats.class);

        Mockito.when(ubiAccountStatsEntityMapper.createDTO(ubiAccountStatsEntity)).thenReturn(ubiAccountStats);

        ManageableUserProjection projection = new ManageableUserProjection();
        projection.setId(1L);
        projection.setUbiAccountStats(ubiAccountStatsEntity);
        projection.setUbiAuthTicket("ubiAuthTicket");
        projection.setUbiSpaceId("ubiSpaceId");
        projection.setUbiSessionId("ubiSessionId");
        projection.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        projection.setUbiRememberMeTicket("ubiRememberMeTicket");
        projection.setTradeByFiltersManagers(List.of(tradeByFiltersManagerEntity1, tradeByFiltersManagerEntity2));
        projection.setTradeByItemIdManagers(List.of(tradeByItemIdManagerEntity1, tradeByItemIdManagerEntity2));
        projection.setSellTradesManagingEnabledFlag(true);
        projection.setSellTradePriorityExpression("sellTradePriorityExpression");
        projection.setBuyTradesManagingEnabledFlag(false);
        projection.setBuyTradePriorityExpression("buyTradePriorityExpression");

        ManageableUser result = userEntityMapper.createManageableUser(projection);

        assertEquals(1L, result.getId());
        assertSame(ubiAccountStats, result.getUbiAccountStats());
        assertEquals("ubiAuthTicket", result.getUbiAuthTicket());
        assertEquals("ubiSpaceId", result.getUbiSpaceId());
        assertEquals("ubiSessionId", result.getUbiSessionId());
        assertEquals("ubiRememberDeviceTicket", result.getUbiRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", result.getUbiRememberMeTicket());
        assertTrue(result.getTradeByFiltersManagers().stream().anyMatch(tm -> tm == tradeByFiltersManager1));
        assertTrue(result.getTradeByFiltersManagers().stream().anyMatch(tm -> tm == tradeByFiltersManager2));
        assertTrue(result.getTradeByItemIdManagers().stream().anyMatch(tm -> tm == tradeByItemIdManager1));
        assertTrue(result.getTradeByItemIdManagers().stream().anyMatch(tm -> tm == tradeByItemIdManager2));
        assertTrue(result.getSellTradesManagingEnabledFlag());
        assertEquals("sellTradePriorityExpression", result.getSellTradePriorityExpression());
        assertFalse(result.getBuyTradesManagingEnabledFlag());
        assertEquals("buyTradePriorityExpression", result.getBuyTradePriorityExpression());
    }
}