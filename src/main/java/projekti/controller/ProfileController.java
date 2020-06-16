/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @GetMapping("/profile/{username}")
    public String getProfile(Model model, @PathVariable("username") String username) {
        User user = userService.fetch(username);
        Boolean isOwnProfile = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getAccount().getUsername())) {
            isOwnProfile = true;
        }
        model.addAttribute("user", user);
        model.addAttribute("isOwnProfile", isOwnProfile);
        return "profile";
    }
    
    @RequestMapping(path = "/profile")
    public String redirectToOwnProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username:" + username);
        return "redirect:/profile/" + username;
    }
}
