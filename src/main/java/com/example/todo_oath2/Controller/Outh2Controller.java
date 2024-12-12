package com.example.todo_oath2.Controller;

import com.example.todo_oath2.dto.UserDto;
import com.example.todo_oath2.model.Role;
import com.example.todo_oath2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Outh2Controller {
    private final UserService userService;

    @GetMapping("/admin/1")
    public String getAdmin() {
        return "Admin only get this page";
    }

    @GetMapping("/user")
    public UserDto user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        Role role = userService.findByEmail(principal.getAttribute("email")).getRole();
        return new UserDto(principal.getAttribute("id"),
                principal.getAttribute("name"),
                principal.getAttribute("email"),
                role);
    }

    @GetMapping("/")
    public String index() {
        return "Get ALL";
    }

}
