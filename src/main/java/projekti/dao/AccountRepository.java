package projekti.dao;

import java.util.List;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    
    List<Account> findByNameContainingIgnoreCase(String string);
}
