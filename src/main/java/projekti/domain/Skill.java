package projekti.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends AbstractPersistable<Long> {

    @ManyToOne
    private Account account;
    
    @NotEmpty
    @Size(max=255)
    private String name;
    
//    @Formula("select count(voter_id) from skill_vote where skill_vote.id = id")
    private Integer upvotes;

}
