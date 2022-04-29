package com.DenforLy.blog.controllers;

import com.DenforLy.blog.model.Role;
import com.DenforLy.blog.model.User;
import com.DenforLy.blog.repositori.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo usercrud;

    @GetMapping("registration")
    public  String registration(){
        return "registration";
    }

    @PostMapping("registration")
    public String adduser(@RequestParam String login, @RequestParam String password,
                          @RequestParam String name, @RequestParam String telephone,
                          User user, Map<String,Object> model){

        User userS=new User(name,password,login,telephone);
        userS.setActive(true);
        userS.setRoles(Collections.singleton(Role.USER));
        usercrud.save(userS);

        return "redirect:/login";
    }

}