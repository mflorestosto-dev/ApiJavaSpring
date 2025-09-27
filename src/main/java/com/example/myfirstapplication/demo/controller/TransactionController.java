package com.example.myfirstapplication.demo.controller;

import com.example.myfirstapplication.demo.model.Transaction;
import com.example.myfirstapplication.demo.model.TransactionType;
import com.example.myfirstapplication.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    // Endpoint para obtener todas las transacciones de un usuario
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactionsByUserId(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
            response.put("success", true);
            response.put("data", transactions);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> createTransaction(@PathVariable Long userId, @RequestBody TransactionRequest transactionRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Transaction newTransaction = transactionService.createTransaction(
                userId,
                transactionRequest.getDescription(),
                transactionRequest.getAmount(),
                transactionRequest.getType()
            );
            response.put("success", true);
            response.put("message", "Transacción creada exitosamente.");
            response.put("data", newTransaction);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    // Helper class for the POST request body
    
}