/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.dao.SkillRepository;
import projekti.dao.SkillVoteRepository;
import projekti.domain.Account;
import projekti.domain.Skill;
import projekti.domain.SkillVote;

/**
 *
 * @author aleksi
 */
@Service
public class SkillService {
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillVoteRepository skillVoteRepository;
    
    public Skill add(String username, String skill) {
        
        Account account = accountService.fetch(username);
        Skill s = new Skill(account, skill, 0);

        return skillRepository.save(s);
    }
    
    public Skill fetch(Long id) {
        return skillRepository.getOne(id);
    }
    
    public List<Skill> listTopSkills(Account user) {
        return skillRepository.findTop3ByAccountOrderByUpvotesDesc(user);
    }
    
    public Skill addVote(Long id, Account voter) {
        Skill skill = skillRepository.getOne(id);

        if (skillVoteRepository.findBySkillAndVoter(skill, voter) != null) {
            return skill;
        }

        SkillVote skillvote = new SkillVote(skill, voter);

        skillVoteRepository.save(skillvote);

        skill.setUpvotes(skill.getUpvotes() + 1);

        return skillRepository.save(skill);
    }
}
