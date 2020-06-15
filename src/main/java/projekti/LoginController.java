/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author aleksi
 */
@Controller
public class LoginController {
    
    @PostMapping("/login")
    public String login(@RequestParam("user") String user) {
        return "redirect:/feed";
    }
}
