package join.chat.mvp.platform.repository;

import join.chat.mvp.platform.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository extends JpaRepository<Validation, Long> {
    Validation findByCode(String code);

    Validation findByNumber(String number);
}
