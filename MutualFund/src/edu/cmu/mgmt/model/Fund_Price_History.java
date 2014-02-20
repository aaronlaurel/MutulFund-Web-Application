package edu.cmu.mgmt.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "fund_price_history")
// @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Fund_Price_History implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Integer history_id;
	private Integer fund_id;
	private Date price_date;
	private long price;
	private int version;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "history_id", nullable = false)
	public Integer getHistory_id() {
		return history_id;
	}

	public void setHistory_id(Integer history_id) {
		this.history_id = history_id;
	}

	@Column(name = "fund_id", nullable = false)
	public Integer getFund_id() {
		return fund_id;
	}

	public void setFund_id(Integer fund_id) {
		this.fund_id = fund_id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "price_date", nullable = false)
	public Date getPrice_date() {
		return price_date;
	}

	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}

	@Column(name = "price", nullable = false)
	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
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
