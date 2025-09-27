package com.example.myfirstapplication.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; 

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2) // Coincide con DECIMAL(10,2)
    private BigDecimal amount; 

    @Enumerated(EnumType.STRING) 
    @Column(name = "type", nullable = false)
    private TransactionType type; 

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    // Constructor para crear nuevas instancias (sin ID)
    public Transaction(Long userId, 
                       String description, 
                       BigDecimal amount, 
                       TransactionType type, 
                       LocalDateTime transactionDate) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return type;
    }

    public void setTransactionType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
