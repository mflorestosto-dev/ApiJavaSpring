package com.example.myfirstapplication.demo.controller;

import com.example.myfirstapplication.demo.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.example.myfirstapplication.demo.model.User;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Data
    public static class RegisterRequest {
        public String firstName;
        public String lastName;
        public String email;
        public String password;
    }

    @Data
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.registerUser(request.firstName, request.lastName, request.email, request.password);
            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<User> userOptional = userService.verifyLogin(request.email, request.password);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                response.put("success", true);
                response.put("message", "Inicio de sesión exitoso.");
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("id", user.getId());
                return new ResponseEntity<>(response, HttpStatus.OK); // Código 200 OK
            }   
            else {
                response.put("success", false);
                response.put("message", "Credenciales inválidas.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // Código 401 Unauthorized
            }
        }
            catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al procesar la solicitud de login: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Código 500 Internal Server Error
        }
    }
}