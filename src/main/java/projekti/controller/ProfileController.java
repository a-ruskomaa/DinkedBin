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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Boolean isOwnProfile = false;
        Boolean isAuthenticated = false;
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            isAuthenticated = true;
            if (authentication.getName().equals(user.getAccount().getUsername())) {
                isOwnProfile = true;
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isOwnProfile", isOwnProfile);
        System.out.println("hep");
        return "profile";
    }

}
