package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.UbiUserDatabaseService;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UbiUserPostgresService implements UbiUserDatabaseService {

    private final UbiUserPostgresRepository ubiUserRepository;

    @Override
    public void save(UbiUser user) {
        ubiUserRepository.save(new TelegramLinkedUbiUserEntity(user));
    }

    @Override
    public void deleteById(String chatId, String email) {
        ubiUserRepository.deleteById(new TelegramLinkedUbiUserEntityId(chatId, email));
    }

    @Override
    public void deleteAllByChatId(String chatId) {
        ubiUserRepository.deleteAllByChatId(chatId);
    }

    @Override
    public UbiUser findById(String chatId, String email) throws UbiUserDoesntExistException {
        return ubiUserRepository.findById(new TelegramLinkedUbiUserEntityId(chatId, email)).orElseThrow(() -> new UbiUserDoesntExistException("User with chatId " + chatId + " and email " + email + " doesn't exist")).toUbiUser();
    }

    @Override
    public Collection<UbiUser> findAllByChatId(String chatId) {
        return ubiUserRepository.findAllByChatId(chatId).stream().map(TelegramLinkedUbiUserEntity::toUbiUser).toList();
    }

    @Override
    public Collection<UbiUser> findAll() {
        return ubiUserRepository.findAll().stream().map(TelegramLinkedUbiUserEntity::toUbiUser).toList();
    }
}
