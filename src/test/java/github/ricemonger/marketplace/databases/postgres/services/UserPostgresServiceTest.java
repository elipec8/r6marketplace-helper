package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserPostgresServiceTest {
    @Autowired
    private UserPostgresService userPostgresService;
    @Autowired
    private UserPostgresRepository userPostgresRepository;

    @BeforeEach
    void setUp() {
        userPostgresRepository.deleteAll();
    }

    @Test
    public void getAllTradingUsers_should_return_only_users_with_required_fields(){

    }
}