package com.bram.concat.tom_dotmatrices_associations.pattern.validity;

/**
 * Check whether the indicated pattern of dots would be accepted by the method described in:
 * Ichikawa, S (1983). Verbal memory span, visual memory span, and their correlations with cognitive tasks. Japanese Psychological Research 25(4), 173-180.
 */
public abstract class CRC {	
	/**
	 * Any pattern with a CRC value below this is accepted.
	 * Computed as follows:
	 * 		Calculated the CRC value of 50000 patterns of four dots placed completely randomly in a 4x4 matrix.
	 * 		The cutoff value of 0.329 corresponds to the mean of the 50000 values minus the standard deviation. 
	 */
	public static final double CRC_CUTOFF = 0.3293491;
	
	public static boolean validByCrc(int[][] squares) {
		return computeCRC(squares) < CRC_CUTOFF;
	}	
	
	private static double computeCRC(int[][] squares) {
		//calculate CR
		double numerator = 0;	//start by computing the numerator
		for (int row = 0; row < 4; row++) {				
			double pi = 0; //nb of dots in this row divided by total number of dots
			for (int col = 0; col < 4; col++) pi += squares[row][col];
			pi = pi / 4;
			
			if (pi != 0) numerator += pi * log2(1/pi); //avoid division by zero; we assume that 0 * Log₂0 = 0			
		}
		double CR = 1 - numerator / log2(4);
		
		//calculate CC
		numerator = 0;		
		for (int col = 0; col < 4; col++) {		
			double pi = 0; //nb of dots in this col divided by total number of dots
			for(int row = 0; row < 4; row++) pi += squares[row][col]; 
			pi = pi / 4;
			
			if (pi != 0) numerator += pi * log2(1/pi); //avoid division by zero; we assume that 0 * Log₂0 = 0			
		}		
		double CC = 1 - numerator / log2(4);
		
		return 1 - (( 1 - CR ) * ( 1 - CC)); //return CRC
	}
	
	private static double log2(double val) {
		return Math.log(val) / Math.log(2);
	}
}
