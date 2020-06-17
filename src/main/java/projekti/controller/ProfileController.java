/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import projekti.domain.User;
import projekti.service.AccountService;
import projekti.service.UserService;
import sun.nio.ch.IOUtil;

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
        if (auth == null) {
            return "redirect:/";
        }
        String username = auth.getName();
        System.out.println("username:" + username);
        return "redirect:/profile/" + username;
    }

    @GetMapping("/profile/{username}")
    public String getProfile(Model model, @PathVariable("username") String username) {
        User user = userService.fetch(username);
        Boolean isOwnProfile = false;
        Boolean isAuthenticated = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            isAuthenticated = true;
            if (auth.getName().equals(user.getAccount().getUsername())) {
                isOwnProfile = true;
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isOwnProfile", isOwnProfile);
        return "profile";
    }

    @GetMapping(value = "/profile/{username}/picture", produces = "image/png")
    @ResponseBody
    public byte[] getPicture(@PathVariable("username") String username) {
        User user = userService.fetch(username);
        if (user.getPicture() == null) {

            try {
                Resource resource = new ClassPathResource("static/img/no-pic.png");
                InputStream input = resource.getInputStream();
                return IOUtils.toByteArray(input);
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                return new byte[0];
            } catch (NullPointerException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                return new byte[0];
            }
        }
        return user.getPicture().getContent();
    }

    @Transactional
    @PostMapping("/request")
    public String request(@RequestParam String username) {
        User current = userService.fetch(SecurityContextHolder.getContext().getAuthentication().getName());
        if (current != null) {
            return "redirect:/request";
        }

        return "redirect:/profile";
    }
}
