package com.bram.concat.tom_dotmatrices_associations.experiment;


/**
 * Represents the user of the experiment.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
public class Participant {
	
	/**
	 * The participant's number. Should be given to him/her by the experimenter.
	 */
	public int ssNb;	
	public int age;		
	public char gender;
	
	public Participant(int ssNb, int age, char gender) {
		this.ssNb = ssNb;		
		this.age = age;		
		this.gender = gender;
	}
}
