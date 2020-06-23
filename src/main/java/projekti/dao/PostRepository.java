/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.dao;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Post;
import projekti.domain.User;

/**
 *
 * @author aleksi
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByUserIn(Collection<User> users, Pageable pageable);
}
