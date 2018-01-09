package join.chat.mvp.platform.service;

import join.chat.mvp.platform.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;

    @Autowired
    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Account> account = accountService.findByUsername(username);

        if (account.isPresent() && !StringUtils.isEmpty(account.get().getUsername())) {
            // TODO: add support account authorities
            return new User(account.get().getUsername(), account.get().getPassword(), emptyList());
        }

        throw new UsernameNotFoundException(username);
    }
}
