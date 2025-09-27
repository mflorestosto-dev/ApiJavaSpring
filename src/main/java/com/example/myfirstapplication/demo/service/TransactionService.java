package com.example.myfirstapplication.demo.service;

import com.example.myfirstapplication.demo.model.Transaction;
import com.example.myfirstapplication.demo.model.TransactionType;
import com.example.myfirstapplication.demo.model.User;
import com.example.myfirstapplication.demo.repository.TransactionRepository;
import com.example.myfirstapplication.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(Long userId, String description, BigDecimal amount, TransactionType type) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario no encontrado.");
        }
        
        // La validación de fondos insuficientes debe ser manejada en el cliente
        // si el balance no se guarda en el servidor.
        
        Transaction newTransaction = new Transaction(userId, description, amount, type, LocalDateTime.now());
        return transactionRepository.save(newTransaction);
    }
    
    public List<Transaction> getTransactionsByUserId(Long userId) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario no encontrado.");
        }
        return transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);
    }
}