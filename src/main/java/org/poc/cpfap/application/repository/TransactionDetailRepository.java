package org.poc.cpfap.application.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.poc.cpfap.application.model.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
	
	List<TransactionDetail> findByAccountNumberOrderByIdDesc(@Param("accountNumber") long accountNumber);

	TransactionDetail findByTransactionIdOrderByIdDesc(@Param("transactionId") String transactionId);
}
