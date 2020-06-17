/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import projekti.controller.ProfileController;
import projekti.dao.UserRepository;
import projekti.domain.Account;
import projekti.domain.ImageObject;
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
    
    public User save(User user) {
        return userRepository.save(user);
    }

    public User fetch(Long id) {
        return userRepository.getOne(id);
    }

    public User fetch(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> search(String string) {
        return userRepository.findByNameContainingIgnoreCase(string);
    }

}
