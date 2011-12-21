package cz.vasekpurchart.aos.bank.backend;

import cz.cvut.felk.support.aos.sw.exchangerates.ExchangeRates;
import cz.cvut.felk.support.aos.sw.exchangerates.ExchangeRatesImplService;
import cz.cvut.felk.support.aos.sw.exchangerates.UnsupportedCurrencyFault_Exception;
import java.math.BigDecimal;

/**
 *
 * @author vasek
 */
public class ExchangeRatesAdapter {

	private ExchangeRates exchangeRates;

	public ExchangeRatesAdapter() {
		ExchangeRatesImplService service = new ExchangeRatesImplService();
		exchangeRates = service.getExchangeRatesImplPort();
	}

	public BigDecimal convertMoney(BigDecimal amount, String fromCurrency, String toCurrency) throws UnsupportedCurrencyException {
		if (fromCurrency.compareTo(toCurrency) == 0) {
			return amount;
		}

		try {
			BigDecimal rate = exchangeRates.getExchangeRate(fromCurrency, toCurrency);
			return amount.multiply(rate);
		} catch (UnsupportedCurrencyFault_Exception ex) {
			throw new UnsupportedCurrencyException();
		}
	}

	public boolean isSupported(String currency) {
		return exchangeRates.getSupportedCurrencies().contains(currency);
	}

}
