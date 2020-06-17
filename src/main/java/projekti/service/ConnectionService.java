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

/**
 *
 * @author aleksi
 */
@Service
public class ConnectionService {
        
    @Autowired
    ConnectionRepository connectionRepository;
    
    public Connection create(Connection connection) {
        return connectionRepository.save(connection);
    }
    
    public Connection fetch(Long id) {
        return connectionRepository.getOne(id);
    }

}
