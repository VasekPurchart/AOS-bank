package cz.vasekpurchart.aos.bank.backend.transfer;

import cz.cvut.felk.support.aos.sw.clearingcenter.PaymentResult;
import cz.vasekpurchart.aos.bank.backend.model.AbstractRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Vašek Purchart
 */
public class TransferRepository extends AbstractRepository<Transfer> {

	public TransferRepository() {
		super(Transfer.class);
	}

	public TransferRepository(EntityManager em) {
		super(Transfer.class, em);
	}

	public List<Transfer> findTransfersToSend() {
		Query q = em.createQuery("SELECT t FROM Transfer t WHERE t.paymentResult = :paymentResult AND t.universalId is null");
		q.setParameter("paymentResult", PaymentResult.NOTSET);
		return q.getResultList();
	}

	public List<Transfer> findSentTransfers() {
		Query q = em.createQuery("SELECT t FROM Transfer t WHERE t.paymentResult != :paymentResult AND t.universalId is not null");
		q.setParameter("paymentResult", PaymentResult.OK);
		return q.getResultList();
	}

	public List<Transfer> findTransfers(int accountNumber) {
		Query q = em.createQuery("SELECT t FROM Transfer t WHERE t.paymentResult = :paymentResult AND t.accountNumber = :accountNumber");
		q.setParameter("paymentResult", PaymentResult.OK);
		q.setParameter("accountNumber", accountNumber);
		return q.getResultList();
	}

}
