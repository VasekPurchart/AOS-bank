package cz.vasekpurchart.aos.bank.client;

/**
 *
 * @author vasek
 */
public class NotEnoughMoneyException extends Exception {

	public NotEnoughMoneyException() {
	}

	public NotEnoughMoneyException(String msg) {
		super(msg);
	}
}
