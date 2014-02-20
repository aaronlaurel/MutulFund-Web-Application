package edu.cmu.mgmt.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "customer")
// @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Customer implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Integer customer_id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String addr_line1;
	private String addr_line2;
	private String city;
	private String state;
	private String zip;
	private long cash;
	private long availableCash;
	private int version;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customer_id", nullable = false)
	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	@Column(name = "username", unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = StringUtils.trim(username);
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "firstname", nullable = false)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = StringUtils.trim(firstname);
	}

	@Column(name = "lastname", nullable = false)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = StringUtils.trim(lastname);
	}

	@Column(name = "addr_line1")
	public String getAddr_line1() {
		return addr_line1;
	}

	public void setAddr_line1(String addr_line1) {
		this.addr_line1 = StringUtils.trim(addr_line1);
	}

	@Column(name = "addr_line2")
	public String getAddr_line2() {
		return addr_line2;
	}

	public void setAddr_line2(String addr_line2) {
		this.addr_line2 = StringUtils.trim(addr_line2);
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = StringUtils.trim(city);
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = StringUtils.trim(state);
	}

	@Column(name = "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = StringUtils.trim(zip);
	}

	@Column(name = "cash", nullable = false)
	public long getCash() {
		return cash;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}

	@Column(name = "availableCash", nullable = false)
	public long getAvailableCash() {
		return availableCash;
	}

	public void setAvailableCash(long availableCash) {
		this.availableCash = availableCash;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Customer other = (Customer) obj;
		if (this.customer_id != other.customer_id
				&& (this.customer_id == null || !this.customer_id
						.equals(other.customer_id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash
				+ (this.customer_id != null ? this.customer_id.hashCode() : 0);
		return hash;
	}

}
