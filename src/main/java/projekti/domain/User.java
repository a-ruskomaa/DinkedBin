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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.WhereJoinTable;
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
    @OneToOne
    private ImageObject picture;
   
    
    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = true")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    )

    private List<User> connections = new ArrayList<>();

    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = false")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    )

    private List<User> requestedConnections = new ArrayList<>();
    
    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = false")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id")
    )

    private List<User> receivedRequests = new ArrayList<>();
}
