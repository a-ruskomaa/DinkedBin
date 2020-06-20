/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.dao.SkillRepository;
import projekti.dao.SkillVoteRepository;
import projekti.dao.UserRepository;
import projekti.domain.Skill;
import projekti.domain.SkillVote;
import projekti.domain.User;
import projekti.service.AccountService;
import projekti.service.UserService;

/**
 *
 * @author aleksi
 */
@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillVoteRepository skillVoteRepository;

    @RequestMapping(path = "/profile")
    public String redirectToOwnProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        }
        String username = auth.getName();
        System.out.println("username:" + username);
        return "redirect:/profile/" + username;
    }

    @GetMapping("/profile/{username}")
    public String getProfile(Authentication authentication, Model model, @PathVariable("username") String username) {
        User user = userService.fetch(username);
        User current = null;
        Boolean isOwnProfile = false;
        Boolean isAuthenticated = false;
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            isAuthenticated = true;
            current = userService.fetch(authentication.getName());
            if (user.equals(current)) {
                isOwnProfile = true;
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("current", current);
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isOwnProfile", isOwnProfile);
        return "profile";
    }

    @PostMapping("/profile/{username}/skills/add")
    public String addSkill(Authentication authentication, @PathVariable("username") String username, @RequestParam("skill") String skill) {
        if (!authentication.getName().equals(username)) {
            System.out.println("You can't do that!");
            return "redirect:/profile";
        }
        User u = userService.fetch(username);
        Skill s = new Skill(u, skill, 0);

        skillRepository.save(s);

        return "redirect:/profile/" + username;
    }

    @Transactional
    @GetMapping("/profile/{username}/skills/{id}/vote")
    public String voteSkill(Authentication authentication, @PathVariable("username") String username, @PathVariable("id") Long skillId) {
        if (authentication instanceof AnonymousAuthenticationToken || authentication.getName().equals(username)) {
            System.out.println("You can't do that!");
            return "redirect:/profile";
        }
        User voter = userService.fetch(authentication.getName());

        Skill skill = skillRepository.getOne(skillId);
        
        if (skillVoteRepository.findBySkillAndVoter(skill, voter) != null) {
            return "redirect:/profile/" + username;
        }
        
        SkillVote sv = new SkillVote(skill, voter);

        skillVoteRepository.save(sv);

        skill.setUpvotes(skill.getUpvotes() + 1);

        skillRepository.save(skill);

        return "redirect:/profile/" + username;
    }

}
