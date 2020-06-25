/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Comment;
import projekti.domain.Post;
import projekti.domain.Account;
import projekti.service.AccountService;
import projekti.service.PostService;

/**
 * Controller class that handles getting and adding posts, comments and likes
 * @author aleksi
 */
@Controller
public class FeedController {

    @Autowired
    PostService postService;

    @Autowired
    AccountService accountService;

    @GetMapping("/feed")
    public String getFeed(Model model, Authentication authentication) {
        Account current = accountService.fetch(authentication.getName());

        List<Account> users = current.getConnections();
        users.add(current);

        List<Post> posts = postService.fetchLatestPostsByUsers(users, 25);

        model.addAttribute("current", current);
        model.addAttribute("posts", posts);

        return "feed";
    }

    @PostMapping("/feed/post/add")
    public String addPost(Authentication authentication, @RequestParam("content") String content) {
        if (content.isEmpty() || content.isBlank() || content.length() > 4000) {
            System.out.println("Invalid content!");
            return "redirect:/feed";
        }

        Account current = accountService.fetch(authentication.getName());

        Post post = new Post();
        post.setAccount(current);
        post.setContent(content);
        post.setDateTime(LocalDateTime.now());
        post.setLikes(0);

        postService.save(post);

        return "redirect:/feed";
    }

    @PostMapping("/feed/post/{postid}/comments/add")
    public String addComment(Authentication authentication, @PathVariable("postid") Long postId, @RequestParam("content") String content) {
        if (content.isEmpty() || content.isBlank() || content.length() > 4000) {
            System.out.println("Invalid content!");
            return "redirect:/feed";
        }

        Account current = accountService.fetch(authentication.getName());

        Post post = postService.fetch(postId);

        Comment comment = new Comment(current, post, LocalDateTime.now(), content);

        postService.saveComment(comment);

        return "redirect:/feed";
    }
    
    
    @GetMapping("/feed/post/{postid}/like")
    public String likePost(Authentication authentication, @PathVariable("postid") Long id) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("You can't do that!");
            return "redirect:/profile";
        }
        Account voter = accountService.fetch(authentication.getName());

        Post post = postService.fetch(id);

        postService.addLike(post, voter);

        return "redirect:/feed";
    }

}
