package com.DenforLy.blog.repositori;

import com.DenforLy.blog.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post,Long> {
    List<Post> findByTitle(String title);
}
