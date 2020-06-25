package projekti.dao;

import java.util.List;
import projekti.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author aleksi
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    
    List<Account> findByNameContainingIgnoreCase(String string);
}
