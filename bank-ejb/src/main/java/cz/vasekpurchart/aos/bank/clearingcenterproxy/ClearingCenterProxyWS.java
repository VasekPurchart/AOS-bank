package cz.vasekpurchart.aos.bank.clearingcenterproxy;

import cz.cvut.felk.support.aos.sw.clearingcenter.Payment;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@WebService
public interface ClearingCenterProxyWS {

	public List<Payment> getPaymentsResult(String bankKey, List<Long> paymentIds) throws GetPaymentsResultException;

	public void setPaymentsResult(String bankKey, List<Payment> paymentsWithResults) throws SetPaymentsResultException;

	public List<Payment> fetchPayments(String bankKey) throws FetchPaymentsException;

	public List<Long> sendPayments(String bankKey, List<Payment> payments) throws SendPaymentsException;

	public String register(String bankCode) throws RegisterException;

}
