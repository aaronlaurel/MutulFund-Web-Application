package edu.cmu.mgmt.model.vo;

import java.util.Date;

import edu.cmu.mgmt.common.TransactionType;

public class TransactionHistoryVo {

	private String fundName;
	private Date transactionDate;
	private long shares;
	private TransactionType transaction_type;
	private long amount;
	private String status;
	private long price;

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public TransactionType getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(TransactionType transaction_type) {
		this.transaction_type = transaction_type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
