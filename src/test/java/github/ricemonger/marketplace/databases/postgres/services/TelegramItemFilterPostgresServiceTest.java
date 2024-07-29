package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TelegramItemFilterPostgresServiceTest {
    @MockBean
    private ItemFilterPostgresRepository repository;
    @Autowired
    private TelegramUserItemFilterPostgresService service;


}