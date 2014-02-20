package edu.cmu.mgmt.service;

import java.util.List;

import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.Employee;

public interface EmployeeService {

	public Employee loginEmployee(Employee employee);

	public boolean createEmployee(Employee employee);

	public Employee findEmployeeByUsername(Employee employee);

	public boolean updatePwd(Employee employee, String newpassword,
			String repassword);

	public List<Customer> getSearchResult(String inputValue);

}
