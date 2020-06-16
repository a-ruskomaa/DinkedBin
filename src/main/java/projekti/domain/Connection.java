/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Connection extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user1;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user2;
    @ManyToOne
    private User requestedBy;
    private Boolean isAccepted = false;
    private LocalDate connectedSince;
    
}
