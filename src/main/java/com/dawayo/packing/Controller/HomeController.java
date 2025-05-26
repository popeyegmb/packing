package com.dawayo.packing.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.ResponseBody;

import com.dawayo.packing.Service.UserService;
import com.dawayo.packing.VO.UserVO;

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
public String loginPost(UserVO userVO) {
    System.err.println("Login attempt with user: " + userVO.getUserid());
    userService.login(userVO);
    
    return "redirect:/";
}
}