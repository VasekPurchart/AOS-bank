package cz.vasekpurchart.aos.bank.backend.account;

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
