package cz.vasekpurchart.aos.bank.backend.transfer;

import cz.cvut.felk.support.aos.sw.clearingcenter.PaymentResult;
import cz.vasekpurchart.aos.bank.backend.model.SimpleEntity;
import java.math.BigDecimal;
import javax.persistence.Entity;

/**
 *
 * @author vasek
 */
@Entity
public class Transfer extends SimpleEntity {

	private Long universalId;
	private int accountNumber;
    private BigDecimal amount;
    private String bankCode;
    private String currency;
    private PaymentResult paymentResult;
    private int remoteAccountNumber;
    private String remoteBankCode;
	private Type type;

	public Transfer() {
		paymentResult = PaymentResult.NOTSET;
	}

	public Long getUniversalId() {
		return universalId;
	}

	public void setUniversalId(Long universalId) {
		this.universalId = universalId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public PaymentResult getPaymentResult() {
		return paymentResult;
	}

	public void setPaymentResult(PaymentResult paymentResult) {
		this.paymentResult = paymentResult;
	}

	public int getRemoteAccountNumber() {
		return remoteAccountNumber;
	}

	public void setRemoteAccountNumber(int remoteAccountNumber) {
		this.remoteAccountNumber = remoteAccountNumber;
	}

	public String getRemoteBankCode() {
		return remoteBankCode;
	}

	public void setRemoteBankCode(String remoteBankCode) {
		this.remoteBankCode = remoteBankCode;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		CREDIT, DEBET;
	}

}
