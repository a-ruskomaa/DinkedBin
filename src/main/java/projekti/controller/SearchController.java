package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.User;
import projekti.service.AccountService;
import projekti.service.UserService;

@Controller
public class SearchController {
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/search")
    public String search(Model model, Authentication authentication, @RequestParam(value = "query", required = false) String query) {
        User current = userService.fetch(authentication.getName());
        if (query != null) {
            model.addAttribute("results", userService.search(query));
            model.addAttribute("query", query);
        }
        model.addAttribute("current", current);
        return "search";
    }

}
