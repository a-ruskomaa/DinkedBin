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
import projekti.domain.User;

/**
 *
 * @author aleksi
 */
@Service
public class ConnectionService {
        
    @Autowired
    ConnectionRepository connectionRepository;
    
    @Autowired
    UserService userService;
    
    public Connection create(String senderUsername, String recipientUsername) {
        User sendingUser = userService.fetch(senderUsername);

        User receivingUser = userService.fetch(recipientUsername);
        
        return create(sendingUser, receivingUser);
    }
    
    public Connection create(User sender, User recipient) {
        Connection connection = new Connection(sender, recipient, Boolean.FALSE);
        
        return connectionRepository.save(connection);
    }
    
    public Connection accept(Connection connection) {
        connection.setIsAccepted(true);
        return connectionRepository.save(connection);
    }
    
    public Connection accept(String senderUsername, String recipientUsername) {
        User sender = userService.fetch(senderUsername);

        User recipient = userService.fetch(recipientUsername);
        
        if (sender == null || recipient == null) throw new IllegalArgumentException();
        
        return accept(sender,recipient);
    }
    
    public Connection accept(User sender, User recipient) {
        Connection c = fetch(sender, recipient);
        
        if (c == null) throw new IllegalArgumentException();
        
        return accept(c);
    }
    
    public Connection save(Connection connection) {
        return connectionRepository.save(connection);
    }
    
    public Connection fetch(Long id) {
        return connectionRepository.getOne(id);
    }
    
    public Connection fetch(User sender, User recipient) {
        return connectionRepository.findBySenderAndRecipient(sender, recipient);
    }
    
    public void remove(Connection connection) {
        connectionRepository.delete(connection);
    }
    
    public void remove(User first, User other) {        
        Connection c = fetch(first, other);
        
        if (c == null) {
            c = fetch(other, first);
        }
        
        if (c == null) throw new IllegalArgumentException();
        
        remove(c);
    }
    
    public void remove(String usernameFirst, String usernameOther) {        
        User first = userService.fetch(usernameFirst);

        User other = userService.fetch(usernameOther);
        
        if (first == null || other == null) throw new IllegalArgumentException();
        
        remove(first,other);
    }
    
    

}
