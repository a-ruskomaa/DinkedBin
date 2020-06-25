/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Post;
import projekti.domain.PostLike;
import projekti.domain.Account;

/**
 *
 * @author aleksi
 */
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    public PostLike findByPostAndLiker(Post post, Account liker);
}
