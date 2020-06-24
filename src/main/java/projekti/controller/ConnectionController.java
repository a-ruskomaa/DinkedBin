/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Account;
import projekti.service.AccountService;
import projekti.service.ConnectionService;

/**
 * Controller class that handles getting and altering {@link projekti.domain.Connection}s between two distinct {@link projekti.domain.Account}s.
 * @author aleksi
 */
@Controller
public class ConnectionController {
    
    @Autowired
    AccountService accountService;

    @Autowired
    ConnectionService connectionService;

    @GetMapping("/connections")
    public String getConnections(Model model, Authentication authentication) {
        Account current = accountService.fetch(authentication.getName());
        
        model.addAttribute("connections", current.getConnections());
        model.addAttribute("requests", current.getRequestedConnections()); 
        model.addAttribute("received", current.getReceivedRequests());

        return "connections";
    }

    @PostMapping("/connections/request")
    public String requestConnection(@RequestParam String username, Authentication authentication, HttpServletRequest request) {
        connectionService.create(authentication.getName(), username);

        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }
    
    @PostMapping("/connections/cancel")
    public String cancelRequest(@RequestParam String username, Authentication authentication, HttpServletRequest request) {
        connectionService.remove(authentication.getName(), username);

        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }
    
    @PostMapping("/connections/accept")
    public String acceptRequest(@RequestParam String username, Authentication authentication, HttpServletRequest request) {
        connectionService.accept(username, authentication.getName());

        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }

    @PostMapping("/connections/decline")
    public String declineRequest(@RequestParam String username, Authentication authentication, HttpServletRequest request) {
        connectionService.remove(username, authentication.getName());

        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }
    
    @PostMapping("/connections/remove")
    public String removeConnection(@RequestParam String username, Authentication authentication, HttpServletRequest request) {
        connectionService.remove(authentication.getName(), username);

        String referer = request.getHeader("Referer");
        
        return "redirect:" + referer;
    }

}
