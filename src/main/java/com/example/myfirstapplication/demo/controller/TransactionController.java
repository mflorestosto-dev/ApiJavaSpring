package com.example.myfirstapplication.demo.controller;

import com.example.myfirstapplication.demo.model.Transaction;
import com.example.myfirstapplication.demo.model.TransactionType;
import com.example.myfirstapplication.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.Data;

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

    @Data
    public static class TransactionRequest {
        private Long originId;
        private Long destinationId;
        private String description;
        private BigDecimal amount;
        private TransactionType type;
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
    @PostMapping("/transaction")
    public ResponseEntity<Map<String, Object>> createTransaction(@RequestBody TransactionRequest transactionRequest){
        Map<String, Object> response = new HashMap<>();
        try {
            transactionService.createTransaction(
                transactionRequest.originId,
                transactionRequest.destinationId,
                transactionRequest.description,
                transactionRequest.amount,
                transactionRequest.type
            );
            response.put("success", true);
            response.put("message", "Transacción creada exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    // Helper class for the POST request body
    
}