package edu.cmu.mgmt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cmu.mgmt.dao.BaseDao;
import edu.cmu.mgmt.model.Fund;
import edu.cmu.mgmt.model.Fund_Price_History;
import edu.cmu.mgmt.service.FundService;
import edu.cmu.mgmt.utils.DoubleLongParseUtil;
import edu.cmu.mgmt.utils.ValidateUtil;

@Service("fundService")
public class FundServiceImpl implements FundService {
	@Autowired
	private BaseDao<Fund> fundDao;

	@Autowired
	private BaseDao<Fund_Price_History> fundPriceHistoryDao;

	public String createFund(Fund fund) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", fund.getName());
		params.put("symbol", fund.getSymbol());
		Fund f = fundDao.get(
				"from Fund f where f.name =:name or f.symbol =:symbol",
				params);
		if (f != null) {
			return "This Fund Name or Fund Ticker has already existed";
		}
		fundDao.save(fund);
		return "";
	}

	public List<Fund> listFund() {
		return fundDao.find("from Fund");
	}
	
	public long getFundCount() {
	    return fundDao.count("select count(*) from Fund");
	}

	public Fund getFundById(Integer fund_id) {
		return fundDao.get(Fund.class, fund_id);
	}

	public String updateFundsPrice(List<String> fund_ids, List<String> prices,
			Date date) {
		if (fund_ids.size() < getFundCount()) {
			return "every fund price should be input!";
		}
		for (int i = 0; i < fund_ids.size(); i++) {
			Fund fund = this.getFundById(Integer.parseInt(fund_ids.get(i)));
			if (fund.getPrice_date() != null
					&& !fund.getPrice_date().before(date)) {
				return "Transition day should be later than the last trading day";
			}
			fund.setPrice(DoubleLongParseUtil.parseAmount(prices.get(i)));
			fund.setPrice_date(date);
			fundDao.update(fund);

			Fund_Price_History fh = new Fund_Price_History();
			fh.setFund_id(fund.getFund_id());
			fh.setPrice_date(fund.getPrice_date());
			fh.setPrice(fund.getPrice());
			fundPriceHistoryDao.save(fh);
		}
		return "";
	}

	public Date getLastTradingDay() {
		List<Fund> list = listFund();
		for (Fund fund : list) {
			if (fund.getPrice_date() != null) {
				return fund.getPrice_date();
			}
		}
		return null;
	}

	public List<Fund> getSearchResult(String value) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("from Fund f where 1=1 ");
		if (ValidateUtil.isValid(value)) {
			hql.append(" and f.name =:name or f.symbol =:symbol");
			params.put("name", value);
			params.put("symbol", value);
		}
		return fundDao.find(hql.toString(), params);
	}
}
