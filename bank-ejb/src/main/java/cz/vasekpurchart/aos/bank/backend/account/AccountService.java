package cz.vasekpurchart.aos.bank.backend.account;

import cz.vasekpurchart.aos.bank.backend.ExchangeRatesAdapter;
import cz.vasekpurchart.aos.bank.backend.UnsupportedCurrencyException;
import cz.vasekpurchart.aos.bank.scoringadapter.ScoringAdapter;
import java.math.BigDecimal;
import javax.inject.Inject;

/**
 *
 * @author vasek
 */
public class AccountService {

	public static final float LOAN_TRESHOLD = 0.6f;

	private AccountRepository accountRepository;

	private ExchangeRatesAdapter exchangeRates;

	private ScoringAdapter scoring;

	@Inject
	public AccountService(AccountRepository accountRepository, ExchangeRatesAdapter exchangeRates, ScoringAdapter scoringAdapter) {
		this.accountRepository = accountRepository;
		this.exchangeRates = exchangeRates;
		this.scoring = scoringAdapter;
	}

	public int createAccount(String name, String currency) throws UnsupportedCurrencyException {
		if (!exchangeRates.isSupported(currency)) {
			throw new UnsupportedCurrencyException();
		}
		Account acc = new Account(name, currency);
		accountRepository.create(acc);
		return acc.getIntId();
	}

	public BigDecimal deleteAccount(int accountNumber) throws InvalidAccountException {
		Account acc = accountRepository.find((long) accountNumber);
		if (acc == null) throw new InvalidAccountException();
		accountRepository.remove(acc);

		return acc.getAmount();
	}

	public BigDecimal depositMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		Account acc = accountRepository.find((long) accountNumber);
		if (acc == null) throw new InvalidAccountException();
		acc.setAmount(acc.getAmount().add(exchangeRates.convertMoney(amount, currency, acc.getCurrency())));

		return acc.getAmount();
	}

	public BigDecimal withdrawMoney(int accountNumber, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		Account acc = accountRepository.find((long) accountNumber);
		if (acc == null) throw new InvalidAccountException();
		if (acc.getAmount().compareTo(amount) == -1) throw new NotEnoughMoneyException();
		acc.setAmount(acc.getAmount().subtract(exchangeRates.convertMoney(amount, currency, acc.getCurrency())));

		return acc.getAmount();
	}

	public BigDecimal getCurrentBalance(int accountNumber, String currency) throws InvalidAccountException, UnsupportedCurrencyException {
		Account acc = accountRepository.find((long) accountNumber);
		if (acc == null) throw new InvalidAccountException();

		return exchangeRates.convertMoney(acc.getAmount(), currency, acc.getCurrency());
	}

	public void getLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException, LowBonityException {
		Account acc = accountRepository.find((long) accountNumber);
		if (acc == null) throw new InvalidAccountException();
		if (scoring.getScoring(acc.getIntId()) > LOAN_TRESHOLD) {
			acc.setAmount(acc.getAmount().add(amount));
			acc.setDebt(acc.getDebt().add(amount));
		} else {
			throw new LowBonityException();
		}
	}

	public BigDecimal payLoan(int accountNumber, BigDecimal amount) throws InvalidAccountException {
		Account acc = accountRepository.find((long) accountNumber);
		if (acc == null) throw new InvalidAccountException();
		acc.setDebt(acc.getDebt().subtract(amount));

		return acc.getDebt();
	}

}
