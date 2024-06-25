package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.ItemFilterDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramItemFilterPostgresServiceTest {
    @MockBean
    private ItemFilterPostgresRepository repository;
    @Autowired
    private TelegramItemFilterPostgresService service;


}