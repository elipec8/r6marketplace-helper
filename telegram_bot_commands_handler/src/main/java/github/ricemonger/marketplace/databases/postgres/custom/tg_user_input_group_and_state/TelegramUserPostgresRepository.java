package github.ricemonger.marketplace.databases.postgres.custom.tg_user_input_group_and_state;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserPostgresRepository extends JpaRepository<TelegramUserEntity, String> {

}
