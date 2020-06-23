/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author aleksi
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageObject extends AbstractPersistable<Long>{
    
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;
    
}