/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Connection;
import projekti.domain.User;
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

    @PostMapping("/connections/request")
    public String requestConnection(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User requested = userService.fetch(username);
        
        Connection connection = new Connection(current, requested, Boolean.FALSE);
        
        connectionService.save(connection);

        return "redirect:/connections";
    }
    
    @PostMapping("/connections/remove")
    public String removeConnection(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User other = userService.fetch(username);
        
        Connection c = connectionService.fetch(current, other);
        
        if (c == null) {
            c = connectionService.fetch(other, current);
        }
        
        connectionService.remove(c);

        return "redirect:/connections";
    }
    
    @PostMapping("/connections/cancel")
    public String cancelRequest(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User recipient = userService.fetch(username);
        
        
        Connection c = connectionService.fetch(current, recipient);
        
        connectionService.remove(c);

        return "redirect:/connections";
    }

    @PostMapping("/connections/accept")
    public String acceptRequest(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User sender = userService.fetch(username);
        
        Connection c = connectionService.fetch(sender, current);
        
        c.setIsAccepted(true);
        
        connectionService.save(c);

        return "redirect:/connections";
    }

    @PostMapping("/connections/decline")
    public String declineRequest(@RequestParam String username, Authentication authentication) {
        User current = userService.fetch(authentication.getName());

        User sender = userService.fetch(username);
        
        Connection c = connectionService.fetch(sender, current);
        
        connectionService.remove(c);

        return "redirect:/connections";
    }
}
