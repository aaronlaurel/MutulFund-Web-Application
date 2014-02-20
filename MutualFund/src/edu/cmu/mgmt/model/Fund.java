package edu.cmu.mgmt.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "fund")
// @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Fund implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Integer fund_id;
	private String name;
	private String symbol;
	private Date price_date;
	private long price;
	private int version;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "fund_id", nullable = false)
	public Integer getFund_id() {
		return fund_id;
	}

	public void setFund_id(Integer fund_id) {
		this.fund_id = fund_id;
	}

	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.trim(name);
	}

	@Column(name = "symbol", nullable = false, unique = true)
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = StringUtils.trim(symbol);
	}

	@Column(name = "price_date")
	public Date getPrice_date() {
		return price_date;
	}

	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}

	@Column(name = "price")
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