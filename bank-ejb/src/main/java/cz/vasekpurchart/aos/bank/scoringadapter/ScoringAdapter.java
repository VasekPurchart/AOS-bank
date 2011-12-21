package cz.vasekpurchart.aos.bank.scoringadapter;

/**
 *
 * @author vasek
 */
public class ScoringAdapter {

	ScoringWS scoringWS;

	public ScoringAdapter() {
		ScoringFacadeService service = new ScoringFacadeService();
		scoringWS = service.getScoringFacadePort();
	}

	public float getScoring(int accountNumber) {
		return scoringWS.getScoring(accountNumber);
	}

}
