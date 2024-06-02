package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.UbiUserDatabaseService;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.marketplace.authorization.AuthorizationService;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationServerErrorException;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiUserService {

    private final UbiUserDatabaseService ubiUserDatabaseService;

    private final AuthorizationService authorizationService;

    public Collection<String> getOwnedItemsIds(String chatId, String email) throws UbiUserDoesntExistException {
        return ubiUserDatabaseService.getOwnedItemsIds(chatId, email);
    }

    public Collection<UbiUser> findAllByLinkedTelegramUserChatId(String chatId) {
        return ubiUserDatabaseService.findAllByChatId(chatId);
    }

    public void deleteAllByLinkedTelegramUserChatId(String chatId) {
        ubiUserDatabaseService.deleteAllByChatId(chatId);
    }

    public void deleteByLinkedTelegramUserChatIdAndEmail(String chatId, String email) {
        ubiUserDatabaseService.deleteById(chatId, email);
    }

    public void authorizeAndSaveUser(String chatId, String email, String password) throws
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        AuthorizationDTO userAuthorizationDTO = authorizationService.authorizeAndGetDTO(email, password);

        ubiUserDatabaseService.save(buildUbiUser(chatId, email, authorizationService.getEncodedPassword(password),userAuthorizationDTO));
    }

    public Collection<UbiUser> reauthorizeAllUbiUsersAndGetUnauthorizedList(){
        List<UbiUser> users = new ArrayList<>(ubiUserDatabaseService.findAll());

        List<UbiUser> unauthorizedUsers = new ArrayList<>();

        for(UbiUser user: users){
            try {
                reauthorizeAndSaveUser(user.getChatId(), user.getEmail(), user.getEncodedPassword());
            }
            catch(UbiUserAuthorizationClientErrorException | UbiUserAuthorizationServerErrorException e){
                unauthorizedUsers.add(user);
            }
        }

        return unauthorizedUsers;
    }
    private void reauthorizeAndSaveUser(String chatId, String email, String encodedPassword) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        AuthorizationDTO authorizationDTO = authorizationService.authorizeAndGetDtoForEncodedPassword(email, encodedPassword);

        ubiUserDatabaseService.save(buildUbiUser(chatId, email, encodedPassword,authorizationDTO));
    }

    private UbiUser buildUbiUser(String chatId, String email, String password, AuthorizationDTO authorizationDTO) {
        UbiUser user = new UbiUser();

        user.setChatId(chatId);
        user.setEmail(email);
        user.setEncodedPassword(password);

        user.setUbiProfileId(authorizationDTO.getProfileId());
        user.setUbiSessionId(authorizationDTO.getSessionId());
        user.setUbiAuthTicket(authorizationDTO.getTicket());
        user.setUbiSpaceId(authorizationDTO.getSpaceId());
        user.setUbiRememberMeTicket(authorizationDTO.getRememberMeTicket());
        user.setUbiRememberDeviceTicket(authorizationDTO.getRememberDeviceTicket());
        user.setUbiTwoFactorAuthTicket(authorizationDTO.getTwoFactorAuthenticationTicket());
        return user;
    }
}
