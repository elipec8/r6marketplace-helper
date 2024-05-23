package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UbiUserNodeRepository extends Neo4jRepository<UbiUserNode, String> {
    void deleteAllByLinkedTelegramUserChatId(String chatId);

    void deleteByLinkedTelegramUserChatIdAndEmail(String chatId, String email);

    Optional<UbiUserNode> findByLinkedTelegramUserChatIdAndEmail(String chatId, String email);
}
