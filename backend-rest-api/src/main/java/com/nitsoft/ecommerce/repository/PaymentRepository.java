package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    
    Payment findByPaymentId(Long paymentId);
}
