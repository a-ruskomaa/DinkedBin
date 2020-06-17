/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.time.LocalDate;
import java.util.List;
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
import projekti.domain.Connection;
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

//    @GetMapping(value = "/profile/{username}/picture", produces = "image/png")
//    @ResponseBody
//    public byte[] getPicture(@PathVariable("username") String username) {
//        User user = userService.fetch(username);
//        if (user.getPicture() == null) {
//
//            try {
//                Resource resource = new ClassPathResource("static/img/no-pic.png");
//                InputStream input = resource.getInputStream();
//                return IOUtils.toByteArray(input);
//            } catch (IOException ex) {
//                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
//                return new byte[0];
//            } catch (NullPointerException ex) {
//                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
//                return new byte[0];
//            }
//        }
//        return user.getPicture().getContent();
//    }

}
