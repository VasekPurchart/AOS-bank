package cz.vasekpurchart.aos.bank.client;

import java.math.BigDecimal;
import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@Stateless
@WebService(endpointInterface = "cz.vasekpurchart.aos.bank.client.ClientWS")
public class ClientFacade implements ClientWS {

	@Inject
	private BackendAdapter backend;

	@Inject
	private FileServerAdapter fileServer;

	@Override
	public int setUpAccount(String name, String currency) throws UnsupportedCurrencyException {
		return backend.createAccount(name, currency);
	}

	@Override
	public BigDecimal deleteAccount(int accountNumber) throws InvalidAccountException {
		return backend.deleteAccount(accountNumber);
	}

	@Override
	public BigDecimal depositMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		return backend.depositMoney(accountNumber, amount, currency);
	}

	@Override
	public BigDecimal withdrawMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		return backend.withdrawMoney(accountNumber, amount, currency);
	}

	@Override
	public void transferMoney(int myAccount, int targetAccount, String targetBank, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		backend.transferMoney(myAccount, targetAccount, targetBank, amount, currency);
	}

	@Override
	public BigDecimal getCurrentBalance(int accountNumber, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		return backend.getCurrentBalance(accountNumber, currency);
	}

	@Override
	public void getLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException, LowBonityException {
		backend.getLoan(accountNumber, amount);
	}

	@Override
	public BigDecimal payLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException {
		return backend.payLoan(accountNumber, amount);
	}

	@Override
	public DataHandler getAccountStatement(int accountNumber) throws InvalidAccountException {
		return fileServer.getAccountStatement(accountNumber);
	}

}
