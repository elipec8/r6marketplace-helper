package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntityId;
import github.ricemonger.marketplace.databases.postgres.mappers.UbiUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.UbiUserDatabaseService;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UbiUserPostgresService implements UbiUserDatabaseService {

    private final UbiUserPostgresRepository ubiUserRepository;

    private final UbiUserPostgresMapper mapper;

    @Override
    public void save(UbiUser user) {
        ubiUserRepository.save(mapper.mapUbiUserEntity(user));
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
    public Collection<String> getOwnedItemsIds(String chatId, String email) throws UbiUserDoesntExistException{
        UbiUser user = findById(chatId, email);

        return user.getOwnedItemsIds();
    }

    @Override
    public UbiUser findById(String chatId, String email) throws UbiUserDoesntExistException {
        try {
            TelegramLinkedUbiUserEntity entity = ubiUserRepository.findById(new TelegramLinkedUbiUserEntityId(chatId, email)).orElseThrow();
            return mapper.mapUbiUser(entity);
        }
        catch (NoSuchElementException e) {
            throw new UbiUserDoesntExistException("User with chatId " + chatId + " and email " + email + " doesn't exist");
        }
    }

    @Override
    public Collection<UbiUser> findAllByChatId(String chatId) {
        return mapper.mapUbiUsers(ubiUserRepository.findAllByChatId(chatId));
    }

    @Override
    public Collection<UbiUser> findAll() {
        return mapper.mapUbiUsers(ubiUserRepository.findAll());
    }
}
