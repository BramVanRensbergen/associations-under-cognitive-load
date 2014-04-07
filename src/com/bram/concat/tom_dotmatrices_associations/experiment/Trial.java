package com.bram.concat.tom_dotmatrices_associations.experiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bram.concat.tom_dotmatrices_associations.IO;

/**
 * Represents one trial in the experiment.
 * Each trial begins with a fixation cross, then the prime, a blank screen, the target, and finally another blank screen.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
public class Trial {
	
	/**
	 * TrialGroups (each contains five trials) that will be used in the training phase.
	 */
	public static List<TrialGroup> trainingTrialGroups; 
		
	/**
	 * TrialGroups (each contains five trials) that will be used in the experiment (does not include training trials).
	 */
	public static List<TrialGroup> experimentTrialGroups;
    	
	/**
	 * Full factorial of 2 conditions (lowLoad|highload), in randomized order.
	 * (Yes, this is overengineered for the current setup, but allows for easy adding of extra conditions.)
	 */
	private static final boolean[][] CONDITION_MATRIX = { 
		{true},
		{false}
	};   
    
    public static void createTrials() {
    	if (Experiment.pp == null) {
    		System.err.println("Cannot create trials before participant is defined!");
    	} else {
    		int ssOffset = Experiment.pp.ssNb % CONDITION_MATRIX.length; //1 to 2
    		if (ssOffset == 0) ssOffset = CONDITION_MATRIX.length;

    		//create all trials, in 8 groups of 40 (one group per condition)
    		List<Trial> lowLoadTrials = new ArrayList<Trial>();
    		List<Trial> highLoadTrials = new ArrayList<Trial>();

    		while (!IO.stimStrings.isEmpty()) {
    			String[] trialLine = IO.stimStrings.remove(0); //the line containing the text of this trial    		
    			String cue = trialLine[0];
    			int list = Integer.parseInt(trialLine[1]);
    			    			
    			boolean[] condition = CONDITION_MATRIX[(ssOffset - 1) % CONDITION_MATRIX.length]; //-1 because ssOffset starts at 1, while conditionMatrix starts at 0
    			boolean list1lowload = condition[0];

    			boolean lowLoad = ((list == 1 && list1lowload) || (list == 2 && !list1lowload));
    			
    			Trial t = new Trial(cue, lowLoad, false, list);
    			
    			if	(lowLoad)   lowLoadTrials.add(t);
    			else 			highLoadTrials.add(t);  			
    		}
    		    		
    		//randomize the order within each list
    		Collections.shuffle(lowLoadTrials);
    		Collections.shuffle(highLoadTrials);

    		experimentTrialGroups = new ArrayList<TrialGroup>();
    		experimentTrialGroups.addAll(TrialGroup.createTrialGroups(lowLoadTrials));
    		experimentTrialGroups.addAll(TrialGroup.createTrialGroups(highLoadTrials));   		
    		Collections.shuffle(experimentTrialGroups);    		
    	}
    }      
    
    public static void createTrainingTrials() {
    	List<Trial> lowLoadTrainingTrials = new ArrayList<Trial>();
    	lowLoadTrainingTrials.add(new Trial("menselijk", true, true, -1));
    	lowLoadTrainingTrials.add(new Trial("na", true, true, -1));
    	lowLoadTrainingTrials.add(new Trial("imiteren", true, true, -1));
    	lowLoadTrainingTrials.add(new Trial("slak", true, true, -1));
    	lowLoadTrainingTrials.add(new Trial("larie", true, true, -1));
    	
    	List<Trial> highLoadTrainingTrials = new ArrayList<Trial>();
    	highLoadTrainingTrials.add(new Trial("serie", false, true, -1));
    	highLoadTrainingTrials.add(new Trial("overschot", false, true, -1));
    	highLoadTrainingTrials.add(new Trial("honing", false, true, -1));
    	highLoadTrainingTrials.add(new Trial("bloed", false, true, -1));
    	highLoadTrainingTrials.add(new Trial("gelukkig", false, true, -1));
    	
    	trainingTrialGroups = new ArrayList<TrialGroup>(); 
    	trainingTrialGroups.add(new TrialGroup(lowLoadTrainingTrials, true)); 
    	trainingTrialGroups.add(new TrialGroup(highLoadTrainingTrials, false));
    	//more trialgroups can be added for a longer training phase
    }
      
    
	public String cue;
	
	/**
	 * true: simple pattern (see pattern.LowloadPattern)
	 * false: complex pattern (see pattern.HighloadPattern)
	 */
	public boolean lowLoad;
	
	/**
	 * Textual representation of the indicated condition, for this Trial. Used when writing away the data to disk. 
	 */
	public String loadString;
	
	public int list;
	
	/**
	 * Index of this trial in its trial group i.e. 0..4
	 */
	public int indexInTrialGroup; 
	
    private Trial(String cue, boolean lowLoad, boolean trainingTrial, int list) {
        this.cue = cue;
        this.lowLoad = lowLoad;
        this.list = list;
        loadString = (lowLoad ? "LOW_LOAD" : "HIGH_LOAD");    
    }
}
