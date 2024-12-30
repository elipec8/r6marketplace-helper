package github.ricemonger.marketplace.databases.postgres.custom.inputs.service;

import github.ricemonger.marketplace.databases.postgres.custom.inputs.entities.TelegramUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserPostgresRepository extends JpaRepository<TelegramUserEntity, String> {
}
