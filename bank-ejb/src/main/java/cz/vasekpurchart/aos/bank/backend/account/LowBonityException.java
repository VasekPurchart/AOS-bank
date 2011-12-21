package cz.vasekpurchart.aos.bank.backend.account;

/**
 *
 * @author vasek
 */
public class LowBonityException extends Exception {

	public LowBonityException() {
	}

	public LowBonityException(String msg) {
		super(msg);
	}
}
