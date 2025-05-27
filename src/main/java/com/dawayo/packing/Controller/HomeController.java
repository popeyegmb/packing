package com.dawayo.packing.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.dawayo.packing.Service.UserService;
import com.dawayo.packing.VO.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home(){
        return "home"; 
    }

    
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userVO", new UserVO());

        return "login"; 
    }

    @PostMapping("/loginProcess")
public String loginPost(UserVO userVO, HttpServletRequest request, HttpServletResponse response) {
    System.err.println("Login attempt with user: " + userVO.getUserid());
    UserVO foundUser = userService.login(userVO);
    if (foundUser != null) {
        System.err.println("Login successful for user: " + foundUser.getUserid());
        System.err.println("User details: " + foundUser.toString());

        HttpSession session = request.getSession(true); 

        session.setAttribute("id", foundUser.getId()); 
        return "redirect:/"; 
    }
    System.err.println("Login failed for user: " + userVO.getUserid());

    
    return "redirect:/";
}
}