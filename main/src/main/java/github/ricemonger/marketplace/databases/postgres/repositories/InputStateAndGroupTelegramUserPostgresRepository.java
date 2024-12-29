package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.tg_user_input_group_and_state.InputStateAndGroupTelegramUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputStateAndGroupTelegramUserPostgresRepository extends JpaRepository<InputStateAndGroupTelegramUserEntity, String> {

}
