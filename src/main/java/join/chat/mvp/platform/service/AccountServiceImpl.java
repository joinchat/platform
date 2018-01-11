package join.chat.mvp.platform.service;


import join.chat.mvp.platform.configuration.PasswordConfiguration;
import join.chat.mvp.platform.essential.RegistrationEntity;
import join.chat.mvp.platform.model.Account;
import join.chat.mvp.platform.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordConfiguration passwordConfiguration;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordConfiguration passwordConfiguration) {
        this.accountRepository = accountRepository;
        this.passwordConfiguration = passwordConfiguration;
    }

    @Override
    public Optional<Account> findByPhone(String phone) {
        final Account account = accountRepository.findByPhone(phone);
        return account != null ? Optional.of(account) : Optional.empty();
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        final Account account = accountRepository.findByUsername(username);
        return account != null ? Optional.of(account) : Optional.empty();
    }

    @Override
    @Transactional
    public void signUp(final String phone, final RegistrationEntity essential) {
        final Account account = new Account();
        final PasswordEncoder passwordEncoder = passwordConfiguration.passwordEncoder();

        account.setPhone(phone);
        account.setUsername(essential.getUsername());
        account.setPassword(passwordEncoder.encode(essential.getPassword()));

        accountRepository.save(account);
    }
}
