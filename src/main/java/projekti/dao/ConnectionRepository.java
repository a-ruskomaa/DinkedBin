/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Connection;
import projekti.domain.Account;

/**
 *
 * @author aleksi
 */
public interface ConnectionRepository extends JpaRepository<Connection, Long>{
    public Connection findBySenderAndRecipient(Account sender, Account recipient);
}
