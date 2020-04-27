package org.poc.cpfap.application.repository;

import java.util.List;

import org.poc.cpfap.application.model.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRequestRepository extends JpaRepository<TransactionRequest, Long> {

	List<TransactionRequest> findBySourceAccountNumber(@Param("sourceAccountNumber") long sourceAccountNumber);
}
