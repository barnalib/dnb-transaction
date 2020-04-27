package org.poc.cpfap.application.request;

import java.util.List;

public class TransactionDetailsJSON {
	private String accountNumber;
	private List<TransactionDtl> transactions;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public List<TransactionDtl> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionDtl> transactions) {
		this.transactions = transactions;
	}
}
