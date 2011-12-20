package cz.vasekpurchart.aos.bank.scoring;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

/**
 *
 * @author vasek
 */
@Stateless
@WebService(endpointInterface = "cz.vasekpurchart.aos.bank.scoring.ScoringWS")
public class ScoringFacade implements ScoringWS {

	@Inject
	ScoringService scoringService;

	private ScoringService getScoringService() {
		if (scoringService == null) scoringService = new ScoringService();
		return scoringService;
	}

	@Override
	public float getScoring(int accountId) {
		return getScoringService().getScoring(accountId);
	}

}
