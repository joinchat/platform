package join.chat.mvp.platform.repository;

import join.chat.mvp.platform.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByPhone(String phone);

    Account findByUsername(String username);
}
