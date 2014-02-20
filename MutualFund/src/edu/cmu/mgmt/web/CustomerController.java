package edu.cmu.mgmt.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.vo.UserFundVo;
import edu.cmu.mgmt.service.CustomerService;
import edu.cmu.mgmt.service.FundService;
import edu.cmu.mgmt.service.TransactionService;
import edu.cmu.mgmt.utils.DoubleLongParseUtil;
import edu.cmu.mgmt.utils.ValidateUtil;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FundService fundService;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping("/customer.do")
	public String createLoginJSP(HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		return "user";
	}

	@RequestMapping("/login.do")
	public String login(Customer customer, HttpServletRequest request,
			HttpSession session) {
		Customer u = customerService.loginCustomer(customer);
		if (u == null) {
			request.setAttribute("errorInfo",
					"Username or password is incorrect");
			request.setAttribute("c", customer);
			return "login";
		}
		session.setAttribute("user", u);
		return "user";
	}

	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "login";
	}

	@RequestMapping("/search.do")
	public String search(String value, HttpServletRequest request,
			HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		request.setAttribute("funds", fundService.getSearchResult(value));
		return "research_fund";
	}

	@RequestMapping("/sell_fund_List_link.do")
	public String createUserFundVoListJSP(HttpServletRequest request,
			HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		List<UserFundVo> list = customerService.findUserFundVos(c
				.getCustomer_id());
		request.setAttribute("UserFundVos", list);
		request.setAttribute("price_date", list.size() == 0 ? null : list
				.get(0).getPrice_date());
		return "sell_fund_list";
	}

	@RequestMapping("/sell_fund_Detail_link.do")
	public String createUserFundVoDetialJSP(Integer fund_id,
			HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		
		List<String> errors = new LinkedList<String>();
        request.setAttribute("errors", errors);
		if (fund_id != null) {
		    if (customerService.getPosition(c.getCustomer_id(), fund_id) == null) {
                errors.add("You does not own this Fund!");
            } else {
                request.setAttribute("userFundVo",
                        customerService.findUserFundVo(c.getCustomer_id(), fund_id));
            }
		} else {
		    errors.add("invalid request!");
		}
		
		return "sell_fund";
	}

	@RequestMapping("/sell_fund_Detail.do")
	public String sellFund(Integer fund_id, String shareNum,
			HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		session.setAttribute("user",
				customerService.getCustomerById(c.getCustomer_id()));
		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);

		String result;
		if (shareNum != null) {
			shareNum = shareNum.trim();
		}
		boolean flag = true;
        request.setAttribute("errors", errors);
        if (fund_id != null) {
            if (customerService.getPosition(c.getCustomer_id(), fund_id) == null) {
                errors.add("You does not own this Fund!");
                flag = false;
            }
        } else {
            errors.add("invalid request!");
            flag = false;
        }
	        
		if (!ValidateUtil.isValid(shareNum)) {
			errors.add("shareNum is required");
			return "sell_fund";
		} else if ((result = ValidateUtil.isNumberValid(shareNum, 3)) != null) {
			errors.add(result);
			return "sell_fund";
		}

		if (errors.size() == 0) {
	        if (transactionService.saveSellFund(c.getCustomer_id(), fund_id,
                    DoubleLongParseUtil.parseShares(shareNum))) {
                request.setAttribute("success",
                        "Your selling request has been placed");
            } else {
                errors.add("Number of shares to sell shouldn't exceed the available number");
            }			
		}
		if (flag) {
            request.setAttribute("userFundVo",
                    customerService.findUserFundVo(c.getCustomer_id(), fund_id));           
		}
		session.setAttribute("user",
                customerService.getCustomerById(c.getCustomer_id()));
		return "sell_fund";
	}

	@RequestMapping("/account.do")
	public String viewAccount(HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		session.setAttribute("user",
				customerService.getCustomerById(c.getCustomer_id()));
		List<UserFundVo> list = customerService.findUserFundVos(c
				.getCustomer_id());
		Date lastTradingDay = fundService.getLastTradingDay();
		if (lastTradingDay != null) {
			request.setAttribute("ltd", lastTradingDay);
		}
		request.setAttribute("userFundVoList", list);
		return "account";
	}

	@RequestMapping("/research_fund.do")
	public String viewFundList(HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		request.setAttribute("funds", fundService.listFund());
		return "research_fund";
	}

	@RequestMapping("/buy_fund_link.do")
	public String createBuyFundJSP(Integer fund_id, HttpSession session,
			HttpServletRequest request) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		session.setAttribute("user",
				customerService.getCustomerById(c.getCustomer_id()));
		
		List<String> errors = new LinkedList<String>();
        request.setAttribute("errors", errors);
        if (fund_id != null) {
            if (fundService.getFundById(fund_id) == null) {
                errors.add("Fund does not exist!");
            } else {
                request.setAttribute("fund", fundService.getFundById(fund_id));
            }
        } else {
            errors.add("invalid request!");
        }
        
		return "buy_fund";
	}

	@RequestMapping("/buy_fund.do")
	public String buyFund(Integer fund_id, String amount, HttpSession session,
			HttpServletRequest request) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		session.setAttribute("user",
				customerService.getCustomerById(c.getCustomer_id()));

		List<String> errors = new LinkedList<String>();
		request.setAttribute("errors", errors);
        if (fund_id != null) {
            if (fundService.getFundById(fund_id) == null) {
                errors.add("Fund does not exist!");
            } else {
                request.setAttribute("fund", fundService.getFundById(fund_id));
            }
        } else {
            errors.add("invalid request!");
        }
		
		String result;
		if (amount != null)
			amount = amount.trim();
		if (!ValidateUtil.isValid(amount)) {
			errors.add("Amount to buy is required");
		} else if ((result = ValidateUtil.isNumberValid(amount, 2)) != null) {
			errors.add(result);
		}

		if (errors.size() == 0) {
            if (transactionService.saveBuyFund(c.getCustomer_id(), fund_id,
            		DoubleLongParseUtil.parseAmount(amount))) {
            	request.setAttribute("success",
            			"Your buying request has been placed");               	                	
            } else {
            	errors.add("Amount to buy shouldn't exceed the available cash balance");
            }
			session.setAttribute("user",
        			customerService.getCustomerById(c.getCustomer_id()));
			request.setAttribute("fund", fundService.getFundById(fund_id));
		}
		return "buy_fund";
	}

	@RequestMapping("/request_check_link.do")
	public String requestCheck(HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		session.setAttribute("user",
				customerService.getCustomerById(c.getCustomer_id()));
		return "request_check";
	}

	@RequestMapping("/request_check.do")
	public String updateCheck(String amount, HttpServletRequest request,
			HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		String result;
		if (c == null) {
			return "login";
		} else {
			session.setAttribute("user",
					customerService.getCustomerById(c.getCustomer_id()));
			// validate whether amount is null
			ArrayList<String> errors = new ArrayList<String>();
			request.setAttribute("errors", errors);
			if (amount != null)
				amount = amount.trim();
			if (!ValidateUtil.isValid(amount)) {
				errors.add("Check amount is required");
				session.setAttribute("user",
						customerService.getCustomerById(c.getCustomer_id()));
			} else if ((result = ValidateUtil.isNumberValid(amount, 2)) != null) {
				errors.add(result);
				session.setAttribute("user",
						customerService.getCustomerById(c.getCustomer_id()));
			}

			if (errors.size() == 0) {
                if (transactionService.saveCheck(c.getCustomer_id(),
                		DoubleLongParseUtil.parseAmount(amount))) {                    	
                	request.setAttribute("success",
                			"Your check request has been placed");
                } else {
                	errors.add("Amount to withdraw shouldn't exceed the available cash balance");
                }
				session.setAttribute("user",
            			customerService.getCustomerById(c.getCustomer_id()));
			}
			return "request_check";
		}
	}

	@RequestMapping("/change_pwd_link.do")
	public String linkChangePassword(HttpServletRequest request,
			HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		} else {
			return "updatepwd";
		}
	}

	@RequestMapping("/change_pwd.do")
	public String updatePwd(String currentpassword, String newpassword,
			String repassword, HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null)
			return "login";

		session.setAttribute("user",
				customerService.getCustomerById(c.getCustomer_id()));
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
			return "updatepwd";
		if (!ValidateUtil.isValidLength(newpassword, 6, 16)) {
			errors.add("Password length should between 6 and 16 characters");
		}
		if (!newpassword.equals(repassword)) {
			errors.add("New password doesn't match with the re-enter password");
		}

		if (errors.size() == 0) {
			c.setPassword(currentpassword);
			if (customerService.updatePwd(c, newpassword, repassword)) {
				request.setAttribute("success",
						"Your password has been changed");
			} else {
				errors.add("Current password is incorrect");
			}
		}
		return "updatepwd";
	}

	@RequestMapping("fund_detail_link.do")
	public String createFundDetailJSP(Integer fund_id,
			HttpServletRequest request, HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		}
		
        List<String> errors = new LinkedList<String>();
        request.setAttribute("errors", errors);
        if (fund_id != null) {
            if (fundService.getFundById(fund_id) == null) {
                errors.add("Fund does not exist!");
            } else {
                request.setAttribute("fund", fundService.getFundById(fund_id));
                request.setAttribute("priceHistoryList",
                        transactionService.getFundHistory(fund_id));
            }
        } else {
            errors.add("invalid request!");
        }
        
		return "fund_chart";
	}

	@RequestMapping("transaction_history.do")
	public String viewTransaction(HttpServletRequest request,
			HttpSession session) {
		Customer c = (Customer) session.getAttribute("user");
		if (c == null) {
			return "login";
		} else {
			request.setAttribute("historyList", customerService
					.findTransactionByCustomerId(c.getCustomer_id()));
			return "transaction_history";
		}
	}
}
