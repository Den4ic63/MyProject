package com.DenforLy.blog.repositori;

import com.DenforLy.blog.model.Post;
import com.DenforLy.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo  extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
