package edu.cmu.mgmt.service;

import java.util.List;

import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.Position;
import edu.cmu.mgmt.model.vo.TransactionHistoryVo;
import edu.cmu.mgmt.model.vo.UserFundVo;

public interface CustomerService {

	public Customer loginCustomer(Customer customer);

	public Customer getCustomerById(Integer customerId);

	public List<Position> findPosition(Integer customer_id);

	public boolean createCustomer(Customer customer);

	public List<Customer> listCustomer();

	public Customer findCustomerByUsername(Customer customer);

	public boolean updatePwd(Customer customer, String newpassword,
			String repassword);

	public List<UserFundVo> findUserFundVos(Integer customer_Id);

	public Position getPosition(Integer customer_id, Integer fund_id);

	public List<TransactionHistoryVo> findTransactionByCustomerId(
			Integer customer_id);

	public UserFundVo findUserFundVo(Integer customer_Id, Integer fund_id);

	public void updateCustomer(Customer customer);

}
