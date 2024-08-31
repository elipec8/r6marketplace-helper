package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradingUser;

import java.util.List;

public interface UserDatabaseService {
    List<TradingUser> getAllTradingUsers();
}
