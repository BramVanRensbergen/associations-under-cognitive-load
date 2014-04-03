package com.bram.concat.tom_dotmatrices_associations.experiment;

import java.util.ArrayList;
import java.util.List;

import com.bram.concat.tom_dotmatrices_associations.Options;
import com.bram.concat.tom_dotmatrices_associations.pattern.HighloadPattern;
import com.bram.concat.tom_dotmatrices_associations.pattern.LowloadPattern;
import com.bram.concat.tom_dotmatrices_associations.pattern.Pattern;

@SuppressWarnings("serial")
public class TrialGroup extends ArrayList<Trial>{
		
	 /**
     * @return The trials in TrialGroups containing Options.TRIALS_PER_GROUP trials (defaults to 5).
     */
    public static List<TrialGroup> createTrialGroups(List<Trial> trials){
    	List<TrialGroup> trialGroups = new ArrayList<TrialGroup>();
    	
    	while (!trials.isEmpty()) {
    		List<Trial> trialsInThisGroup = new ArrayList<Trial>();
			
    		for (int iTrial = 0; iTrial < Options.TRIALS_PER_GROUP; iTrial++) { //0 to 4
				if (!trials.isEmpty()) {
					trialsInThisGroup.add(trials.remove(0));
				} else {
					System.err.println("Error: not enough trials left to create group of five trials!");
				}
			}
			trialGroups.add(new TrialGroup(trialsInThisGroup, trialsInThisGroup.get(0).lowLoad));
		}    	
    	return trialGroups;
    }

	public Pattern pattern;
		
	public TrialGroup(List<Trial> trials, boolean lowLoad) {
		super(trials);
		if (trials.size() != Options.TRIALS_PER_GROUP) {
			System.err.println("Error: trying to create a group of five trials, but " + trials.size() + " trials were passed to the constructor!");
		} else {			
			for (int i = 0; i < trials.size(); i++) {
				trials.get(i).indexInTrialGroup = i;
			}
			
			if (lowLoad) {
				pattern = new LowloadPattern();
			} else {
				pattern = new HighloadPattern();
			}
		}
		
	}
	
}
