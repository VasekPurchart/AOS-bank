package cz.vasekpurchart.aos.bank.backend;

/**
 *
 * @author vasek
 */
public class UnsupportedCurrencyException extends Exception {

	public UnsupportedCurrencyException() {
	}

	public UnsupportedCurrencyException(String msg) {
		super(msg);
	}
}
