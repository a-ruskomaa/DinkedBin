package projekti.controller;

import projekti.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
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
import projekti.domain.Account;
import projekti.service.AccountService;

/**
 * Controller class that handles user authentication on login and creating and adding new users.
 * 
 * Authentication credentials are stored as objects of type {@link projekti.domain.Account}
 * @author aleksi
 */
@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    
    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            System.out.println("User logged in: redirecting");
            return "redirect:/profile";
        }
        return "login";
    }

    @PostMapping("/login")
    public String dologin() {
        return "login";
    }

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

        Account a = new Account();
        a.setUsername(username);
        a.setPassword(passwordEncoder.encode(password));
        a.setName(name);
        accountService.save(a);
        System.out.println("Adding new user:" + a.getUsername());
        return "redirect:/register?success";
    }

}
