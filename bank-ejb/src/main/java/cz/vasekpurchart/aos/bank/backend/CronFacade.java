package cz.vasekpurchart.aos.bank.backend;

import cz.vasekpurchart.aos.bank.backend.transfer.TransferService;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author vasek
 */
@Stateless
public class CronFacade {

	@Inject
	private TransferService transferService;

	@Schedule(minute="*/2", hour="*", persistent=false)
	public void processOutgoingTransfers() {
		System.out.println("CRON: processOutgoingTransfers");
		transferService.processOutgoingTransfers();
	}

	@Schedule(minute="*/3", hour="*", persistent=false)
	public void processIncommingTransfers() {
		System.out.println("CRON: processIncommingTransfers");
		transferService.processIncommingTransfers();
	}

	@Schedule(minute="*/5", hour="*", persistent=false)
	public void processSentTransfers() {
		System.out.println("CRON: processSentTransfers");
		transferService.processSentTransfers();
	}

}
