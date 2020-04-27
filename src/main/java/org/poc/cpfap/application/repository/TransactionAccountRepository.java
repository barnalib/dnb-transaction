package org.poc.cpfap.application.repository;

import org.poc.cpfap.application.model.TransactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, Long> {

}
