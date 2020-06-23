/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class Comment extends AbstractPersistable<Long>{
    @ManyToOne
    private Account account;
    
    @ManyToOne
    private Post post;
    
    private LocalDateTime dateTime;
    
    @NotEmpty
    @Size(min = 1, max = 4000)
    @Column(length = 4000)
    private String content;
}
