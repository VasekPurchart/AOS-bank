package cz.vasekpurchart.aos.bank.client;

import java.math.BigDecimal;
import javax.activation.DataHandler;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@WebService
public interface ClientWS {

	public int setUpAccount(String name, String currency) throws UnsupportedCurrencyException;

	public BigDecimal deleteAccount(int accountNumber) throws InvalidAccountException;

	public BigDecimal depositMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException;

	public BigDecimal withdrawMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException;

	public void transferMoney(int myAccount, int targetAccount, String targetBank, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException;

	public BigDecimal getCurrentBalance(int accountNumber, String currency) throws InvalidAccountException, UnsupportedCurrencyException;

	public void getLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException, LowBonityException;

	public BigDecimal payLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException;

	public DataHandler getAccountStatement(int accountNumber) throws InvalidAccountException;

}
