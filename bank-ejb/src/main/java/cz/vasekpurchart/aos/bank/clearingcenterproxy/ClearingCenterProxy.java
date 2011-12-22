package cz.vasekpurchart.aos.bank.clearingcenterproxy;

import cz.cvut.felk.support.aos.sw.clearingcenter.ClearingCenter;
import cz.cvut.felk.support.aos.sw.clearingcenter.ClearingCenterImplService;
import cz.cvut.felk.support.aos.sw.clearingcenter.FetchPaymentsFaultException_Exception;
import cz.cvut.felk.support.aos.sw.clearingcenter.GetPaymentsResultFaultException_Exception;
import cz.cvut.felk.support.aos.sw.clearingcenter.Payment;
import cz.cvut.felk.support.aos.sw.clearingcenter.RegisterFaultException_Exception;
import cz.cvut.felk.support.aos.sw.clearingcenter.SendPaymentsFaultException_Exception;
import cz.cvut.felk.support.aos.sw.clearingcenter.SetPaymentsResultFaultException_Exception;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@Stateless
@WebService(endpointInterface="cz.vasekpurchart.aos.bank.clearingcenterproxy.ClearingCenterProxyWS")
public class ClearingCenterProxy implements ClearingCenterProxyWS {

	private ClearingCenter clearingCenter;

	public ClearingCenterProxy() {
		ClearingCenterImplService service = new ClearingCenterImplService();
		clearingCenter = service.getClearingCenterImplPort();
	}

	@Override
	public List<Payment> getPaymentsResult(String bankKey, List<Long> paymentIds) throws GetPaymentsResultException {
		try {
			return clearingCenter.getPaymentsResult(bankKey, paymentIds);
		} catch (GetPaymentsResultFaultException_Exception ex) {
			throw new GetPaymentsResultException();
		}
	}

	@Override
	public void setPaymentsResult(String bankKey, List<Payment> paymentsWithResults) throws SetPaymentsResultException {
		try {
			clearingCenter.setPaymentsResult(bankKey, paymentsWithResults);
		} catch (SetPaymentsResultFaultException_Exception ex) {
			throw new SetPaymentsResultException();
		}
	}

	@Override
	public List<Payment> fetchPayments(String bankKey) throws FetchPaymentsException {
		try {
			return clearingCenter.fetchPayments(bankKey);
		} catch (FetchPaymentsFaultException_Exception ex) {
			throw new FetchPaymentsException();
		}
	}

	@Override
	public List<Long> sendPayments(String bankKey, List<Payment> payments) throws SendPaymentsException {
		try {
			return clearingCenter.sendPayments(bankKey, payments);
		} catch (SendPaymentsFaultException_Exception ex) {
			throw new SendPaymentsException();
		}
	}

	@Override
	public String register(String bankCode) throws RegisterException {
		try {
			return clearingCenter.register(bankCode);
		} catch (RegisterFaultException_Exception ex) {
			throw new RegisterException();
		}
	}



}
