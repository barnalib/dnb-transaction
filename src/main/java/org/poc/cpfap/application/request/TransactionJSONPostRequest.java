package org.poc.cpfap.application.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionJSONPostRequest {
	
	private Long accountNumber;
	
	@JsonProperty("transactions")
	private List<Transaction> transaction;
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public List<Transaction> getTransaction() {
		return transaction;
	}
	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

}
