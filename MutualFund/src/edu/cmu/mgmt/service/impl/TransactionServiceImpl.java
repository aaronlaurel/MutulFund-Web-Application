package edu.cmu.mgmt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cmu.mgmt.common.TransactionType;
import edu.cmu.mgmt.dao.BaseDao;
import edu.cmu.mgmt.model.Customer;
import edu.cmu.mgmt.model.Fund;
import edu.cmu.mgmt.model.Fund_Price_History;
import edu.cmu.mgmt.model.Position;
import edu.cmu.mgmt.model.Transaction;
import edu.cmu.mgmt.service.CustomerService;
import edu.cmu.mgmt.service.FundService;
import edu.cmu.mgmt.service.TransactionService;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private BaseDao<Customer> customerDao;

    @Autowired
    private BaseDao<Transaction> transactionDao;

    @Autowired
    private BaseDao<Fund_Price_History> fundPriceHistoryDao;

    @Autowired
    private BaseDao<Position> positionDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FundService fundService;

    public boolean saveBuyFund(int custumerID, int fundID, long amount) {
        Customer c = customerService.getCustomerById(custumerID);
        if (c.getAvailableCash() < amount) {
            return false;
        }

        Transaction transaction = new Transaction();
        transaction.setCustomer_id(custumerID);
        transaction.setFund_id(fundID);
        transaction.setTransaction_type(TransactionType.BUY);
        transaction.setAmount(amount);
        transaction.setPending(true);

        c.setAvailableCash(c.getAvailableCash() - amount);
        customerDao.update(c);

        transactionDao.save(transaction);
        return true;
    }

    public boolean saveCheck(int customer_id, long amount) {
        Customer c = customerService.getCustomerById(customer_id);
        if (c.getAvailableCash() < amount) {
            return false;
        } else {
            c.setAvailableCash(c.getAvailableCash() - amount);
            customerDao.update(c);
            
            Transaction t = new Transaction();
            t.setAmount(amount);
            t.setCustomer_id(c.getCustomer_id());
            t.setTransaction_type(TransactionType.CHECK);
            t.setExecute_date(null);
            t.setPending(true);
            transactionDao.saveOrUpdate(t);
            return true;
        }

    }

    public boolean saveSellFund(int customer_id, int fund_id, long shares) {
        Position position = customerService.getPosition(customer_id, fund_id);
        if (position.getAvailableShares() < shares) {
            return false;
        } else {
            Transaction transaction = new Transaction();
            transaction.setCustomer_id(customer_id);
            transaction.setFund_id(fund_id);
            transaction.setTransaction_type(TransactionType.SELL);
            transaction.setShares(shares);
            transaction.setPending(true);
            transactionDao.save(transaction);

            position.setAvailableShares(position.getAvailableShares() - shares);
            positionDao.update(position);
            return true;
        }
    }

    public void saveDeposit(int custumerID, long amount) {
        Customer c = customerService.getCustomerById(custumerID);
        c.setAvailableCash(amount + c.getAvailableCash());
        customerDao.update(c);
        
        Transaction t = new Transaction();
        t.setCustomer_id(c.getCustomer_id());
        t.setTransaction_type(TransactionType.DEPOSIT);
        t.setAmount(amount);
        t.setExecute_date(null);
        t.setPending(true);
        transactionDao.save(t);
    }

    public List<Fund_Price_History> getFundHistory(int fund_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fundId", fund_id);
        List<Fund_Price_History> list = fundPriceHistoryDao
                .find("from Fund_Price_History fh where fh.fund_id=:fundId order by fh.price_date",
                        params);
        return list;
    }

    public String executePendingTransaction(List<String> fund_ids,
            List<String> prices, Date date) {
        String result = fundService.updateFundsPrice(fund_ids, prices, date);
        if (result.length() != 0) {
            return result;
        }
        List<Transaction> pendingTransactions = this.getPendingTransactions();
        for (Transaction transaction : pendingTransactions) {
            Customer customer = customerService.getCustomerById(transaction
                    .getCustomer_id());
            Fund fund = null;
            Position position = null;

            switch (transaction.getTransaction_type()) {
            case BUY:
                fund = fundService.getFundById(transaction.getFund_id());
                position = customerService.getPosition(
                        transaction.getCustomer_id(), transaction.getFund_id());
                executeBuyTransaction(transaction, date, customer, fund,
                        position);
                break;
            case SELL:
                fund = fundService.getFundById(transaction.getFund_id());
                position = customerService.getPosition(
                        transaction.getCustomer_id(), transaction.getFund_id());
                executeSellTransaction(transaction, date, customer, fund,
                        position);
                break;
            case CHECK:
                executeCheckTransaction(transaction, date, customer);             
                break;
            case DEPOSIT:
                executeDepositTransaction(transaction, date, customer);                
                break;
            default:
                break;
            }
        }
        return "";
    }

    private List<Transaction> getPendingTransactions() {
        return transactionDao
                .find("from Transaction t where t.execute_date =null");
    }

    private void executeBuyTransaction(Transaction transaction, Date date,
            Customer customer, Fund fund, Position position) {
        // update transaction table
        double amount = transaction.getAmount() / 100.;
        double price = fund.getPrice() / 100.;

        long shares = (long) (amount / price * 1000);
        // calculate the real amount that cost to buy
        long amountL = Math.round(shares / 1000. * fund.getPrice());

        transaction.setExecute_date(date);
        transaction.setPending(false);
        transaction.setShares(shares);
        // set the amount as the real amount
        transaction.setAmount(amountL);
        transactionDao.update(transaction);

        // update customer cash balance;
        customer.setCash(customer.getCash() - transaction.getAmount());
        customer.setAvailableCash(customer.getCash());
        customerDao.update(customer);

        // update position table;
        if (position != null) {
            position.setShares(position.getShares() + shares);
            position.setAvailableShares(position.getShares());
            positionDao.update(position);
        } else {
            Position p = new Position();
            p.setCustomer_id(transaction.getCustomer_id());
            p.setFund_id(transaction.getFund_id());
            p.setAvailableShares(shares);
            p.setShares(shares);
            positionDao.save(p);
        }
    }

    private void executeSellTransaction(Transaction transaction, Date date,
            Customer customer, Fund fund, Position position) {
        // update transaction table
        double shares = transaction.getShares() / 1000.;
        double price = fund.getPrice() / 100.;
        long amount = Math.round(shares * price * 100);
        transaction.setExecute_date(date);
        transaction.setPending(false);
        transaction.setAmount(amount);
        transactionDao.update(transaction);

        // update customer cash balance;
        customer.setCash(customer.getCash() + amount);
        customer.setAvailableCash(customer.getCash());
        customerDao.update(customer);

        // update position table;
        // no need to update available shares;
        // delete position if share == 0;
        long remainingShares = position.getShares() - transaction.getShares();
        if (remainingShares == 0) {
            positionDao.delete(position);
        } else {
            position.setShares(remainingShares);
            position.setAvailableShares(remainingShares);
            positionDao.update(position);
        }
    }

    private void executeCheckTransaction(Transaction transaction, Date date,
            Customer customer) {
        // update transaction table
        transaction.setExecute_date(date);
        transaction.setPending(false);
        transactionDao.update(transaction);

        // update customer cash balance;
        customer.setCash(customer.getCash() - transaction.getAmount());
        customer.setAvailableCash(customer.getCash());
        customerDao.update(customer);
    }

    private void executeDepositTransaction(Transaction transaction, Date date,
            Customer customer) {
        // update transaction table
        transaction.setExecute_date(date);
        transaction.setPending(false);
        transactionDao.update(transaction);

        // update customer cash balance;
        customer.setCash(customer.getCash() + transaction.getAmount());
        customer.setAvailableCash(customer.getCash());
        customerDao.update(customer);
    }

    @Override
    public long FindFundHistoryPrice(int fund_id, Date execute_date) {
        // TODO Auto-generated method stub
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fundId", fund_id);
        params.put("edate", execute_date);
        Fund_Price_History fpr = fundPriceHistoryDao
                .get("from Fund_Price_History f where f.fund_id =:fundId and f.price_date =:edate",
                        params);
        if (fpr != null) {
            return fpr.getPrice();
        } else {
            return -1;
        }
    }

}
