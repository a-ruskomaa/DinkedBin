/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.dao.ImageRepository;
import projekti.domain.ImageObject;
import projekti.domain.Skill;
import projekti.domain.Account;
import projekti.service.AccountService;
import projekti.service.SkillService;

/**
 * Controller class that handles actions regarding user profiles, such as adding skills,
 * uploading pictures and giving upvotes for other users' skills.
 * @author aleksi
 */
@Controller
public class ProfileController {

    @Autowired
    AccountService accountService;

    @Autowired
    SkillService skillService;
    
    @Autowired
    ImageRepository imageRepository;


    @RequestMapping(path = "/profile")
    public String redirectToOwnProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        }
        
        String username = auth.getName();
        
        return "redirect:/profile/" + username;
    }

    @GetMapping("/profile/{username}")
    public String getProfile(Authentication authentication, Model model, @PathVariable("username") String username) {
        Account user = accountService.fetch(username);
        Account current = null;
        Boolean isOwnProfile = false;
        Boolean isAuthenticated = false;
        
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            isAuthenticated = true;
            current = accountService.fetch(authentication.getName());
            if (user.equals(current)) {
                isOwnProfile = true;
            }
        }
        
        List<Skill> topSkills = skillService.listTopSkills(user);
        
        model.addAttribute("user", user);
        model.addAttribute("current", current);
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("topSkills", topSkills);
        
        return "profile";
    }

    @GetMapping("/profile/{username}/skills")
    public String getProfileSkills(Authentication authentication, Model model, @PathVariable("username") String username) {
        Account user = accountService.fetch(username);
        Account current = null;
        Boolean isOwnProfile = false;
        Boolean isAuthenticated = false;
        
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            isAuthenticated = true;
            current = accountService.fetch(authentication.getName());
            if (user.equals(current)) {
                isOwnProfile = true;
            }
        }
        
        model.addAttribute("user", user);
        model.addAttribute("current", current);
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isOwnProfile", isOwnProfile);
        
        return "profile_skills";
    }

    @PostMapping("/profile/{username}/skills/add")
    public String addSkill(Authentication authentication, @PathVariable("username") String username, @RequestParam("skill") String skill, HttpServletRequest request) {
        if (!authentication.getName().equals(username)) {
            System.out.println("You can't do that!");
            return "redirect:/profile";
        }
        
        if (skill.isEmpty() || skill.isBlank() || skill.length() > 255) {
            System.out.println("Invalid skill text!");
            return "redirect:/profile/" + username + "/skills";
        }
        
        skillService.add(username, skill);

        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }

    @Transactional
    @GetMapping("/profile/{username}/skills/{id}/vote")
    public String voteSkill(Authentication authentication, @PathVariable("username") String username, @PathVariable("id") Long skillId, HttpServletRequest request) {
        if (authentication instanceof AnonymousAuthenticationToken || authentication.getName().equals(username)) {
            System.out.println("You can't do that!");
            return "redirect:/profile";
        }
        Account voter = accountService.fetch(authentication.getName());

        skillService.addVote(skillId, voter);
        
        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }

    @Transactional
    @PostMapping("/profile/{username}/picture/upload")
    public String uploadPicture(Authentication authentication, Model model, @PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws IOException {
        if (!authentication.getName().equals(username)) {
            System.out.println("You can't do that!");
            return "redirect:/profile";
        }
        Account u = accountService.fetch(username);

        if (file.getContentType() == null || !(file.getContentType().equals("image/jpeg"))) {
            System.out.println("Invalid file type");
            model.addAttribute("fileError", "Invalid file type");
            return "redirect:/profile";
        }

        ImageObject oldImage = u.getPicture();
        if (oldImage != null) {
            imageRepository.delete(oldImage);
        }
        
        ImageObject newImage = new ImageObject(file.getBytes());
        newImage = imageRepository.save(newImage);
        u.setPicture(newImage);
        
        accountService.save(u);

        return "redirect:/profile";
    }

    @GetMapping(path = "/profile/{username}/picture/get", produces = "image/jpeg")
    @ResponseBody
    public byte[] getContent(@PathVariable("username") String username) {
        Account u = accountService.fetch(username);
        return u.getPicture().getContent();
    }
    
}
