package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UbiUserRepository extends Neo4jRepository<UbiUserEntity, String> {
    void deleteAllByLinkedTelegramUserChatId(String chatId);

    void deleteByLinkedTelegramUserChatIdAndEmail(String chatId, String email);
}
