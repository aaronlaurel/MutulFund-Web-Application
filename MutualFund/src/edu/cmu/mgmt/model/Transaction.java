package edu.cmu.mgmt.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import edu.cmu.mgmt.common.TransactionType;

@Entity
@Table(name = "transaction")
// @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Transaction implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Integer transaction_id;
	private Integer customer_id;
	private Integer fund_id;
	private Date execute_date;
	private long shares;
	private TransactionType transaction_type;
	private long amount;
	private boolean isPending;
	private int version;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "transaction_id", nullable = false)
	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}

	@Column(name = "customer_id", nullable = false)
	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	@Column(name = "fund_id")
	public Integer getFund_id() {
		return fund_id;
	}

	public void setFund_id(Integer fund_id) {
		this.fund_id = fund_id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "execute_date")
	public Date getExecute_date() {
		return execute_date;
	}

	public void setExecute_date(Date execute_date) {
		this.execute_date = execute_date;
	}

	@Column(name = "shares")
	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type", nullable = false)
	public TransactionType getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(TransactionType transaction_type) {
		this.transaction_type = transaction_type;
	}

	@Column(name = "amount")
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Column(name = "isPending", nullable = false)
	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
