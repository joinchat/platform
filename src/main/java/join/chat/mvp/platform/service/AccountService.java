package join.chat.mvp.platform.service;


import join.chat.mvp.platform.essential.RegistrationEntity;
import join.chat.mvp.platform.model.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByPhone(String phone);

    Optional<Account> findByUsername(String username);

    void signUp(final RegistrationEntity essential);
}
