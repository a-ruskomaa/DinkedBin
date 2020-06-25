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
import org.springframework.transaction.annotation.Transactional;
import projekti.dao.CommentRepository;
import projekti.dao.PostLikeRepository;
import projekti.dao.PostRepository;
import projekti.domain.Comment;
import projekti.domain.Post;
import projekti.domain.Account;
import projekti.domain.PostLike;

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

    @Autowired
    PostLikeRepository postLikeRepository;

    public List<Post> fetchLatestPostsByUsers(Collection<Account> users, Integer posts) {
        Pageable pageable = PageRequest.of(0, posts, Sort.by(Sort.Direction.DESC, "dateTime"));
        return postRepository.findByAccountIn(users, pageable);
    }
    
    public List<Comment> fetcthLatestComments(Post post, Integer comments) {
        Pageable pageable = PageRequest.of(0, comments, Sort.by(Sort.Direction.DESC, "dateTime"));
        return commentRepository.findByPost(post, pageable);
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

    @Transactional
    public Post addLike(Post post, Account liker) {
        if (postLikeRepository.findByPostAndLiker(post, liker) != null) {
            return post;
        }

        PostLike pl = new PostLike(post, liker);

        postLikeRepository.save(pl);

        post.setLikes(post.getLikes() + 1);

        return postRepository.save(post);
    }
}
