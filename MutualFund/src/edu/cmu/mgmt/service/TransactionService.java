package edu.cmu.mgmt.service;

import java.util.Date;
import java.util.List;

import edu.cmu.mgmt.model.Fund_Price_History;

public interface TransactionService {

	public boolean saveCheck(int custumerID, long amount);

	public boolean saveBuyFund(int custumerID, int fundID, long amount);

	public boolean saveSellFund(int customer_id, int fund_id, long shares);

	public void saveDeposit(int customer_id, long amount);

	public List<Fund_Price_History> getFundHistory(int fund_id);

	public String executePendingTransaction(List<String> fund_ids,
			List<String> prices, Date date);
	
    public long FindFundHistoryPrice(int fund_id, Date execute_date);
}
