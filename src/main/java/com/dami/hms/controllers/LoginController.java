package com.dami.hms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Controller
public class LoginController {
    //this is stores the admin username tho it is not the best way to work
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @GetMapping("/")
    public String showLoginPage(){return "index";}


    @PostMapping("/menu")
    public String goToMenu(HttpSession session, @RequestParam String username, @RequestParam String password, Model model){
        if(username.equals(adminUsername) && password.equals(adminPassword)){
            LocalDateTime loginTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedLoginTime = loginTime.format(formatter);
            LocalDate today = LocalDate.now();
            session.setAttribute("loginDate", today);
            session.setAttribute("loginTime", formattedLoginTime);
            session.setAttribute("username", adminUsername);

            model.addAttribute("success", "login-Successful");
            System.out.println("the problem is before returning menu.html");
            return "menu";
        }
        else {
            model.addAttribute("error", "login-failed");
            return "index";
        }
    }
}
