package com.dawayo.packing.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dawayo.packing.Service.AdminHomeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminHomeController {

    private final AdminHomeService adminHomeService;
    // 관리자 홈 페이지로 이동
    @RequestMapping("/home")
    public String adminHome() {
        // 관리자 홈 페이지로 이동
        return "admin/adminHome"; // adminHome.html로 이동
    }
    
}
