package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemSaleHistoryPostgresServiceTest {
    @MockBean
    private ItemSaleHistoryPostgresRepository repository;

    @Autowired
    private ItemSaleHistoryPostgresService service;

    @Test
    public void saveAll_should_handle_to_repository() {
        ItemSaleHistory history1 = new ItemSaleHistory();
        history1.setItemId("1");
        ItemSaleHistory history2 = new ItemSaleHistory();
        history2.setItemId("2");

        List<ItemSaleHistory> histories = List.of(history1, history2);

        service.saveAll(histories);

        verify(repository).saveAll(argThat((ArgumentMatcher<Iterable<? extends ItemSaleHistoryEntity>>) argument -> {
            Collection<ItemSaleHistory> historiesArg = new ArrayList<>();
            argument.forEach(entity -> historiesArg.add(entity.toItemSaleHistory()));

            return historiesArg.contains(history1) && historiesArg.contains(history2);
        }));
    }
}