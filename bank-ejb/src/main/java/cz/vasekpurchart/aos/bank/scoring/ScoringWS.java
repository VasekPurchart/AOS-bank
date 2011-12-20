package cz.vasekpurchart.aos.bank.scoring;

import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@WebService
public interface ScoringWS {

	public float getScoring(int accountId);

}
