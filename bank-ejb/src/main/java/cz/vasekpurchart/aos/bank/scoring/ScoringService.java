package cz.vasekpurchart.aos.bank.scoring;

import java.util.Random;

/**
 *
 * @author vasek
 */
public class ScoringService {

	public float getScoring(int accountId) {
		Random r = new Random();
		return r.nextFloat();
	}

}
