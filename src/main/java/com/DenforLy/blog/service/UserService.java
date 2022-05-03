package com.DenforLy.blog.service;

import com.DenforLy.blog.model.Post;
import com.DenforLy.blog.model.User;
import com.DenforLy.blog.repositori.PostRepo;
import com.DenforLy.blog.repositori.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


    public void addfavorite(User user, Post post) {
        user.getPost().add(post);
        userRepo.save(user);
    }

    public void removefavorite(User user, Post post) {
        user.getPost().remove(post);
        userRepo.save(user);
    }
}
