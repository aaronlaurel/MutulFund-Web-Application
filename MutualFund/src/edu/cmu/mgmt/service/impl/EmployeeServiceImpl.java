package edu.cmu.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cmu.mgmt.dao.BaseDao;
import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.Employee;
import edu.cmu.mgmt.service.CustomerService;
import edu.cmu.mgmt.service.EmployeeService;
import edu.cmu.mgmt.service.TransactionService;
import edu.cmu.mgmt.utils.PasswordUtils;
import edu.cmu.mgmt.utils.ValidateUtil;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private BaseDao<Employee> employeeDao;

	@Autowired
	private BaseDao<Customer> customerDao;

	@Autowired
	private CustomerService cutomerService;

	@Autowired
	private TransactionService transactionService;

	public Employee loginEmployee(Employee employee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", employee.getUsername());
		params.put("pwd", PasswordUtils.md5(employee.getPassword()));
		Employee e = employeeDao.get(
				"from Employee e where e.username =:name and e.password =:pwd",
				params);
		return e;
	}

	public boolean createEmployee(Employee employee) {
		Employee e = findEmployeeByUsername(employee);
		if (e == null) {
			employee.setPassword(PasswordUtils.md5(employee.getPassword()));
			employeeDao.save(employee);
			return true;
		} else
			return false;
	}

	public Employee findEmployeeByUsername(Employee employee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", employee.getUsername());
		Employee e = employeeDao.get(
				"from Employee e where e.username =:username", params);
		return e;
	}

	public boolean updatePwd(Employee employee, String newpassword,
			String repassword) {		
		Employee e = loginEmployee(employee);
		if (e != null) {
			e.setPassword(PasswordUtils.md5(newpassword));
			employeeDao.update(e);
			return true;
		} else {
			return false;
		}
	}

	public List<Customer> getSearchResult(String inputValue) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("from Customer c where 1=1 ");
		if (ValidateUtil.isValid(inputValue)) {
			hql.append(" and c.username =:param");
			params.put("param", inputValue);
		}
		return customerDao.find(hql.toString(), params);
	}

}
