/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.dao.ConnectionRepository;
import projekti.domain.Connection;
import projekti.domain.Account;

/**
 *
 * @author aleksi
 */
@Service
public class ConnectionService {
        
    @Autowired
    ConnectionRepository connectionRepository;
    
    @Autowired
    AccountService accountService;
    
    public Connection create(String senderUsername, String recipientUsername) {
        Account sendingUser = accountService.fetch(senderUsername);

        Account receivingUser = accountService.fetch(recipientUsername);
        
        return create(sendingUser, receivingUser);
    }
    
    public Connection create(Account sender, Account recipient) {
        Connection connection = new Connection(sender, recipient, Boolean.FALSE);
        
        return connectionRepository.save(connection);
    }
    
    public Connection accept(Connection connection) {
        connection.setIsAccepted(true);
        return connectionRepository.save(connection);
    }
    
    public Connection accept(String senderUsername, String recipientUsername) {
        Account sender = accountService.fetch(senderUsername);

        Account recipient = accountService.fetch(recipientUsername);
        
        if (sender == null || recipient == null) throw new IllegalArgumentException();
        
        return accept(sender,recipient);
    }
    
    public Connection accept(Account sender, Account recipient) {
        Connection connection = fetch(sender, recipient);
        
        if (connection == null) throw new IllegalArgumentException();
        
        return accept(connection);
    }
    
    public Connection save(Connection connection) {
        return connectionRepository.save(connection);
    }
    
    public Connection fetch(Long id) {
        return connectionRepository.getOne(id);
    }
    
    public Connection fetch(Account sender, Account recipient) {
        return connectionRepository.findBySenderAndRecipient(sender, recipient);
    }
    
    public void remove(Connection connection) {
        connectionRepository.delete(connection);
    }
    
    public void remove(Account first, Account other) {        
        Connection connection = fetch(first, other);
        
        if (connection == null) {
            connection = fetch(other, first);
        }
        
        if (connection == null) throw new IllegalArgumentException();
        
        remove(connection);
    }
    
    public void remove(String usernameFirst, String usernameOther) {        
        Account first = accountService.fetch(usernameFirst);

        Account other = accountService.fetch(usernameOther);
        
        if (first == null || other == null) throw new IllegalArgumentException();
        
        remove(first,other);
    }
    
    

}
