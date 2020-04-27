package org.poc.cpfap.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	private Long targetAccount;
	private String transactionType;
	private String transactionDesc;
	private Long amount;
	private String comment;
	@JsonProperty("transactionDatetime")
	private String transactionDateTime;
	private String accountBalance;
	
	public Long getTargetAccount() {
		return targetAccount;
	}
	public void setTargetAccount(Long targetAccount) {
		this.targetAccount = targetAccount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionDesc() {
		return transactionDesc;
	}
	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
}
