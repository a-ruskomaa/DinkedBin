/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author aleksi
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User extends AbstractPersistable<Long>{
    
    @OneToOne
    private Account account;
    
    private String name;
    
    @Lob
    private ImageObject profilePicture;
    
    
    @OneToMany(mappedBy = "user1")
    private Set<Connection> requestedConnections = new HashSet<>();
    
    @OneToMany(mappedBy = "user2")
    private Set<Connection> receivedConnections = new HashSet<>();
    
}