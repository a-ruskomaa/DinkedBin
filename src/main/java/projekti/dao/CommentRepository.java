/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Comment;
import projekti.domain.Post;

/**
 * 
 * @author aleksi
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Post post, Pageable pageable);
}
