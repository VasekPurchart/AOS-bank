package cz.vasekpurchart.aos.bank.backend.transfer;

import cz.vasekpurchart.aos.bank.backend.UnsupportedCurrencyException;
import cz.vasekpurchart.aos.bank.backend.account.AccountService;
import cz.cvut.felk.support.aos.sw.clearingcenter.PaymentResult;
import cz.vasekpurchart.aos.bank.backend.ClearingCenterAdapter;
import cz.vasekpurchart.aos.bank.backend.account.InvalidAccountException;
import cz.vasekpurchart.aos.bank.backend.account.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author vasek
 */
public class TransferService {

	private AccountService accountService;

	private TransferRepository transferRepository;

	private ClearingCenterAdapter clearingCenter;

	public static final String bankCode = "purchva1";

	private String bankKey;

	@Inject
	public TransferService(AccountService accountService, TransferRepository transferRepository, ClearingCenterAdapter clearingCenter) {
		this.accountService = accountService;
		this.transferRepository = transferRepository;
		this.clearingCenter = clearingCenter;
		this.bankKey = clearingCenter.register(bankCode);
	}

	public Transfer createTransfer(int myAccount, String myBank, int targetAccount, String targetBank, BigDecimal amount, String currency, Transfer.Type type) {
		Transfer transfer = new Transfer();
		transfer.setAccountNumber(myAccount);
		transfer.setBankCode(myBank);
		transfer.setRemoteAccountNumber(targetAccount);
		transfer.setRemoteBankCode(targetBank);
		transfer.setAmount(amount);
		transfer.setCurrency(currency);
		transfer.setType(type);

		transferRepository.create(transfer);

		return transfer;
	}

	public void transferMoney(int myAccount, int targetAccount, String targetBank, BigDecimal amount, String currency) throws InvalidAccountException, UnsupportedCurrencyException, NotEnoughMoneyException {
		Transfer transfer = createTransfer(myAccount, bankKey, targetAccount, targetBank, amount, currency, Transfer.Type.DEBET);
		accountService.withdrawMoney(myAccount, amount, currency);
		if (bankKey.equals(targetBank)) {
			transfer.setPaymentResult(PaymentResult.OK);
			accountService.depositMoney(targetAccount, amount, currency);
			Transfer targetTransfer = createTransfer(targetAccount, targetBank, myAccount, bankKey, amount, currency, Transfer.Type.CREDIT);
			targetTransfer.setPaymentResult(PaymentResult.OK);
		}
	}

	// TODO schedule
	public void processOutgoingTransfers() {
		List<Transfer> transferList = transferRepository.findTransfersToSend();
		List<Long> ids = clearingCenter.sendPayments(bankKey, transferList);
		for (int i = 0; i < ids.size(); i++) {
			transferList.get(i).setUniversalId(ids.get(i));
		}
	}

	// TODO schedule
	public void processSentTransfers() {
		List<Transfer> transferList = transferRepository.findSentTransfers();
		Map<Long, PaymentResult> results = clearingCenter.getPaymentResults(bankKey, transferList);
		for (Map.Entry<Long, PaymentResult> entry : results.entrySet()) {
			Transfer transfer = transferRepository.find(entry.getKey());
			transfer.setPaymentResult(entry.getValue());
			if (entry.getValue() == PaymentResult.NO_SUCH_TARGET_ACCOUNT_ERROR || entry.getValue() == PaymentResult.UNKNOWN_ERROR) {
				try {
					accountService.depositMoney(transfer.getAccountNumber(), transfer.getAmount(), transfer.getCurrency());
				} catch (InvalidAccountException ex) {
					Logger.getLogger(TransferService.class.getName()).log(Level.SEVERE, null, ex);
				} catch (UnsupportedCurrencyException ex) {
					Logger.getLogger(TransferService.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	// TODO schedule
	public void processIncommingTransfers() {
		List<Transfer> transferList = clearingCenter.fetchPayments(bankKey);
		for (Transfer transfer : transferList) {
			try {
				accountService.depositMoney(transfer.getAccountNumber(), transfer.getAmount(), transfer.getCurrency());
				transfer.setPaymentResult(PaymentResult.OK);
			} catch (InvalidAccountException ex) {
				transfer.setPaymentResult(PaymentResult.NO_SUCH_TARGET_ACCOUNT_ERROR);
			} catch (UnsupportedCurrencyException ex) {
				transfer.setPaymentResult(PaymentResult.UNKNOWN_ERROR);
			}
			transferRepository.create(transfer);
			clearingCenter.setPaymentResults(bankKey, transferList);
		}
	}

}
