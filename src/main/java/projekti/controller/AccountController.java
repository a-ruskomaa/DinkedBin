package projekti.controller;

import javax.validation.Valid;
import projekti.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.service.AccountService;

/**
 * Controller class that handles user authentication on login and creating and
 * adding new users.
 *
 * Authentication credentials are stored as objects of type
 * {@link projekti.domain.Account}
 *
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
    public String list(@ModelAttribute Account account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/profile";
        }
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String add(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (accountService.fetch(account.getUsername()) != null) {
            FieldError error = new FieldError("account", "username",
                    "Username is already in use");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountService.save(account);
        System.out.println("Adding new user account:" + account.getUsername());
        return "redirect:/register?success";
    }

}
