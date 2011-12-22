package cz.vasekpurchart.aos.bank.fileserver;

import cz.vasekpurchart.aos.bank.backendadapter.BackendFacadeService;
import cz.vasekpurchart.aos.bank.backendadapter.BackendWS;
import cz.vasekpurchart.aos.bank.backendadapter.InvalidAccountException_Exception;
import cz.vasekpurchart.aos.bank.backendadapter.Transfer;
import java.util.List;

/**
 *
 * @author vasek
 */
public class BackendAdapter {

	private BackendWS backend;

	public BackendAdapter() {
		BackendFacadeService service = new BackendFacadeService();
		backend = service.getBackendFacadePort();
	}

	public List<Transfer> getTransfers(int accountNumber) throws InvalidAccountException {
		try {
			return backend.getTransfers(accountNumber);
		} catch (InvalidAccountException_Exception ex) {
			throw new InvalidAccountException();
		}
	}

}
