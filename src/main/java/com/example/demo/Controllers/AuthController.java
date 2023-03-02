package com.example.demo.Controllers;

import com.example.demo.Services.Implementations.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс контроллер для авторизации.
 * @autor Шаля Андрей
 * @version 2.0
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    private UserServiceImpl userService;

    public AuthController( UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Проверка авторизации пользователя")
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        String req = userService.login(username,password);
        if (!req.equals("bad")){
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", req);
            return ResponseEntity.ok(response);
        }else {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Проверка авторизации пользователя")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
