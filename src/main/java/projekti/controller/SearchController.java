package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.service.AccountService;

/**
 * Controller class that handles search queries
 * @author aleksi
 */
@Controller
public class SearchController {
    
    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/search")
    public String search(Model model, Authentication authentication, @RequestParam(value = "query", required = false) String query) {
        Account current = accountService.fetch(authentication.getName());
        if (query != null) {
            model.addAttribute("results", accountService.search(query));
            model.addAttribute("query", query);
        }
        model.addAttribute("current", current);
        return "search";
    }

}
