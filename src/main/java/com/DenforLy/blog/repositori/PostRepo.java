package com.DenforLy.blog.repositori;

import com.DenforLy.blog.model.Post;
import com.DenforLy.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post,Long> {
    List<Post> findByTitle(String title);
    List<Post> findByAuthor(User user);
    List<Post> findByFollowing(User user);
    List<Post> findByCategory(String category);
}
