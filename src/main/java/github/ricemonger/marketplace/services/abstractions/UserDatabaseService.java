package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.TradingUser;

import java.util.List;

public interface UserDatabaseService {
    List<TradingUser> getAllTradingUsers();
}
