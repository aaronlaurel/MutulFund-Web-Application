package edu.cmu.mgmt.model.vo;

import java.util.Date;

public class UserFundVo {

	private Integer fund_id;
	private String name;
	private String symbol;
	private Date price_date;
	private long price;
	private long shares;
	private long availableShare;

	public Integer getFund_id() {
		return fund_id;
	}

	public void setFund_id(Integer fund_id) {
		this.fund_id = fund_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getPrice_date() {
		return price_date;
	}

	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getShares() {
		return shares;
	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public long getAvailableShare() {
		return availableShare;
	}

	public void setAvailableShare(long availableShare) {
		this.availableShare = availableShare;
	}

}
