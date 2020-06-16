package projekti.controller;

import java.util.HashSet;
import projekti.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String add(@RequestParam String username, @RequestParam String password, @RequestParam String name) {
        if (accountService.fetch(username) != null) {
            return "redirect:/register";
        }
        
        Account a = new Account(username, passwordEncoder.encode(password));
        a = accountService.create(a);
        User u = new User(a, name, null, new HashSet<Connection>(), new HashSet<Connection>());
        userService.create(u);
        System.out.println("Adding new user:" + a.getUsername());
        return "redirect:/login";
    }

}
