package github.ricemonger.fast_sell_trade_manager.services;

import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellCommand;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.FastSellManagedUser;
import github.ricemonger.fast_sell_trade_manager.services.factories.PotentialTradeFactory;
import github.ricemonger.fast_sell_trade_manager.services.factories.TradeManagementCommandsFactory;
import github.ricemonger.marketplace.graphQl.common_query_items_prices.CommonQueryItemsPricesGraphQlClientService;
import github.ricemonger.marketplace.graphQl.personal_query_owned_items_prices_and_current_sell_orders.PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService;
import github.ricemonger.utils.DTOs.common.ItemCurrentPrices;
import github.ricemonger.utils.DTOs.personal.FastUserUbiStats;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserFastSellTradesManagerTest {
    @SpyBean
    private UserFastSellTradesManager userFastSellTradesManager;
    @MockBean
    private PersonalQueryOwnedItemsPricesAndCurrentSellOrdersGraphQlClientService personalGraphQlClientService;
    @MockBean
    private CommonQueryItemsPricesGraphQlClientService commonGraphQlClientService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private TradeManagementCommandsFactory tradeManagementCommandsFactory;
    @MockBean
    private TradeManagementCommandsExecutor fastTradeManagementCommandExecutor;

    @Test
    public void submitCreateCommandsTaskByFetchedUserStats_should_add_commands_to_commands_list_if_it_is_empty() throws Exception {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellManagedUser user = mock(FastSellManagedUser.class);

        AuthorizationDTO dto = mock(AuthorizationDTO.class);

        when(user.toAuthorizationDTO()).thenReturn(dto);

        List itemsMedianPriceAndRarity = mock(List.class);

        List<FastSellCommand> commands = new ArrayList<>();
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        FastUserUbiStats savedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId1", 1, 2),
                new ItemCurrentPrices("itemId2", 3, 4)
        ));

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);
        ReflectionTestUtils.setField(userFastSellTradesManager, "savedUserStats", savedUserStats);

        FastUserUbiStats fetchedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId3", 5, 6),
                new ItemCurrentPrices("itemId4", 7, 8)
        ));

        when(commonValuesService.getFastTradeOwnedItemsLimit()).thenReturn(200);

        when(personalGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(same(dto), eq(200))).thenReturn(fetchedUserStats);

        List potentialTrades = mock(List.class);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        when(potentialTradeFactory.createPotentialTradesForUser(same(fetchedUserStats.getItemsCurrentPrices()), same(itemsMedianPriceAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);

        List<FastSellCommand> createdCommands = List.of(command1, command2);

        when(tradeManagementCommandsFactory.createFastSellCommandsForUser(same(user), same(fetchedUserStats.getCurrentSellOrders()), same(fetchedUserStats.getItemsCurrentPrices()), same(itemsMedianPriceAndRarity), same(potentialTrades), eq(3), eq(4))).thenReturn(createdCommands);

        userFastSellTradesManager.submitCreateCommandsTaskByFetchedUserStats(user, itemsMedianPriceAndRarity, 3, 4);

        assertTrue(tasks.size() == 1);

        tasks.get(0).get();

        assertTrue(commands.size() == 2);
        assertTrue(commands.stream().anyMatch(c -> c == command1));
        assertTrue(commands.stream().anyMatch(c -> c == command2));
    }

    @Test
    public void submitCreateCommandsTaskByFetchedUserStats_should_additionally_check_commands_list_before_adding_new_commands() throws Exception {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellManagedUser user = mock(FastSellManagedUser.class);

        AuthorizationDTO dto = mock(AuthorizationDTO.class);

        when(user.toAuthorizationDTO()).thenReturn(dto);

        List itemsMedianPriceAndRarity = mock(List.class);

        List<FastSellCommand> commands = new ArrayList<>();
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        FastUserUbiStats savedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId1", 1, 2),
                new ItemCurrentPrices("itemId2", 3, 4)
        ));

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);
        ReflectionTestUtils.setField(userFastSellTradesManager, "savedUserStats", savedUserStats);

        FastUserUbiStats fetchedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId3", 5, 6),
                new ItemCurrentPrices("itemId4", 7, 8)
        ));

        when(commonValuesService.getFastTradeOwnedItemsLimit()).thenReturn(200);

        when(personalGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(same(dto), eq(200))).then(new Answer<FastUserUbiStats>() {
            @Override
            public FastUserUbiStats answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(100);
                return fetchedUserStats;
            }
        });

        List potentialTrades = mock(List.class);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        when(potentialTradeFactory.createPotentialTradesForUser(same(fetchedUserStats.getItemsCurrentPrices()), same(itemsMedianPriceAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);

        List<FastSellCommand> createdCommands = List.of(command1, command2);

        when(tradeManagementCommandsFactory.createFastSellCommandsForUser(same(user), same(fetchedUserStats.getCurrentSellOrders()), same(fetchedUserStats.getItemsCurrentPrices()), same(itemsMedianPriceAndRarity), same(potentialTrades), eq(3), eq(4))).thenReturn(createdCommands);

        userFastSellTradesManager.submitCreateCommandsTaskByFetchedUserStats(user, itemsMedianPriceAndRarity, 3, 4);

        assertTrue(tasks.size() == 1);

        FastSellCommand command3 = mock(FastSellCommand.class);
        commands.add(command3);

        tasks.get(0).get();

        assertTrue(commands.size() == 1);
        assertTrue(commands.stream().noneMatch(c -> c == command1));
        assertTrue(commands.stream().noneMatch(c -> c == command2));
        assertTrue(commands.stream().anyMatch(c -> c == command3));
    }

    @Test
    public void submitCreateCommandsTaskByFetchedUserStats_should_do_nothing_if_commands_list_is_not_empty() throws Exception {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellManagedUser user = mock(FastSellManagedUser.class);

        AuthorizationDTO dto = mock(AuthorizationDTO.class);

        when(user.toAuthorizationDTO()).thenReturn(dto);

        List itemsMedianPriceAndRarity = mock(List.class);

        List<FastSellCommand> commands = new ArrayList<>();
        commands.add(mock(FastSellCommand.class));
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        FastUserUbiStats savedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId1", 1, 2),
                new ItemCurrentPrices("itemId2", 3, 4)
        ));

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);
        ReflectionTestUtils.setField(userFastSellTradesManager, "savedUserStats", savedUserStats);

        FastUserUbiStats fetchedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId3", 5, 6),
                new ItemCurrentPrices("itemId4", 7, 8)
        ));

        when(commonValuesService.getFastTradeOwnedItemsLimit()).thenReturn(200);

        when(personalGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(same(dto), eq(200))).thenReturn(fetchedUserStats);
        List potentialTrades = mock(List.class);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        when(potentialTradeFactory.createPotentialTradesForUser(same(fetchedUserStats.getItemsCurrentPrices()), same(itemsMedianPriceAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);

        List<FastSellCommand> createdCommands = List.of(command1, command2);

        when(tradeManagementCommandsFactory.createFastSellCommandsForUser(same(user), same(fetchedUserStats.getCurrentSellOrders()), same(fetchedUserStats.getItemsCurrentPrices()), same(itemsMedianPriceAndRarity), same(potentialTrades), eq(3), eq(4))).thenReturn(createdCommands);

        userFastSellTradesManager.submitCreateCommandsTaskByFetchedUserStats(user, itemsMedianPriceAndRarity, 3, 4);

        assertTrue(tasks.size() == 0);

        verify(fastTradeManagementCommandExecutor, never()).executeCommand(any());
        verify(commonGraphQlClientService, never()).fetchLimitedItemsStats(any(), anyInt(), anyInt());
        verify(potentialTradeFactory, never()).createPotentialTradesForUser(any(), any(), anyInt(), anyInt());
        verify(tradeManagementCommandsFactory, never()).createFastSellCommandsForUser(any(), any(), any(), any(), any(), anyInt(), anyInt());
    }

    @Test
    public void submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices_should_add_commands_to_commands_list_if_it_is_empty() throws Exception {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellManagedUser user = mock(FastSellManagedUser.class);
        List itemsMedianPriceAndRarity = mock(List.class);

        AuthorizationDTO authorizationDTO = mock(AuthorizationDTO.class);

        List<FastSellCommand> commands = new ArrayList<>();
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        FastUserUbiStats savedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId1", 1, 2),
                new ItemCurrentPrices("itemId2", 3, 4)
        ));

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);
        ReflectionTestUtils.setField(userFastSellTradesManager, "savedUserStats", savedUserStats);

        when(commonValuesService.getFetchUsersItemsLimit()).thenReturn(200);
        when(commonValuesService.getFetchUsersItemsOffset()).thenReturn(20);

        List<ItemCurrentPrices> fetchedCurrentPrices = List.of(
                new ItemCurrentPrices("itemId1", 5, 6),
                new ItemCurrentPrices("itemId3", 7, 8)
        );

        when(commonGraphQlClientService.fetchLimitedItemsStats(same(authorizationDTO), eq(200), eq(20))).thenReturn(fetchedCurrentPrices);

        List<ItemCurrentPrices> ownedItemsCurrentPrices = List.of(
                new ItemCurrentPrices("itemId1", 5, 6)
        );

        List potentialTrades = mock(List.class);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        when(potentialTradeFactory.createPotentialTradesForUser(argThat(arg -> arg.containsAll(ownedItemsCurrentPrices) && arg.size() == ownedItemsCurrentPrices.size()), same(itemsMedianPriceAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);

        List<FastSellCommand> createdCommands = List.of(command1, command2);


        when(tradeManagementCommandsFactory.createFastSellCommandsForUser(same(user), same(savedUserStats.getCurrentSellOrders()), argThat(arg -> arg.containsAll(ownedItemsCurrentPrices) && arg.size() == ownedItemsCurrentPrices.size()), same(itemsMedianPriceAndRarity), same(potentialTrades), eq(3), eq(4))).thenReturn(createdCommands);

        userFastSellTradesManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(user, authorizationDTO, itemsMedianPriceAndRarity, 3, 4);

        assertTrue(tasks.size() == 1);

        tasks.get(0).get();

        assertTrue(commands.size() == 2);
        assertTrue(commands.stream().anyMatch(c -> c == command1));
        assertTrue(commands.stream().anyMatch(c -> c == command2));
    }

    @Test
    public void submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices_should_additionally_check_if_commands_list_empty_before_adding_new() throws Exception {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellManagedUser user = mock(FastSellManagedUser.class);
        List itemsMedianPriceAndRarity = mock(List.class);

        AuthorizationDTO authorizationDTO = mock(AuthorizationDTO.class);

        List<FastSellCommand> commands = new ArrayList<>();
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        FastUserUbiStats savedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId1", 1, 2),
                new ItemCurrentPrices("itemId2", 3, 4)
        ));

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);
        ReflectionTestUtils.setField(userFastSellTradesManager, "savedUserStats", savedUserStats);

        when(commonValuesService.getFetchUsersItemsLimit()).thenReturn(200);
        when(commonValuesService.getFetchUsersItemsOffset()).thenReturn(20);

        List<ItemCurrentPrices> fetchedCurrentPrices = List.of(
                new ItemCurrentPrices("itemId1", 5, 6),
                new ItemCurrentPrices("itemId3", 7, 8)
        );

        when(commonGraphQlClientService.fetchLimitedItemsStats(same(authorizationDTO), eq(200), eq(20))).then(new Answer<List<ItemCurrentPrices>>() {
            @Override
            public List<ItemCurrentPrices> answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(100);
                return fetchedCurrentPrices;
            }
        });

        List<ItemCurrentPrices> ownedItemsCurrentPrices = List.of(
                new ItemCurrentPrices("itemId1", 5, 6)
        );

        List potentialTrades = mock(List.class);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        when(potentialTradeFactory.createPotentialTradesForUser(argThat(arg -> arg.containsAll(ownedItemsCurrentPrices) && arg.size() == ownedItemsCurrentPrices.size()), same(itemsMedianPriceAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);

        List<FastSellCommand> createdCommands = List.of(command1, command2);


        when(tradeManagementCommandsFactory.createFastSellCommandsForUser(same(user), same(savedUserStats.getCurrentSellOrders()), argThat(arg -> arg.containsAll(ownedItemsCurrentPrices) && arg.size() == ownedItemsCurrentPrices.size()), same(itemsMedianPriceAndRarity), same(potentialTrades), eq(3), eq(4))).thenReturn(createdCommands);

        userFastSellTradesManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(user, authorizationDTO, itemsMedianPriceAndRarity, 3, 4);

        assertTrue(tasks.size() == 1);

        FastSellCommand command3 = mock(FastSellCommand.class);
        commands.add(command3);

        tasks.get(0).get();

        assertTrue(commands.size() == 1);
        assertTrue(commands.stream().noneMatch(c -> c == command1));
        assertTrue(commands.stream().noneMatch(c -> c == command2));
        assertTrue(commands.stream().anyMatch(c -> c == command3));
    }

    @Test
    public void submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices_should_do_nothing_if_commands_is_not_empty() throws Exception {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellManagedUser user = mock(FastSellManagedUser.class);
        List itemsMedianPriceAndRarity = mock(List.class);

        AuthorizationDTO authorizationDTO = mock(AuthorizationDTO.class);

        List<FastSellCommand> commands = new ArrayList<>();
        commands.add(mock(FastSellCommand.class));
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        FastUserUbiStats savedUserStats = new FastUserUbiStats();
        savedUserStats.setCurrentSellOrders(mock(List.class));
        savedUserStats.setItemsCurrentPrices(List.of(
                new ItemCurrentPrices("itemId1", 1, 2),
                new ItemCurrentPrices("itemId2", 3, 4)
        ));

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);
        ReflectionTestUtils.setField(userFastSellTradesManager, "savedUserStats", savedUserStats);

        when(commonValuesService.getFetchUsersItemsLimit()).thenReturn(200);
        when(commonValuesService.getFetchUsersItemsOffset()).thenReturn(20);

        List<ItemCurrentPrices> fetchedCurrentPrices = List.of(
                new ItemCurrentPrices("itemId1", 5, 6),
                new ItemCurrentPrices("itemId3", 7, 8)
        );

        when(commonGraphQlClientService.fetchLimitedItemsStats(same(authorizationDTO), eq(200), eq(20))).thenReturn(fetchedCurrentPrices);

        List<ItemCurrentPrices> ownedItemsCurrentPrices = List.of(
                new ItemCurrentPrices("itemId1", 5, 6)
        );

        List potentialTrades = mock(List.class);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        when(potentialTradeFactory.createPotentialTradesForUser(argThat(arg -> arg.containsAll(ownedItemsCurrentPrices) && arg.size() == ownedItemsCurrentPrices.size()), same(itemsMedianPriceAndRarity), eq(1), eq(2))).thenReturn(potentialTrades);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);

        List<FastSellCommand> createdCommands = List.of(command1, command2);


        when(tradeManagementCommandsFactory.createFastSellCommandsForUser(same(user), same(savedUserStats.getCurrentSellOrders()), argThat(arg -> arg.containsAll(ownedItemsCurrentPrices) && arg.size() == ownedItemsCurrentPrices.size()), same(itemsMedianPriceAndRarity), same(potentialTrades), eq(3), eq(4))).thenReturn(createdCommands);

        userFastSellTradesManager.submitCreateCommandsTaskBySavedUserStatsAndFetchedCurrentPrices(user, authorizationDTO, itemsMedianPriceAndRarity, 3, 4);

        assertTrue(tasks.size() == 0);

        verify(fastTradeManagementCommandExecutor, never()).executeCommand(any());
        verify(commonGraphQlClientService, never()).fetchLimitedItemsStats(any(), anyInt(), anyInt());
        verify(potentialTradeFactory, never()).createPotentialTradesForUser(any(), any(), anyInt(), anyInt());
        verify(tradeManagementCommandsFactory, never()).createFastSellCommandsForUser(any(), any(), any(), any(), any(), anyInt(), anyInt());
    }

    @Test
    public void executeFastSellCommands_should_execute_commands_and_clear_tasks_in_commands_list_is_not_empty() {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        FastSellCommand command1 = mock(FastSellCommand.class);
        FastSellCommand command2 = mock(FastSellCommand.class);
        List commands = new ArrayList<>();
        commands.add(command1);
        commands.add(command2);

        Future future1 = mock(Future.class);
        Future future2 = mock(Future.class);
        List tasks = new ArrayList<>();
        tasks.add(future1);
        tasks.add(future2);

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);

        userFastSellTradesManager.executeFastSellCommands();

        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command1));
        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command2));

        verify(future1).cancel(true);
        verify(future2).cancel(true);

        assertTrue(commands.isEmpty());
    }

    @Test
    public void executeFastSellCommands_should_do_nothing_is_commands_is_empty() {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        List commands = new ArrayList<>();

        Future future1 = mock(Future.class);
        Future future2 = mock(Future.class);
        List tasks = List.of(future1, future2);

        ReflectionTestUtils.setField(userFastSellTradesManager, "fastSellCommands", commands);
        ReflectionTestUtils.setField(userFastSellTradesManager, "createFastSellCommandsTasks", tasks);

        userFastSellTradesManager.executeFastSellCommands();

        Mockito.verify(fastTradeManagementCommandExecutor, never()).executeCommand(any());

        verify(future1, never()).cancel(anyBoolean());
        verify(future2, never()).cancel(anyBoolean());
    }

    @Test
    public void createAndExecuteCommandsToKeepOneSellSlotUnused_should_fetch_info_and_execute_commands() {
        userFastSellTradesManager = new UserFastSellTradesManager(personalGraphQlClientService, commonGraphQlClientService, commonValuesService, potentialTradeFactory, tradeManagementCommandsFactory, fastTradeManagementCommandExecutor);

        List itemCurrentPrices = Mockito.mock(List.class);
        List sellTrades = Mockito.mock(List.class);

        AuthorizationDTO authorizationDTO = Mockito.mock(AuthorizationDTO.class);

        FastSellManagedUser user = Mockito.mock(FastSellManagedUser.class);
        when(user.toAuthorizationDTO()).thenReturn(authorizationDTO);
        when(user.getUbiProfileId()).thenReturn("profileId");

        when(commonValuesService.getFastTradeOwnedItemsLimit()).thenReturn(120);

        FastUserUbiStats ubiStats = new FastUserUbiStats();
        ubiStats.setItemsCurrentPrices(itemCurrentPrices);
        ubiStats.setCurrentSellOrders(sellTrades);

        when(personalGraphQlClientService.fetchOwnedItemsCurrentPricesAndSellOrdersForUser(same(authorizationDTO), eq(120))).thenReturn(ubiStats);

        when(commonValuesService.getMinMedianPriceDifference()).thenReturn(1);
        when(commonValuesService.getMinMedianPriceDifferencePercentage()).thenReturn(2);

        List medianPricesAndRarity = Mockito.mock(List.class);

        FastSellCommand command1 = Mockito.mock(FastSellCommand.class);
        FastSellCommand command2 = Mockito.mock(FastSellCommand.class);

        List commands = List.of(command1, command2);
        when(tradeManagementCommandsFactory.createKeepUnusedSlotCommandForUser(same(user), same(sellTrades), same(itemCurrentPrices), same(medianPricesAndRarity), eq(3), eq(4))).thenReturn(commands);

        userFastSellTradesManager.createAndExecuteCommandsToKeepOneSellSlotUnused(user, medianPricesAndRarity, 3, 4);

        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command1));
        Mockito.verify(fastTradeManagementCommandExecutor).executeCommand(same(command2));
    }
}