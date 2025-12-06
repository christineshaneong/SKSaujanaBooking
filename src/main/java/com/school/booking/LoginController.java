package com.school.booking;

import jakarta.servlet.http.HttpSession; // Import Session
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, HttpSession session) {
        
        // LOGIC: Check if the email contains the word "admin"
        if (email.contains("admin")) {
            session.setAttribute("role", "ADMIN");
            System.out.println("Logged in as ADMIN");
        } else {
            session.setAttribute("role", "USER");
            System.out.println("Logged in as USER");
        }

        return "redirect:/calendar";
    }
    
    // Optional: Logout to clear the session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}