package edu.cmu.mgmt.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.Employee;
import edu.cmu.mgmt.model.Fund;
import edu.cmu.mgmt.model.vo.TransactionHistoryVo;
import edu.cmu.mgmt.model.vo.UserFundVo;
import edu.cmu.mgmt.service.CustomerService;
import edu.cmu.mgmt.service.EmployeeService;
import edu.cmu.mgmt.service.FundService;
import edu.cmu.mgmt.service.TransactionService;
import edu.cmu.mgmt.utils.DateEditor;
import edu.cmu.mgmt.utils.DoubleLongParseUtil;
import edu.cmu.mgmt.utils.PasswordUtils;
import edu.cmu.mgmt.utils.ValidateUtil;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FundService fundService;

	@Autowired
	private TransactionService transactionService;

	/*
	 * use DateEditor handle the field that need to be transfered to the Date
	 * type
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	@RequestMapping("/admin.do")
	public String createLoginJSP(HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
			return "admin";
		}
	}

	@RequestMapping("/login.do")
	public String login(Employee employee, HttpServletRequest request,
			HttpSession session) {
		Employee u = employeeService.loginEmployee(employee);
		if (u == null) {
			request.setAttribute("errorInfo",
					"Username or password is incorrect");
			request.setAttribute("e", employee);
			return "admin_login";
		} else {
			session.setAttribute("admin", u);
			return "admin";
		}
	}

	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "admin_login";
	}

	@RequestMapping("/search.do")
	public String search(String username, String type,
			HttpServletRequest request, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("admin");
		if (employee == null) {
			return "admin_login";
		}
		List<Customer> list = employeeService.getSearchResult(username);
		request.setAttribute("customerList", list);
		return type;
	}

	@RequestMapping("/create_fund_link.do")
	public String createFundJSP(HttpSession session) {
		Employee employee = (Employee) session.getAttribute("admin");
		if (employee == null) {
			return "admin_login";
		}
		return "admin_create_fund";
	}

	@RequestMapping("/create_fund.do")
	public String createFund(Fund fund, HttpServletRequest request,
			HttpSession session) {
		Employee employee = (Employee) session.getAttribute("admin");
		if (employee == null) {
			return "admin_login";
		}

		// request.setAttribute("fd", fund);
		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);
		if (!ValidateUtil.isValid(fund.getName())) {
			errors.add("Fund Name is Required");
		}
		if (!ValidateUtil.isValid(fund.getSymbol())) {
			errors.add("Fund Ticker is Required");
		}
		if (!ValidateUtil.isValidInput(fund.getName())) {
			errors.add("Fund name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(fund.getName(), 0, 20)) {
			errors.add("Fund name shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidInput(fund.getSymbol())) {
			errors.add("Fund Ticker shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(fund.getSymbol(), 0, 5)) {
			errors.add("Fund Ticker shouldn't exceed 5 characters");
		}
		if (errors.size() == 0) {
			String result = fundService.createFund(fund);
			if (result.length() == 0) {
				request.setAttribute("success", "Fund " + fund.getName()
						+ " has been created");
			} else {
				errors.add(result);
			}
		}

		return "admin_create_fund";
	}

	@RequestMapping("/create_employee_account_link.do")
	public String createEmployeeAccountJSP(HttpSession session) {
		Employee employee = (Employee) session.getAttribute("admin");
		if (employee == null) {
			return "admin_login";
		}
		return "admin_create_employee_account";
	}

	@RequestMapping("/create_employee_account.do")
	public String createEmployeeAccount(Employee employee, String password2,
			HttpServletRequest request, HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		}
		// request.setAttribute("emp", employee);

		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);
		if (!ValidateUtil.isValid(employee.getUsername())) {
			errors.add("User Name is Required");
		}
		if (!ValidateUtil.isValid(employee.getPassword())) {
			errors.add("Password is Required");
		}
		if (!ValidateUtil.isValid(password2)) {
			errors.add("Reenter Password is Required");
		}
		if (!ValidateUtil.isValid(employee.getFirstname())) {
			errors.add("Fisrt Name is Required");
		}
		if (!ValidateUtil.isValid(employee.getLastname())) {
			errors.add("Last Name is Required");
		}
		if (errors.size() != 0)
			return "admin_create_employee_account";
		if (!ValidateUtil.isValidInput(employee.getUsername())) {
			errors.add("User name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(employee.getUsername(), 0, 20)) {
			errors.add("Employee username shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidLength(employee.getPassword(), 6, 16)) {
			errors.add("Password length should between 6 and 16 characters");
		}
		if (!employee.getPassword().equals(password2)) {
			errors.add("Password doesn't match with the re-enter password");
		}
		if (!ValidateUtil.isValidInput(employee.getFirstname())) {
			errors.add("First name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(employee.getFirstname(), 0, 20)) {
			errors.add("Employee firstname shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidInput(employee.getLastname())) {
			errors.add("Last name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(employee.getLastname(), 0, 20)) {
			errors.add("Employee lastname shouldn't exceed 20 characters");
		}
		if (errors.size() == 0) {
			if (employeeService.createEmployee(employee)) {
				request.setAttribute("success",
						"Employee \"" + employee.getUsername()
								+ "\" has been created");
			} else {
				errors.add("This username already exists");
			}
		}

		return "admin_create_employee_account";
	}

	@RequestMapping("/create_customer_account_link.do")
	public String createCustomerAccountJSP(HttpSession session) {
		Employee employee = (Employee) session.getAttribute("admin");
		if (employee == null) {
			return "admin_login";
		}
		return "admin_create_customer_account";
	}

	@RequestMapping("/create_customer_account.do")
	public String createCustomerAccount(Customer customer, String password2,
			HttpServletRequest request, HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		}
		// request.setAttribute("cmr", customer);

		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);
		if (!ValidateUtil.isValid(customer.getUsername())) {
			errors.add("User Name is Required");
		}
		if (!ValidateUtil.isValid(customer.getPassword())) {
			errors.add("Password is Required");
		}
		if (!ValidateUtil.isValid(password2)) {
			errors.add("Reenter Password is Required");
		}
		if (!ValidateUtil.isValid(customer.getFirstname())) {
			errors.add("Fisrt Name is Required");
		}
		if (!ValidateUtil.isValid(customer.getLastname())) {
			errors.add("Last Name is Required");
		}
		if (errors.size() != 0)
			return "admin_create_customer_account";
		if (!ValidateUtil.isValidInput(customer.getUsername())) {
			errors.add("User name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(customer.getUsername(), 0, 20)) {
			errors.add("Customer username shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidLength(customer.getPassword(), 6, 16)) {
			errors.add("Password length should between 6 and 16 characters");
		}
		if (!customer.getPassword().equals(password2)) {
			errors.add("Password doesn't match with the re-enter password");
		}
		if (!ValidateUtil.isValidInput(customer.getFirstname())) {
			errors.add("First name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(customer.getFirstname(), 0, 20)) {
			errors.add("Customer firstname shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidInput(customer.getLastname())) {
			errors.add("Last name shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(customer.getLastname(), 0, 20)) {
			errors.add("Customer lastname shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidInput(customer.getAddr_line1())) {
			errors.add("Address Line 1 shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(customer.getAddr_line1(), 0, 100)) {
			errors.add("Customer Address line 1 shouldn't exceed 100 characters");
		}
		if (!ValidateUtil.isValidInput(customer.getAddr_line2())) {
			errors.add("Address Line 2 shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(customer.getAddr_line2(), 0, 100)) {
			errors.add("Customer Address line 2 shouldn't exceed 100 characters");
		}
		if (!ValidateUtil.isValidInput(customer.getCity())) {
			errors.add("City shouldn't contain angle brackets or quotes");
		}
		if (!ValidateUtil.isValidLength(customer.getCity(), 0, 20)) {
			errors.add("City shouldn't exceed 20 characters");
		}
		if (!ValidateUtil.isValidInput(customer.getState())) {
			errors.add("State shouldn't contain angle brackets or quotes");
		}
		if (!customer.getState().equals("")
				&& (!ValidateUtil.isValidLength(customer.getState(), 2, 2) || !StringUtils
						.isAlpha(customer.getState()))) {
			errors.add("State should be 2 alphabets");
		}
		if (!ValidateUtil.isValidInput(customer.getZip())) {
			errors.add("Zip code shouldn't contain angle brackets or quotes");
		}
		if (!customer.getZip().equals("")
				&& (!ValidateUtil.isValidLength(customer.getZip(), 5, 5) || !NumberUtils
						.isDigits(customer.getZip()))) {
			errors.add("Zip code should be 5 digits number");
		}
		if (errors.size() == 0) {
			if (customerService.createCustomer(customer)) {
				request.setAttribute("success",
						"Customer \"" + customer.getUsername()
								+ "\" has been created");
			} else {
				errors.add("This username already exists");
			}
		}
		return "admin_create_customer_account";
	}

	@RequestMapping("/list_customer_account.do")
	public String listCustomerAccount(HttpServletRequest request,
			HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
			List<Customer> list = customerService.listCustomer();
			request.setAttribute("customerList", list);
			return "admin_list_account";
		}
	}

	@RequestMapping("/view_customer_account.do")
	public String viewCustomerAccount(Integer cid, HttpServletRequest request,
			HttpSession session) {
	    List<String> errors = new LinkedList<String>();
        request.setAttribute("errors", errors);
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
		    if (cid != null) {
		        Customer c = new Customer();
	            c.setCustomer_id(cid);
	            List<UserFundVo> list = customerService.findUserFundVos(cid);
	            c = customerService.getCustomerById(c.getCustomer_id());
	            if (c == null) {
	                errors.add("User does not exit!");
	            } else {
	                // List<Integer> priceList ;
	                Date lastTradingDay = fundService.getLastTradingDay();
	                request.setAttribute("ltd", lastTradingDay);
	                request.setAttribute("customer", c);
	                request.setAttribute("userFundVoList", list);
	            }
		    } else {
		        errors.add("invalid request!");
		    }
			
			return "admin_view_account";
		}
	}

	@RequestMapping("/change_pwd_link.do")
	public String linkChangePassword(HttpServletRequest request,
			HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
			return "admin_updatepwd";
		}
	}

	@RequestMapping("/change_pwd.do")
	public String updatePwd(String currentpassword, String newpassword,
			String repassword, HttpServletRequest request, HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null)
			return "admin_login";

		// store status information - success update or error message
		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);

		if (!ValidateUtil.isValid(currentpassword)) {
			errors.add("Current password is required");
		}
		if (!ValidateUtil.isValid(newpassword)) {
			errors.add("New password is required");
		}
		if (!ValidateUtil.isValid(repassword)) {
			errors.add("Reenter new password is required");
		}
		if (errors.size() != 0)
			return "admin_updatepwd";
		if (!ValidateUtil.isValidLength(newpassword, 6, 16)) {
			errors.add("Password length should between 6 and 16 characters");
		}
		if (!newpassword.equals(repassword)) {
			errors.add("New password doesn't match with the re-enter password");
		}

		if (errors.size() == 0) {
			e.setPassword(currentpassword);
			if (employeeService.updatePwd(e, newpassword, repassword)) {
				request.setAttribute("success",
						"Your password has been changed");
			} else {
				errors.add("The current password is incorrect");
			}
		}
		return "admin_updatepwd";
	}

	@RequestMapping("/make_deposit_list.do")
	public String listDepositAccount(HttpServletRequest request,
			HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
			List<Customer> list = customerService.listCustomer();
			request.setAttribute("customerList", list);
			return "admin_make_deposit_list";
		}
	}

	@RequestMapping("/make_deposit_link.do")
	public String linkDeposit(Integer cid, HttpSession session,
			HttpServletRequest request) {
	    ArrayList<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
            if (cid != null) {
                if (customerService.getCustomerById(cid) == null) {
                    errors.add("Customer does not exist!");
                    return "admin_make_deposit";
                }
                request.setAttribute("u", customerService.getCustomerById(cid));
            } else {
                errors.add("invalid request!");
            }
			return "admin_make_deposit";
		}
	}

	@RequestMapping("/make_deposit.do")
	public String makeDeposit(Integer cid, String amount, HttpSession session,
			HttpServletRequest request) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		}

		ArrayList<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		if (cid != null) {
		    request.setAttribute("u", customerService.getCustomerById(cid));
	        String result;
	        if (amount != null)
	            amount = amount.trim();
	        if (!ValidateUtil.isValid(amount)) {
	            errors.add("Deposit amount is required");
	            return "admin_make_deposit";
	        } else if ((result = ValidateUtil.isNumberValid(amount, 2)) != null) {
	            errors.add(result);
	            return "admin_make_deposit";
	        } else if (customerService.getCustomerById(cid) == null) {
	            errors.add("Customer does not exist!");
                return "admin_make_deposit";
	        }
            transactionService.saveDeposit(cid,
                    DoubleLongParseUtil.parseAmount(amount));
            request.setAttribute("success", "Your deposit request has been placed");
            request.setAttribute("u", customerService.getCustomerById(cid));
        } else {
            errors.add("invalid request!");
        }
		
		return "admin_make_deposit";
	}

	@RequestMapping("/list_customer_history.do")
	public String listCustomerHistory(HttpServletRequest request,
			HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
			List<Customer> list = customerService.listCustomer();
			request.setAttribute("customerList", list);
			return "admin_list_history";
		}
	}

	@RequestMapping("/view_customer_history.do")
	public String viewCustomerHistory(HttpServletRequest request,
			HttpSession session, Integer cid) {
        List<String> errors = new LinkedList<String>();
        request.setAttribute("errors", errors);
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
            if (cid != null) {
                Customer c = customerService.getCustomerById(cid);
                if (c == null) {
                    errors.add("Customer does not exist!");
                } else {
                    List<TransactionHistoryVo> historyList = customerService
                            .findTransactionByCustomerId(cid);
                    request.setAttribute("historyList", historyList);
                    request.setAttribute("user", c);
                }
            } else {
                errors.add("invalid request!");
            }
			return "admin_view_history";
		}
	}

	@RequestMapping("/transition_day_link.do")
	public String createTransitionDayJSP(HttpSession session,
			HttpServletRequest request) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		}
		List<Fund> funds = fundService.listFund();
		request.setAttribute("funds", funds);
		Date lastTradingDay = fundService.getLastTradingDay();
		if (lastTradingDay != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(lastTradingDay);
			c.add(Calendar.DATE, 1);
			Date startDay = c.getTime();
			request.setAttribute("startDay", startDay);
			request.setAttribute("ltd", lastTradingDay);
		}
		return "admin_transition_day";
	}

	@RequestMapping("/transition_day.do")
	public String submitTransitionDay(
			@RequestParam("fund_id") List<String> fund_ids,
			@RequestParam("fund_name") List<String> fund_names,
			@RequestParam("price") List<String> prices, String date,
			HttpSession session, HttpServletRequest request) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		}

		ArrayList<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		if (fund_ids.size() != prices.size()) {
			errors.add("Listed data is not complete");
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		Date d = null;

		Pattern p = Pattern.compile("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})$");
		Matcher m = p.matcher(date);
		if (m.matches()) {
			try {
				d = df.parse(date);
			} catch (ParseException ex) {
				errors.add("It should be a valid date!");
			}
		} else {
			errors.add("Date format should be \"yyyy-MM-dd\"");
		}

		if (errors.size() == 0) {
			for (int i = 0; i < fund_ids.size(); i++) {
				String fid = fund_ids.get(i);
				String price = prices.get(i);
				if (!ValidateUtil.isIDValid(fid)) {
					errors.add("Fund ID should be an integer");
					break;
				}
				String result;
				if (price != null)
					price = price.trim();
				if (!ValidateUtil.isValid(price)) {
					errors.add("The price of " + fund_names.get(i)
							+ " is Required");
					break;
				}
				if ((result = ValidateUtil.isNumberValid(price, 2)) != null) {
					errors.add(fund_names.get(i) + ": " + result);
					break;
				}
				Fund fund = fundService.getFundById(Integer.parseInt(fid));
				if (fund == null) {
					errors.add("The fund does not exist");
					break;
				}
			}
		}

		if (errors.size() == 0) {
			String result = transactionService.executePendingTransaction(
					fund_ids, prices, d);
			if (result.length() == 0) {
				request.setAttribute("success",
						"All pending transactions have been processed");
			} else {
				errors.add(result);
			}
		}

		List<Fund> funds = fundService.listFund();
		request.setAttribute("funds", funds);
		Date lastTradingDay = fundService.getLastTradingDay();
		if (lastTradingDay != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(lastTradingDay);
			c.add(Calendar.DATE, 1);
			Date startDay = c.getTime();
			request.setAttribute("startDay", startDay);
			request.setAttribute("ltd", lastTradingDay);
		}
		return "admin_transition_day";
	}

	@RequestMapping("/reset_cust_pwd_link.do")
	public String linkUpdateCustPwd(HttpServletRequest request,
			HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		} else {
			List<Customer> list = customerService.listCustomer();
			session.setAttribute("customerList", list);
			return "admin_reset_cust_pwd";
		}
	}

	@RequestMapping("/reset_cust_pwd.do")
	public String resetCustPwd(Integer cid, HttpServletRequest request,
			HttpSession session) {
		Employee e = (Employee) session.getAttribute("admin");
		if (e == null) {
			return "admin_login";
		}

		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);

		if (cid != null) {
			request.setAttribute("customerList", customerService.listCustomer());
			Customer c = customerService.getCustomerById(cid);
			String newPassword = ValidateUtil.getRandom();
			if (c == null) {
			    errors.add("Customer does not exist!");
			} else {
                c.setPassword(PasswordUtils.md5(newPassword));
                customerService.updateCustomer(c);
                request.setAttribute("success", "The password of customer \""
                        + c.getUsername() + "\" has been reset to \""
                        + newPassword + "\" successfully");
			}
		} else
			errors.add("Customer ID shouldn't be empty");
		return "admin_list_account";
	}
}
