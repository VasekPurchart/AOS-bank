package cz.vasekpurchart.aos.bank.backend;


import cz.vasekpurchart.aos.bank.backend.transfer.Transfer;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.ClearingCenterProxyService;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.ClearingCenterProxyWS;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.FetchPaymentsException_Exception;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.GetPaymentsResultException_Exception;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.Payment;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.PaymentResult;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.RegisterException_Exception;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.SendPaymentsException_Exception;
import cz.vasekpurchart.aos.bank.clearingcenterproxyadapter.SetPaymentsResultException_Exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vasek
 */
public class ClearingCenterAdapter {

	private ClearingCenterProxyWS clearingCenter;

	public ClearingCenterAdapter() {
		ClearingCenterProxyService service = new ClearingCenterProxyService();
		clearingCenter = service.getClearingCenterProxyPort();
	}

	public List<Long> sendPayments(String bankKey, List<Transfer> transferList) {
		List<Payment> payments = new ArrayList<Payment>();
		for (Transfer transfer : transferList) {
			Payment payment = new Payment();
			payment.setSenderAccountNumber(transfer.getAccountNumber());
			payment.setSenderBankCode(transfer.getBankCode());
			payment.setAccountNumber(transfer.getRemoteAccountNumber());
			payment.setBankCode(transfer.getRemoteBankCode());
			payment.setAmount(transfer.getAmount());
			payment.setCurrency(transfer.getCurrency());
			payments.add(payment);
		}

		List<Long> ids = new ArrayList<Long>();
		try {
			ids = clearingCenter.sendPayments(bankKey, payments);
		} catch (SendPaymentsException_Exception ex) {
			Logger.getLogger(ClearingCenterAdapter.class.getName()).log(Level.SEVERE, null, ex);
		}

		return ids;
	}

	public List<Transfer> fetchPayments(String bankKey) {
		List<Transfer> transfers = new ArrayList<Transfer>();
		try {
			List<Payment> payments = clearingCenter.fetchPayments(bankKey);
			for (Payment payment : payments) {
				Transfer transfer = new Transfer();
				transfer.setUniversalId(payment.getId());
				transfer.setRemoteAccountNumber(payment.getSenderAccountNumber());
				transfer.setRemoteBankCode(payment.getSenderBankCode());
				transfer.setAccountNumber(payment.getAccountNumber());
				transfer.setBankCode(payment.getBankCode());
				transfer.setAmount(payment.getAmount());
				transfer.setCurrency(payment.getCurrency());
				transfer.setType(Transfer.Type.CREDIT);
				transfers.add(transfer);
			}
		} catch (FetchPaymentsException_Exception ex) {
			Logger.getLogger(ClearingCenterAdapter.class.getName()).log(Level.SEVERE, null, ex);
		}

		return transfers;
	}

	public void setPaymentResults(String bankKey, List<Transfer> transferList) {
		List<Payment> paymentsWithResults = new ArrayList<Payment>();
		for (Transfer transfer : transferList) {
			Payment payment = new Payment();
			payment.setId(transfer.getUniversalId());
			payment.setSenderAccountNumber(transfer.getRemoteAccountNumber());
			payment.setSenderBankCode(transfer.getRemoteBankCode());
			payment.setAccountNumber(transfer.getAccountNumber());
			payment.setBankCode(transfer.getBankCode());
			payment.setAmount(transfer.getAmount());
			payment.setCurrency(transfer.getCurrency());
			payment.setPaymentResult(transfer.getPaymentResult());
		}
		try {
			clearingCenter.setPaymentsResult(bankKey, paymentsWithResults);
		} catch (SetPaymentsResultException_Exception ex) {
			Logger.getLogger(ClearingCenterAdapter.class.getName()).log(Level.SEVERE, null, ex);
			// TODO resend results
		}
	}

	public Map<Long, PaymentResult> getPaymentResults(String bankKey, List<Transfer> transferList) {
		List<Long> ids = new ArrayList<Long>();
		for (Transfer transfer : transferList) {
			ids.add(transfer.getUniversalId());
		}
		Map<Long, PaymentResult> results = new HashMap<Long, PaymentResult>();
		try {
//			for (Payment payment : clearingCenter.getPaymentsResult(bankKey, ids)) {
//				results.put(payment.getId(), payment.getPaymentResult());
//			}
			List<Payment> payments = clearingCenter.getPaymentsResult(bankKey, ids);
			for (int i = 0; i < payments.size(); i++) {
				results.put(ids.get(i), payments.get(i).getPaymentResult());
			}
		} catch (GetPaymentsResultException_Exception ex) {
			Logger.getLogger(ClearingCenterAdapter.class.getName()).log(Level.SEVERE, null, ex);
		}

		return results;
	}

	public String register(String bankCode) {
		try {
			return clearingCenter.register(bankCode);
		} catch (RegisterException_Exception ex) {
			throw new IllegalStateException();
		}

	}

}

