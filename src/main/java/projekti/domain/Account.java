/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 5, max = 30)
    private String username;
    
    @NotEmpty
    private String password;
    
    @NotEmpty
    @Size(min = 5, max = 50)
    private String name;

    @Lob
    @OneToOne
    private ImageObject picture;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Account> connections;

    /*
    * A temporary method to list other users this user is connected to. Due to the
    * symmetrical nature of the table where connections are stored, this user's
    * user_id must match either the sender_id or recipient_id of a connection.
    * Could not figure out how to map it using a custom "where id in A or B" style
    * query, so this is a quick-n-dirty alternative.
    */
    public List<Account> getConnections() {
        this.connections = new ArrayList<Account>();
        this.connections.addAll(getConnectedTo());
        this.connections.addAll(getConnectedFrom());
        return this.connections;
    }

    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = true")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id"))
    private List<Account> connectedTo = new ArrayList<>();

    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = true")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id"))
    private List<Account> connectedFrom = new ArrayList<>();

    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = false")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id"))
    private List<Account> requestedConnections = new ArrayList<>();

    @ManyToMany
    @WhereJoinTable(clause = "is_accepted = false")
    @JoinTable(name = "Connection",
            joinColumns
            = @JoinColumn(name = "recipient_id", referencedColumnName = "id"),
            inverseJoinColumns
            = @JoinColumn(name = "sender_id", referencedColumnName = "id"))
    private List<Account> receivedRequests = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Skill> skills;

}
