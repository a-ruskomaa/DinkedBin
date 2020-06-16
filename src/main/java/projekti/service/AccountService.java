/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.dao.AccountRepository;
import projekti.domain.Account;

/**
 *
 * @author aleksi
 */
@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepository;
    
    public Account create(Account account) {
        return accountRepository.save(account);
    }
    
    public Account fetch(Long id) {
        return accountRepository.getOne(id);
    }
    
    public Account fetch(String username) {
        return accountRepository.findByUsername(username);
    }
    
    
    
}
