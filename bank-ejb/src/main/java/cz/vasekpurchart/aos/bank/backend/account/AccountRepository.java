package cz.vasekpurchart.aos.bank.backend.account;

import cz.vasekpurchart.aos.bank.backend.model.AbstractRepository;
import javax.persistence.EntityManager;

/**
 *
 * @author Vašek Purchart
 */
public class AccountRepository extends AbstractRepository<Account> {

	public AccountRepository() {
		super(Account.class);
	}

	public AccountRepository(EntityManager em) {
		super(Account.class, em);
	}

}
