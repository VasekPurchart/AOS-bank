package cz.vasekpurchart.aos.bank.fileserver;

import javax.activation.DataHandler;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@WebService
public interface FileServerWS {

	public DataHandler getAccountStatement(int accountNumber) throws InvalidAccountException;

}
