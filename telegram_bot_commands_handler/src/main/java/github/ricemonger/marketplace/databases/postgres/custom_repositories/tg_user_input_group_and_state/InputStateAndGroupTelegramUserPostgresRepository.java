package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_group_and_state;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InputStateAndGroupTelegramUserPostgresRepository extends JpaRepository<InputStateAndGroupTelegramUserEntity, String> {

}
