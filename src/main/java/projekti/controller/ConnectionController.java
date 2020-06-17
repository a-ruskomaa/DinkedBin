/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import projekti.service.ConnectionService;
import projekti.service.UserService;

/**
 *
 * @author aleksi
 */
@Controller
public class ConnectionController {

    @Autowired
    UserService userService;

    @Autowired
    ConnectionService connectionService;

    @GetMapping("/connections")
    public String getConnections(Model model, Authentication authentication) {
        User current = userService.fetch(authentication.getName());
        
        model.addAttribute("connections", current.getConnections());
        model.addAttribute("requests", current.getRequestedConnections()); 
        model.addAttribute("received", current.getReceivedRequests());

        return "connections";
    }

    @Transactional
    @PostMapping("/connections/request")
    public String requestConnection(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User requested = userService.fetch(username);
        
        Connection connection = new Connection(current, requested, Boolean.FALSE, LocalDate.MIN);
        
        connectionService.create(connection);

        return "redirect:/connections";
    }

    @Transactional
    @PostMapping("/connections/accept")
    public String acceptConnection(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User requestedBy = userService.fetch(username);

        return "redirect:/connections";
    }

    @Transactional
    @PostMapping("/connections/decline")
    public String declineConnection(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User requestedBy = userService.fetch(username);
        Connection connection;
        

        userService.save(current);

        return "redirect:/connections";
    }
}
