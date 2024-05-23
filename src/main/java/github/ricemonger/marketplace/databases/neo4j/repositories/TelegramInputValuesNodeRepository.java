package github.ricemonger.marketplace.databases.neo4j.repositories;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramInputValuesNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TelegramInputValuesNodeRepository extends Neo4jRepository<TelegramInputValuesNode, String>{

    void deleteAllByOwnerChatId(String chatId);

    TelegramInputValuesNode findByOwnerChatIdAndInputState(String chatId, String inputState);
}
