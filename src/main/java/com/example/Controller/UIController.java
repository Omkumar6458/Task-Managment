package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.example.model.User;

@Controller
public class UIController {
	 
	    @Autowired
	    private RestTemplate restTemplate;

	    private static final String AUTH_SERVICE_URL = "http://UserDetails/auth";
	    String url = "http://EMS/EMS/employee/";
	    
   
	    @GetMapping("/")
	    public String home() {
	        return "login";  
	    }

	    @GetMapping("/register")
	    public String showRegisterForm() {
	        return "register";
	    }

	    @PostMapping("/login")
	    public String login(@ModelAttribute User user, Model model) {
	        String url = AUTH_SERVICE_URL + "/login";
	        ResponseEntity<String> response = restTemplate.postForEntity(url, user, String.class);

	        if (response.getBody().equals("Login Successful")) {
	            return "redirect:/dashboard";
	        } else {
	            model.addAttribute("error", "Invalid Credentials!");
	            return "login";
	        }
	    }

	    @PostMapping("/register")
	    public String register(@ModelAttribute User user, Model model) {
	        String url = AUTH_SERVICE_URL + "/register";
	        restTemplate.postForEntity(url, user, User.class);
	        model.addAttribute("message", "User Registered Successfully!");
	        return "login";
	    }

	    @GetMapping("/dashboard")
	    public String dashboard() {
	        return "redirect:http://localhost:9127/EMS/";
	    }

}
