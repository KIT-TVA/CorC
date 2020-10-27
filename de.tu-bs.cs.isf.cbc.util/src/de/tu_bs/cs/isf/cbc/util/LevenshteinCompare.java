package de.tu_bs.cs.isf.cbc.util;

/**
 * Provides a string comparison according to the Levenshtein Distance.
 * http://en.wikipedia.org/wiki/Levenshtein_distance#Iterative_with_two_matrix_rows
 *
 * @author David Wille edited by Tobias Runge
 */
public class LevenshteinCompare {

	/**
	 * calculates the similarity of two Strings
	 * @param s1	String number one
	 * @param s2	String number two
	 * @return		the similarity value of the two string (0-1)
	 */
	public static double calculateSimilarity(String s1, String s2) {
		int distance = getDistance(s1, s2);
		int maxLength = Math.max(s1.length(), s2.length());
		double similarity = (double)(maxLength-distance)/maxLength;
		
		return similarity;
	}

	/**
	 * This method returns the Levenshtein Distance of two Strings.
	 * The Levenshtein Distance shows how many of the characters of String
	 * number two need to be changed in order to get String one.
	 * @param s1	String number one
	 * @param s2	String number two, which is compared to String number two
	 * @return		the Levenshtein Distance of the two Strings
	 */
	private static int getDistance(String s1, String s2) {
		// degenerate cases
		if (s1.equals(s2)) {
			return 0;
		}
		else if (s1.length() == 0) {
			return s2.length();
		}
		else if (s2.length() == 0) {
			return s1.length();
		}
		
		// create two work vectors of integer distances
		int[] v0 = new int[s2.length()+1];
		int[] v1 = new int[s2.length()+1];

		// initialize v0 (the previous row of distances)
		// this row is A[0][i]: edit distance for an empty s
		// the distance is just the number of characters to delete from t		
		for (int i = 0; i < v0.length; i++) {
			v0[i] = i;
		}
		
		// calculate v1 (current row distances) from the previous row v0
		for (int i = 0; i < s1.length(); i++) {
			// first element of v1 is A[i+1][0]
			// edit distance is delete (i+1) chars from s to match empty t
			v1[0] = i+1;
			
			// use formula to fill in the rest of the row
			for (int j = 0; j < s2.length(); j++) {
				String charS0 = "" + s1.charAt(i);
				String charS1 = "" + s2.charAt(j);
				
				int cost = (charS0.equals(charS1)) ? 0 : 1;
				v1[j+1] = Math.min(Math.min(v1[j] + 1, v0[j+1] + 1), v0[j] + cost);
			}
			
			// copy v1 (current row) to v0 (previous row) for next iteration
			for (int j = 0; j < v0.length; j++) {
				v0[j] = v1[j];
			}
		}
	
		return v1[s2.length()];	
	}

}
