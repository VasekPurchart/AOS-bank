package cz.vasekpurchart.aos.bank.fileserver;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@Stateless
@WebService(endpointInterface = "cz.vasekpurchart.aos.bank.fileserver.FileServerWS")
public class FileServerFacade implements FileServerWS {

	@Inject
	private FileServerService fileServerService;

	@Override
	public DataHandler getAccountStatement(int accountNumber) throws InvalidAccountException {
		return fileServerService.getAccountStatement(accountNumber);
	}

}
