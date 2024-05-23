package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbiUserEntityRepository extends JpaRepository<UbiUserEntity, UbiUserEntityId> {
    void deleteAllByChatId(String chatId);

    List<UbiUserEntity> findAllByChatId(String chatId);
}
