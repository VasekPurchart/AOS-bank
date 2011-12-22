package cz.vasekpurchart.aos.bank.client;

import cz.vasekpurchart.aos.bank.backendadapter.BackendWS;
import cz.vasekpurchart.aos.bank.backendadapter.BackendFacadeService;
import cz.vasekpurchart.aos.bank.backendadapter.InvalidAccountException_Exception;
import cz.vasekpurchart.aos.bank.backendadapter.LowBonityException_Exception;
import cz.vasekpurchart.aos.bank.backendadapter.NotEnoughMoneyException_Exception;
import cz.vasekpurchart.aos.bank.backendadapter.UnsupportedCurrencyException_Exception;
import java.math.BigDecimal;

/**
 *
 * @author vasek
 */
public class BackendAdapter {

	private BackendWS backend;

	public BackendAdapter() {
		BackendFacadeService service = new BackendFacadeService();
		backend = service.getBackendFacadePort();
	}

	public int createAccount(String name, String currency) throws UnsupportedCurrencyException {
		try {
			return backend.createAccount(name, currency);
		} catch (UnsupportedCurrencyException_Exception ex) {
			throw new UnsupportedCurrencyException();
		}
	}

	public BigDecimal deleteAccount(int accountNumber) throws InvalidAccountException {
		try {
			return backend.deleteAccount(accountNumber);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		}
	}

	public BigDecimal depositMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		try {
			return backend.depositMoney(accountNumber, amount, currency);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		} catch (UnsupportedCurrencyException_Exception ex) {
			throw new UnsupportedCurrencyException();
		}
	}

	public BigDecimal withdrawMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		try {
			return backend.withdrawMoney(accountNumber, amount, currency);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		} catch (NotEnoughMoneyException_Exception ex) {
			throw new NotEnoughMoneyException();
		} catch (UnsupportedCurrencyException_Exception ex) {
			throw new UnsupportedCurrencyException();
		}
	}

	public void transferMoney(int myAccount, int targetAccount, String targetBank, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		try {
			backend.transferMoney(myAccount, targetAccount, targetBank, amount, currency);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		} catch (NotEnoughMoneyException_Exception ex) {
			throw new NotEnoughMoneyException();
		} catch (UnsupportedCurrencyException_Exception ex) {
			throw new UnsupportedCurrencyException();
		}
	}

	public BigDecimal getCurrentBalance(int accountNumber, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		try {
			return backend.getCurrentBalance(accountNumber, currency);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		} catch (UnsupportedCurrencyException_Exception ex) {
			throw new UnsupportedCurrencyException();
		}
	}

	public void getLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException, LowBonityException {
		try {
			backend.getLoan(accountNumber, amount);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		} catch (LowBonityException_Exception ex) {
			throw new LowBonityException();
		}
	}

	public BigDecimal payLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException {
		try {
			return backend.payLoan(accountNumber, amount);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		}
	}

}
