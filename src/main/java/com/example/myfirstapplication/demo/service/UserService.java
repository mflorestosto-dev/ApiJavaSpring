package com.example.myfirstapplication.demo.service;

import com.example.myfirstapplication.demo.model.User;
import com.example.myfirstapplication.demo.repository.UserRepository;
import com.example.myfirstapplication.demo.repository.TransactionRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TransactionRepository transactionRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public User registerUser(String firstName, String lastName, String email, String password) throws Exception {
        if (firstName == null || firstName.isBlank() ||
            lastName == null || lastName.isBlank() ||
            email == null || email.isBlank() ||
            password == null || password.isBlank()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Formato de email inválido.");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new Exception("El email ya está registrado.");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(firstName, lastName, email, hashedPassword, LocalDateTime.now());
        return userRepository.save(newUser);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public Optional<User> verifyLogin(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return Optional.empty(); // Credenciales vacías
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPasswordHash())) {
            return userOptional; // Usuario no encontrado
        }
        // Compara la contraseña proporcionada con el hash almacenado
        // passwordEncoder.matches(rawPassword, encodedPassword)
        return Optional.empty(); // Credenciales inválidas
    }
}