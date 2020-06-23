/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import javax.persistence.Entity;
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
public class Connection extends AbstractPersistable<Long> {
    @OneToOne
    private Account sender;
    @OneToOne
    private Account recipient;
    private Boolean isAccepted = false;
//    private LocalDate connectedSince;
    
}
