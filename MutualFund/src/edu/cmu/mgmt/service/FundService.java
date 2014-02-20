package edu.cmu.mgmt.service;

import java.util.Date;
import java.util.List;

import edu.cmu.mgmt.model.Fund;

public interface FundService {

	public String createFund(Fund fund);

	public List<Fund> listFund();
	
	public long getFundCount();

	public Fund getFundById(Integer fund_id);

	public String updateFundsPrice(List<String> fund_ids, List<String> prices,
			Date date);

	public Date getLastTradingDay();

	public List<Fund> getSearchResult(String value);
}
