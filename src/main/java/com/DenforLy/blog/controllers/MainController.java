package com.DenforLy.blog.controllers;


import com.DenforLy.blog.model.Post;
import com.DenforLy.blog.model.User;
import com.DenforLy.blog.repositori.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private PostRepo postRepo;

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
        Post post = new Post(title,price+"byn",category,view,consist,user);
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
    public String post_edit(@RequestParam("id") Post post, @RequestParam String title, @RequestParam String category, @RequestParam String price
            , @RequestParam String view, @RequestParam String consist , Model model)
    {
        post.setCategory(category);
        post.setConsist(consist);
        post.setTitle(title);
        post.setPrice(price);
        post.setView(view);

        postRepo.save(post);
        return "redirect:/";
    }


}