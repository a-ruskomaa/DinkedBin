/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.service;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projekti.dao.CommentRepository;
import projekti.dao.PostRepository;
import projekti.domain.Comment;
import projekti.domain.Post;
import projekti.domain.User;

/**
 *
 * @author aleksi
 */
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentRepository commentRepository;
    
    public List<Post> fetchLatestPostsByUsers(Collection<User> users, Integer posts) {
        Pageable pageable = PageRequest.of(0, posts, Sort.by(Sort.Direction.DESC, "dateTime"));
        return postRepository.findByUserIn(users, pageable);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post fetch(Long postId) {
        return postRepository.getOne(postId);
    }
    
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
