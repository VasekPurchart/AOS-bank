/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vasekpurchart.aos.bank.exchangeratesadapter;

import cz.vasekpurchart.aos.bank.backend.ExchangeRatesAdapter;
import cz.vasekpurchart.aos.bank.backend.UnsupportedCurrencyException;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vasek
 */
public class ExchangeRatesAdapterTest {

	private ExchangeRatesAdapter exchangeRatesAdapter;

	public ExchangeRatesAdapterTest() {
	}

	@Before
	public void setUp() {
		exchangeRatesAdapter = new ExchangeRatesAdapter();
	}

	@Test
	public void testConvertMoney() throws UnsupportedCurrencyException {
		BigDecimal amount = new BigDecimal(20);
		BigDecimal result = exchangeRatesAdapter.convertMoney(amount, "CZK", "USD");
		assertNotNull(result);
		assertNotSame(amount, result);
	}

	@Test(expected=UnsupportedCurrencyException.class)
	public void testConvertMoneyUnsupportedCurrency() throws UnsupportedCurrencyException {
		exchangeRatesAdapter.convertMoney(new BigDecimal(20), "CZK", "xxx");
	}

	@Test
	public void testConvertMoneySameCurrency() throws UnsupportedCurrencyException {
		BigDecimal amount = new BigDecimal(20);
		assertSame(amount, exchangeRatesAdapter.convertMoney(amount, "CZK", "CZK"));
	}

	@Test
	public void isSupportedCurrency() {
		assertTrue(exchangeRatesAdapter.isSupported("CZK"));
	}

	@Test
	public void isSupportedCurrencyFail() {
		assertFalse(exchangeRatesAdapter.isSupported("xxx"));
	}

	@After
	public void tearDown() {
	}

}
