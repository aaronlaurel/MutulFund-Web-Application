package edu.cmu.mgmt.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "position")
// @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Position implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Integer position_id;
	private Integer customer_id;
	private Integer fund_id;
	private long shares;
	private long availableShares;
	private int version;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "position_id", nullable = false)
	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
	}

	@Column(name = "customer_id", nullable = false)
	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	@Column(name = "fund_id", nullable = false)
	public Integer getFund_id() {
		return fund_id;
	}

	public void setFund_id(Integer fund_id) {
		this.fund_id = fund_id;
	}

	@Column(name = "shares", nullable = false)
	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	@Column(name = "availableShares", nullable = false)
	public long getAvailableShares() {
		return availableShares;
	}

	public void setAvailableShares(long availableShares) {
		this.availableShares = availableShares;
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
