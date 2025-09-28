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

    @Column(name = "origin_id", nullable = false)
    private Long originId; 

    @Column(name = "destination_id", nullable = false)
    private Long destinationId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2) // Coincide con DECIMAL(10,2)
    private BigDecimal amount; 

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING) 
    @Column(name = "type", nullable = false)
    private TransactionType type; 

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    // Constructor para crear nuevas instancias (sin ID)
    public Transaction(Long originId, 
                       Long destinationId,
                       BigDecimal amount, 
                       String description, 
                       TransactionType type, 
                       LocalDateTime transactionDate) {
        this.originId = originId;
        this.destinationId = destinationId;
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

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

       public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return type;
    }

    public void setTransactionType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
