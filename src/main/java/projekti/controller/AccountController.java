package projekti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import projekti.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Connection;
import projekti.domain.User;
import projekti.service.AccountService;
import projekti.service.UserService;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/profile";
        }
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String add(@RequestParam String username, @RequestParam String password, @RequestParam String name) {
        if (accountService.fetch(username) != null) {
            //TODO: add error message when username in use
            return "redirect:/register?error";
        }

        Account a = new Account(username, passwordEncoder.encode(password));
        a = accountService.create(a);
        User u = new User();
        u.setAccount(a);
        u.setName(name);
        userService.create(u);
        System.out.println("Adding new user:" + a.getUsername());
        return "redirect:/register?success";
    }

}
