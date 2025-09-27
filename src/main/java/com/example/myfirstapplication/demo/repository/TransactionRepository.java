package com.example.myfirstapplication.demo.repository;

import com.example.myfirstapplication.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Método para encontrar transacciones por ID de usuario, ordenadas por fecha descendente
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);
}
