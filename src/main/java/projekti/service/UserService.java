/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.dao.UserRepository;
import projekti.domain.User;

/**
 *
 * @author aleksi
 */
@Service
public class UserService {
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    UserRepository userRepository;
    
    public User create(User user) {
        return userRepository.save(user);
    }
    
    public User fetch(Long id) {
        return userRepository.getOne(id);
    }

    public User fetch(String username) {
        return userRepository.findByUsername(username);
    }
    
}
