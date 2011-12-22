package cz.vasekpurchart.aos.bank.backend;

import cz.vasekpurchart.aos.bank.backend.account.InvalidAccountException;
import cz.vasekpurchart.aos.bank.backend.account.LowBonityException;
import cz.vasekpurchart.aos.bank.backend.account.NotEnoughMoneyException;
import cz.vasekpurchart.aos.bank.backend.transfer.Transfer;
import cz.vasekpurchart.aos.bank.backend.transfer.TransferService;
import cz.vasekpurchart.aos.bank.backend.account.AccountService;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@Stateless
@WebService(endpointInterface = "cz.vasekpurchart.aos.bank.backend.BackendWS")
public class BackendFacade implements BackendWS {

	@Inject
	private AccountService accountService;

	@Inject
	private TransferService transferService;

	@Override
	public int createAccount(String name, String currency) throws UnsupportedCurrencyException {
		return accountService.createAccount(name, currency);
	}

	@Override
	public BigDecimal deleteAccount(int accountNumber) throws InvalidAccountException {
		return accountService.deleteAccount(accountNumber);
	}

	@Override
	public BigDecimal depositMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		return accountService.depositMoney(accountNumber, amount, currency);
	}

	@Override
	public BigDecimal withdrawMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		return accountService.withdrawMoney(accountNumber, amount, currency);
	}

	@Override
	public void transferMoney(int myAccount, int targetAccount, String targetBank, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		transferService.transferMoney(myAccount, targetAccount, targetBank, amount, currency);
	}

	@Override
	public BigDecimal getCurrentBalance(int accountNumber, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		return accountService.getCurrentBalance(accountNumber, currency);
	}

	@Override
	public void getLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException, LowBonityException {
		accountService.getLoan(accountNumber, amount);
	}

	@Override
	public BigDecimal payLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException {
		return accountService.payLoan(accountNumber, amount);
	}

	@Override
	public List<Transfer> getTransfers(int accountNumber) throws InvalidAccountException {
		return transferService.getTransfers(accountNumber);
	}

}
