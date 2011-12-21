package cz.vasekpurchart.aos.bank.backend.account;

import cz.vasekpurchart.aos.bank.backend.model.SimpleEntity;
import java.math.BigDecimal;
import javax.persistence.Entity;

/**
 *
 * @author vasek
 */
@Entity
public class Account extends SimpleEntity {

	private String name;

	private String currency;

	private BigDecimal amount;

	private BigDecimal debt;

	public Account() {
	}

	public Account(String name, String currency) {
		this.name = name;
		this.currency = currency;
		this.amount = new BigDecimal(0);
		this.debt = new BigDecimal(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDebt() {
		return debt;
	}

	public void setDebt(BigDecimal debt) {
		this.debt = debt;
	}

}
