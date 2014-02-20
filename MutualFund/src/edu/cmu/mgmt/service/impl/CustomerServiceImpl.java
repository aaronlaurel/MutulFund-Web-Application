package edu.cmu.mgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cmu.mgmt.dao.BaseDao;
import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.Fund;
import edu.cmu.mgmt.model.Position;
import edu.cmu.mgmt.model.Transaction;
import edu.cmu.mgmt.model.vo.TransactionHistoryVo;
import edu.cmu.mgmt.model.vo.UserFundVo;
import edu.cmu.mgmt.service.CustomerService;
import edu.cmu.mgmt.service.FundService;
import edu.cmu.mgmt.service.TransactionService;
import edu.cmu.mgmt.utils.PasswordUtils;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private BaseDao<Customer> customerDao;

	@Autowired
	private BaseDao<Position> positionDao;

	@Autowired
	private BaseDao<Transaction> transactionDao;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private FundService fundService;

	public Customer loginCustomer(Customer customer) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", customer.getUsername());
		params.put("pwd", PasswordUtils.md5(customer.getPassword()));
		Customer c = customerDao.get(
				"from Customer c where c.username =:name and c.password =:pwd",
				params);
		return c;
	}

	public boolean createCustomer(Customer customer) {
		Customer c = findCustomerByUsername(customer);
		if (c == null) {
			customer.setPassword(PasswordUtils.md5(customer.getPassword()));
			customerDao.save(customer);
			return true;
		} else
			return false;
	}

	public void updateCustomer(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public List<Position> findPosition(Integer customer_Id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customer_Id);
		List<Position> list = positionDao.find(
				"from Position p where p.customer_id=:customerId", params);
		return list;
	}

	@Override
	public List<Customer> listCustomer() {
		List<Customer> list = new ArrayList<Customer>();
		list = customerDao.find("from Customer");
		return list;
	}

	public Customer getCustomerById(Integer customerId) {
		return customerDao.get(Customer.class, customerId);
	}

	public Customer findCustomerByUsername(Customer customer) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", customer.getUsername());
		Customer c = customerDao.get(
				"from Customer c where c.username =:username", params);
		return c;
	}

	public boolean updatePwd(Customer customer, String newpassword,
		String repassword) {
		Customer c = loginCustomer(customer);
		// check current password is correct
		if (c != null) {
			c.setPassword(PasswordUtils.md5(newpassword));
			customerDao.update(c);
			return true;
		} else {
			return false;
		}
	}

	public List<UserFundVo> findUserFundVos(Integer customer_Id) {
		List<Position> list = this.findPosition(customer_Id);
		List<UserFundVo> vos = new ArrayList<UserFundVo>();
		for (Position position : list) {
			Fund fund = fundService.getFundById(position.getFund_id());
			UserFundVo vo = new UserFundVo();
			vo.setFund_id(position.getFund_id());
			vo.setName(fund.getName());
			vo.setPrice(fund.getPrice());
			vo.setPrice_date(fund.getPrice_date());
			vo.setSymbol(fund.getSymbol());
			vo.setShares(position.getShares());
			vo.setAvailableShare(position.getAvailableShares());
			vos.add(vo);
		}
		return vos;
	}

	public UserFundVo findUserFundVo(Integer customer_Id, Integer fund_id) {
		Position position = this.getPosition(customer_Id, fund_id);
		Fund fund = fundService.getFundById(fund_id);
		UserFundVo vo = new UserFundVo();
		vo.setFund_id(position.getFund_id());
		vo.setName(fund.getName());
		vo.setPrice(fund.getPrice());
		vo.setPrice_date(fund.getPrice_date());
		vo.setSymbol(fund.getSymbol());
		vo.setShares(position.getShares());
		vo.setAvailableShare(position.getAvailableShares());
		return vo;
	}

	public Position getPosition(Integer customer_id, Integer fund_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer_id", customer_id);
		params.put("fund_id", fund_id);
		Position p = positionDao
				.get("from Position p where p.customer_id =:customer_id and p.fund_id =:fund_id",
						params);
		return p;
	}

	public List<TransactionHistoryVo> findTransactionByCustomerId(
			Integer customer_id) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customer_id);
		List<Transaction> transactionList = transactionDao
				.find("from Transaction t where t.customer_id=:customerId order by t.execute_date desc",
						params);
		List<TransactionHistoryVo> historyList1 = new ArrayList<TransactionHistoryVo>();
		List<TransactionHistoryVo> pendingList = new ArrayList<TransactionHistoryVo>();
		List<TransactionHistoryVo> historyList = new ArrayList<TransactionHistoryVo>();
		for (int i = 0; i < transactionList.size(); i++) {
			TransactionHistoryVo thv = new TransactionHistoryVo();
			if (transactionList.get(i).getFund_id() != null) {
				int fund_id = transactionList.get(i).getFund_id();
				Fund temp = fundService.getFundById(fund_id);
				thv.setFundName(temp.getName());
				thv.setTransactionDate(transactionList.get(i).getExecute_date());
				thv.setTransaction_type(transactionList.get(i)
						.getTransaction_type());
				thv.setShares(transactionList.get(i).getShares());
				if (transactionList.get(i).getExecute_date() != null) {
					long price = transactionService.FindFundHistoryPrice(temp
							.getFund_id(), transactionList.get(i)
							.getExecute_date());
					thv.setPrice(price);
				}
				thv.setAmount(transactionList.get(i).getAmount());
				if (transactionList.get(i).isPending() == true) {
					thv.setStatus("Pending");
					pendingList.add(thv);
				} else {
					thv.setStatus("Completed");
					historyList1.add(thv);
				}
			} else {
				thv.setFundName("");
				thv.setTransactionDate(transactionList.get(i).getExecute_date());
				thv.setTransaction_type(transactionList.get(i)
						.getTransaction_type());
				thv.setShares(transactionList.get(i).getShares());
				thv.setAmount(transactionList.get(i).getAmount());
				if (transactionList.get(i).isPending() == true) {
					thv.setStatus("Pending");
					pendingList.add(thv);
				} else {
					thv.setStatus("Completed");
					historyList1.add(thv);
				}

			}
		}
		for (int i = 0; i < pendingList.size(); i++) {
			historyList.add(pendingList.get(i));
		}
		for (int i = 0; i < historyList1.size(); i++) {
			historyList.add(historyList1.get(i));
		}
		return historyList;
	}

}
