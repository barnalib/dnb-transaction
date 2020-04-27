package org.poc.cpfap.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "transaction_request")
@EntityListeners(AuditingEntityListener.class)
public class TransactionRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(name = "src_acct_no")
	@NotNull
    private Long sourceAccountNumber;

	@Column(name = "target_acct_no")
	@NotNull
    private Long targetAccountNumber;
    
	@Column(name = "transaction_type")
    @NotBlank
    private String transactionType;
    
	@NotNull
    private Long amount;
    
    @NotBlank
    private String description;
    
    @NotBlank
    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(Long sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public Long getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public void setTargetAccountNumber(Long targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
