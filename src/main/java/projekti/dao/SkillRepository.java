/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Skill;

/**
 *
 * @author aleksi
 */
public interface SkillRepository extends JpaRepository<Skill, Long> {
    
}
