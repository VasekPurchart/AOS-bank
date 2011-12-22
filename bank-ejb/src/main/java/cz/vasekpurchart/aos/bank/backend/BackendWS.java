package cz.vasekpurchart.aos.bank.backend;

import cz.vasekpurchart.aos.bank.backend.account.InvalidAccountException;
import cz.vasekpurchart.aos.bank.backend.account.LowBonityException;
import cz.vasekpurchart.aos.bank.backend.account.NotEnoughMoneyException;
import cz.vasekpurchart.aos.bank.backend.transfer.Transfer;
import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@WebService
public interface BackendWS {

	public int createAccount(String name, String currency) throws UnsupportedCurrencyException;

	public BigDecimal deleteAccount(int accountNumber) throws InvalidAccountException;

	public BigDecimal depositMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException;

	public BigDecimal withdrawMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException;

	public void transferMoney(int myAccount, int targetAccount, String targetBank, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException;

	public BigDecimal getCurrentBalance(int accountNumber, String currency) throws InvalidAccountException, UnsupportedCurrencyException;

	public void getLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException, LowBonityException;

	public BigDecimal payLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException;

	public List<Transfer> getTransfers(int accountNumber) throws InvalidAccountException;

}
