package cz.vasekpurchart.aos.bank.client;

/**
 *
 * @author vasek
 */
public class InvalidAccountException extends Exception {

	public InvalidAccountException() {
	}

	public InvalidAccountException(String msg) {
		super(msg);
	}
}
