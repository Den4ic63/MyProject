package com.DenforLy.blog.controllers;


import com.DenforLy.blog.model.Post;
import com.DenforLy.blog.model.Role;
import com.DenforLy.blog.model.User;
import com.DenforLy.blog.repositori.PostRepo;
import com.DenforLy.blog.repositori.UserRepo;
import com.DenforLy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;


    @GetMapping("/")
    public String greeting(@RequestParam(required = false,defaultValue = "") String filter,Model model) {
        model.addAttribute("title", "Главная страница");

        Iterable<Post>posts = postRepo.findAll();

        if(filter !=null &&!filter.isEmpty()){
            posts = postRepo.findByTitle(filter);
            model.addAttribute("posts",posts);
            if(((ArrayList<Post>) posts).size()==0) {
                return "redirect:/non";
            }
        }else {
            posts = postRepo.findAll();
        }

        model.addAttribute("posts",posts);
        model.addAttribute("filter",filter);

        return "homepage";
    }

    @GetMapping("/non")
    public String Nothing(Model model) {
        model.addAttribute("title", "Ничего не найдено");
        return "non";
    }

    @PostMapping("homepage")
    public String add(Model model, @AuthenticationPrincipal User user,
                      @RequestParam String title,
                      @RequestParam String category, @RequestParam String price,
                      @RequestParam String view, @RequestParam String consist){
        Post post = new Post(title,price+" byn",category,view,consist,user);
        postRepo.save(post);

        Iterable<Post>posts = postRepo.findAll();
        model.addAttribute("posts",posts);

        return "homepage";
    }

    @GetMapping("post_info{post}")
    public String post_info(@PathVariable Post post, Model model) {
        model.addAttribute("post",post);
        return "post_info";

    }

    @PostMapping("post_info{post}")
    public String post_remove(@RequestParam("id") Post post, Model model) {
        postRepo.delete(post);
        return "redirect:/";
    }

    @GetMapping("post_edit{post}")
    public String post_edit(@PathVariable Post post, Model model) {
        model.addAttribute("post",post);
        return "post_edit";
    }

    @PostMapping("post_edit{post}")
    public String post_edit(@RequestParam("id") Post post,@AuthenticationPrincipal User user, @RequestParam String title, @RequestParam String category, @RequestParam String price
            , @RequestParam String view, @RequestParam String consist , Model model)
    {
        if(user.getId()!=post.getAuthorid()){
            return "redirect:/";
        }else{
            post.setCategory(category);
            post.setConsist(consist);
            post.setTitle(title);
            post.setPrice(price+" byn");
            post.setView(view);
            postRepo.save(post);
            return "redirect:/";
        }

    }

    @GetMapping("/my_posts")
    public String my_post(@AuthenticationPrincipal User user,Model model) {

        Iterable<Post>posts = postRepo.findByAuthor(user);
        model.addAttribute("posts",posts);
        return "my_posts";
    }

    @GetMapping("post_info_all{post}")
    public String post_info_all(@PathVariable Post post,@AuthenticationPrincipal User currentuser, Model model) {
        model.addAttribute("post",post);
        post.setViews(post.getViews()+1);
        postRepo.save(post);
        model.addAttribute("following",post.getFollowing().stream().anyMatch(user -> user.getId().equals(currentuser.getId())));
        Iterable<Post>posts = postRepo.findByCategory(post.getCategory());
        model.addAttribute("dataminning",posts);
        return "post_info_all";
    }

    @GetMapping("post_info_all{post}following")
    public String post_to_follow(@PathVariable Post post,@AuthenticationPrincipal User currentuser, Model model) {

        post.getFollowing().add(currentuser);
        postRepo.save(post);

        return "redirect:/";
    }

    @GetMapping("post_info_all{post}removing")
    public String post_to_unfollow(@PathVariable Post post,@AuthenticationPrincipal User currentuser, Model model) {


        post.getFollowing().removeIf(user -> user.getId().equals(currentuser.getId()));
        postRepo.save(post);

        return "redirect:/";
    }


    @GetMapping("favorite")
    public String favorite(@AuthenticationPrincipal User user,
                           Model model)
    {
        Iterable<Post>posts = postRepo.findByFollowing(user);
        model.addAttribute("follow",posts);

        return "favorite";
    }

}