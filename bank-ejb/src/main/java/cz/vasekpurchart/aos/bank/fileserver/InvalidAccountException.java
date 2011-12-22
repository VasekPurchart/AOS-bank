package cz.vasekpurchart.aos.bank.fileserver;

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
