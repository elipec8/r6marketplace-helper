package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramItemFilterPostgresService implements ItemFilterDatabaseService {

    private final ItemFilterPostgresRepository filterRepository;

    private final TelegramUserPostgresRepository userRepository;

    @Override
    @Transactional
    public void save(String chatId, ItemFilter filter) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        filterRepository.save(new ItemFilterEntity(user.getUser(), filter));
    }

    @Override
    public void deleteById(String chatId, String name) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        List<ItemFilterEntity> filters = user.getUser().getItemFilters();

        Iterator<ItemFilterEntity> iterator = filters.iterator();

        while(iterator.hasNext()) {
            ItemFilterEntity filter = iterator.next();
            if(filter.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }

        userRepository.save(user);
    }

    @Override
    public ItemFilter findById(String chatId, String name) throws ItemFilterDoesntExistException {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return filterRepository.findById(new ItemFilterEntityId(user.getUser(), name)).orElseThrow(ItemFilterDoesntExistException::new).toItemFilter();
    }

    @Override
    public Collection<ItemFilter> findAllByUserId(String chatId) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return filterRepository.findAllByUserId(user.getUser().getId()).stream().map(ItemFilterEntity::toItemFilter).toList();
    }
}
