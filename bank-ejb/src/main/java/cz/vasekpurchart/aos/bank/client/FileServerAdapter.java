package cz.vasekpurchart.aos.bank.client;

import cz.vasekpurchart.aos.bank.fileserveradapter.FileServerFacadeService;
import cz.vasekpurchart.aos.bank.fileserveradapter.FileServerWS;
import cz.vasekpurchart.aos.bank.fileserveradapter.InvalidAccountException_Exception;
import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;



/**
 *
 * @author vasek
 */
public class FileServerAdapter {

	private FileServerWS fileServer;

	public FileServerAdapter() {
		FileServerFacadeService service = new FileServerFacadeService();
		fileServer = service.getFileServerFacadePort();
	}

	public DataHandler getAccountStatement(int accountNumber) throws InvalidAccountException {
		try {
			return new DataHandler(new ByteArrayDataSource(fileServer.getAccountStatement(accountNumber), "application/pdf"));
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		}
	}

}
