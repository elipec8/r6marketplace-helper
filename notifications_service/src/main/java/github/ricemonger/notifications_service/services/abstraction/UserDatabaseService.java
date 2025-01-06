package github.ricemonger.notifications_service.services.abstraction;

import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;

import java.util.List;

public interface UserDatabaseService {
    ToBeNotifiedUser getToBeNotifiedUser(Long userId);

    List<ToBeNotifiedUser> getAllToBeNotifiedUsers();
}
