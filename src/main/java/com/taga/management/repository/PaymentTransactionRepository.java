package com.taga.management.repository;

import com.taga.management.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    PaymentTransaction findByTxnRef(String txnRef);
}
