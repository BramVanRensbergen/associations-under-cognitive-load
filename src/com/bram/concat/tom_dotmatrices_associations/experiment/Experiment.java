package com.bram.concat.tom_dotmatrices_associations.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bram.concat.tom_dotmatrices_associations.IO;
import com.bram.concat.tom_dotmatrices_associations.Options;
import com.bram.concat.tom_dotmatrices_associations.Text;
import com.bram.concat.tom_dotmatrices_associations.gui.Gui;
import com.bram.concat.tom_dotmatrices_associations.pattern.NoloadPattern;
import com.bram.concat.tom_dotmatrices_associations.pattern.Pattern;

/**
 * Handles the flow of the experiment.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
public class Experiment {
	/**
	 * Handles the experiment's layout.
	 */
	public static Gui gui;
	
	/**
	 * Handles the flow of the experiment.
	 */
	public static Experiment xp;
	
	/**
	 * The current participant. Created after he/she fills of their information.
	 */
	public static Participant pp;
	
	/**
	 * Used to measure participant's reaction time. Starts when the cue is shown.
	 */
	public long RT_start;
	
	/**
	 * Used internally to track the progress of the experiment. Values can be TRAINING, XP.
	 */
	private int experimentPhase;	
	
	/**
	 * Used to indicate the current phase of the experiment.
	 */
	private static final int TRAINING = 1, XP = 2, INTERBLOCK_INSTRUCTIONS = 3;  
	
	/**
	 * The trial that is currently in progress.
	 */
	private Trial currentTrial;	

	/**
	 * Number of the association the pp is currently thinking about / filling in. Value: 1..Options.N_ASSOCIATIONS.
	 */
	public int currentAssociationIndex;
	
	/**
	 * The TrialGroup that is currently in progress.
	 */
	private TrialGroup currentTrialGroup;
	
	/**
	 * Used to pause the experiment for certain delays, e.g. to display fixation-cross for x ms, or a blank screen for y ms, etc.
	 */
	public Timer timer;
	
	/**
	 * Skips the rest of the trial if participant is too slow to respond.
	 */
	public TimerTask responseTimer;
		
	/**
	 * Used to save the participant's response to the trials in the current trial group. 
	 * They are not written to text directly, but only when the pattern has been reproduced (because we want pattern reproduction scores in each line).
	 */
	private List<String> responseLines;
	
	/**
	 * The index of the current Trial in the complete experiment, i.e. 1..nbOfTrials
	 */
	private int globalTrialNb = 0;
	
	/**
	 * The index of the current TrialGroup in the complete experiment, i.e. 1..nbOfTrialGroups
	 */
	private int globalTrialGroupNb = 0;
	
	/**
	 * Total nb of trial groups in the experiment. Used to determine when to display breaks for the participant.
	 */
	private int nbOfTrialGroups;
	
	public Experiment() {
		IO.readStimuli(); 		  //read stimuli from disk
		Pattern.emptyPattern = new NoloadPattern();		
		experimentPhase = TRAINING;
		timer = new Timer();		
		gui = new Gui();		
		start();
	}
	
	/**
	 * Begin the experiment: ask for the ss's information.
	 */
	private void start() {		
		if (!Options.DEBUG) {
			gui.showSsInfo();		
		} else {
			processSsInfo(1, 18, 'm');
		}		
	}
	
	/**
	 * Create a new data file with the ss's information, then display the instruction.
	 */
	public void processSsInfo(int ssNb, int age, char gender) {
		pp = new Participant(ssNb, age, gender);
		IO.initializeWriting(Options.DATA_FOLDER + ssNb + "_" + age + "_" + gender + "_" + Text.getDate() + "_" + Text.getTime() + ".txt");
		createTrials();
		gui.instructionPanel.showMainInstructions(1);		
	}
	
	private void createTrials() {
		Trial.createTrainingTrials();
		Trial.createTrials();
		nbOfTrialGroups = Trial.experimentTrialGroups.size();
	}
	
	/**
	 * Show the experimental screen, and display the next trial.
	 */
	public void displayAndContinue() {
		gui.showXP();
		startNextTrialGroup();
	}

	/**
	 * Start a new TrialGroup (beginning by showing that TrialGroups pattern). 
	 * If there are no more TrialGroups in the current phase of the experiment, move on to the next phase.
	 */
	private void startNextTrialGroup() {	
		if (experimentPhase == TRAINING) { //we're in the first block's training phase 
			if (!Trial.trainingTrialGroups.isEmpty()) { //start next training block
				startTrialGroup(Trial.trainingTrialGroups.remove(0));
			} else { //all training blocks shown, show post-training instructions
				experimentPhase = XP;
				gui.instructionPanel.showFirstBlockPostTrainingInstructions();
			}
		} else {
			if (Trial.experimentTrialGroups.isEmpty()) { // xp over!
				gui.instructionPanel.showXpOverText();
			} else {
				if (globalTrialGroupNb > 0 && experimentPhase == XP && (globalTrialGroupNb % (nbOfTrialGroups / Options.N_BLOCKS) == 0)) {
					//pp should take a break!
					experimentPhase = INTERBLOCK_INSTRUCTIONS;	
					boolean lastblock = globalTrialGroupNb == nbOfTrialGroups * (Options.N_BLOCKS - 1) / Options.N_BLOCKS; // TRUE if the upcoming block is the last one
					gui.instructionPanel.showInterBlockInstructions(lastblock);
				} else {
					//just show the next trialGroup!
					if (experimentPhase == INTERBLOCK_INSTRUCTIONS) {
						experimentPhase = XP;
					}
					globalTrialGroupNb++;
					startTrialGroup(Trial.experimentTrialGroups.remove(0));
				}
			}
		}
	}
	
	/**
	 * Start the indicated TrialGroup, beginning by showing it's pre-pattern fixation cross.
	 */
	private void startTrialGroup(TrialGroup trialGroup) {
		currentTrialGroup = trialGroup;
		responseLines = new ArrayList<String>();
		showPrePatternFixationCross();
	}

	/**
	 * Display a fixation-cross for Options.FIXATION_DURATION MS; after this, show the pattern reproduction screen.
	 */
	private void showPrePatternFixationCross() {
		gui.xpPane.showFixationCross();
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	showPattern();
		    }
		}, Options.FIXATION_DURATION);	
	}
	
	/**
	 * Display the indicated pattern on the screen. After Options.PATTERN_DURATION MS, the pattern is removed, and the next trial starts.
	 */
	private void showPattern() {
		gui.hideCursor();
		gui.xpPane.showPattern(currentTrialGroup.pattern);

		timer.schedule(new TimerTask() {          
			@Override
			public void run() {
				startNextTrial();    
			}
		}, Options.PATTERN_DURATION);
	} 
	
	/**
	 * Start the next Trial of the current TrialGroup; if there are no more Trials in this TrialGroup, move on to pattern reproduction.	
	 */
	private void startNextTrial() {		
		gui.showCursor();
		
		if (!currentTrialGroup.isEmpty()) {
			if (experimentPhase == XP) globalTrialNb++;
			currentTrial = currentTrialGroup.remove(0);
			showPreTrialBlankScreen();
		} else {
			showPatternReproduction();
		}
	}
	
	/**
	 * Show a blank screen before showing a new cue.
	 */
	private void showPreTrialBlankScreen() {
		gui.xpPane.showBlankScreen();
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	showCue();
		    }
		}, Options.INTERTRIAL_DELAY);	
	}
	
	/**
	 * Display the next cue.
	 */
	private void showCue() {
		currentAssociationIndex = 1;
		gui.xpPane.showCue(currentTrial.cue);
		RT_start = System.currentTimeMillis();
		addResponseTimer();
	}
	
	/**
	 * Add a timer that skips the rest of the trial is participants responds too slowly.
	 * Any existing response timers are first destroyed.
	 */
	private void addResponseTimer() {
		removeResponseTimer();
		responseTimer = new TimerTask() {
			 @Override
			    public void run() {
				 gui.xpPane.showTooLateError();
				 
				 timer.schedule(new TimerTask() {          
					    @Override
					    public void run() {
					    	skipFurtherAssociations("__TOO_LATE__");
					    }
					}, Options.ERROR_DURATION);				 
				 	
			    }
		};
		timer.schedule(responseTimer, Options.MAX_RESPONSE_DURATION);	
	}
	
	public void removeResponseTimer() {
		if (responseTimer != null) {
			responseTimer.cancel();
		}
	}
	
	
	/**
	 * Save the participants response to the current Trial.  
	 * They are not written to text directly, but only when the pattern has been reproduced (because we want pattern reproduction scores in each line).
	 */
	public void submitResponse(String association, long timeAtFirstKeypress, long timeAtSubmission) {
				
		Trial t = currentTrial;		
		String responseLine = globalTrialNb + "\t" + globalTrialGroupNb + "\t" + (t.indexInTrialGroup + 1) + "\t" + t.cue + "\t" + association + "\t"
				 + currentAssociationIndex + "\t" + (timeAtFirstKeypress - RT_start) + "\t" + (timeAtSubmission - RT_start) + "\t" + t.list;
		responseLines.add(responseLine);	
		
		if (currentAssociationIndex < Options.N_ASSOCIATIONS) {		
			currentAssociationIndex++;
			addResponseTimer(); //reset timer
			//just waiting for pp to give more answers!
		} else {
			removeResponseTimer(); //remove timer
			startNextTrial();
		}
	}	
	
	/**
	 * Participant clicked 'unknown word' or 'no further associations': write down as such, move to next cue.
	 */
	public void skipFurtherAssociations(String responseErrorMessage) {		
		for (int iAsso = currentAssociationIndex; iAsso <= Options.N_ASSOCIATIONS; iAsso++) {
			submitResponse(responseErrorMessage,  RT_start - 1, RT_start -1);
		}	
	}
	
	/**
	 * Show the screen that allows the user to reproduce the pattern he/she recalls.
	 */
	private void showPatternReproduction() {		
		Pattern.emptyPattern.clearDots();	
		gui.xpPane.showPatternReproduction();    
	}

	/**
	 * Correct the pattern produced by the ss; if the pattern is part of the experimental phase (not the training phase), write the score to text. 
	 * After this, the inter-trial blank screen is displayed.
	 */
	public void correctPatternReproduction() {
		if (experimentPhase == XP) {       //only write away responses if this an actual trial		
			int[] originalDots = currentTrialGroup.pattern.dotArray;     //original pattern, presented at the beginning of the trial
			int[] responseDots = Pattern.emptyPattern.computeDotArray(); //pattern reproduced by the ss
			
			int hits = 0; 					//nb of dots correctly reproduced
			int misses = 0; 				//nb of dots that were not reproduced
			int falseAlarms = 0; 			//nb of dots placed in a spot where there was no dot in the original pattern 
			String originalDotString = "";  //16-digits (0 = no dot, 1 = dot) representation of the original 4x4 matrix
			String responseDotString = "";  //16-digits (0 = no dot, 1 = dot) representation of the user's response
			for (int iDot = 0; iDot < originalDots.length; iDot++) { //go through the 16 squares
				if (originalDots[iDot] == 1 && responseDots[iDot] == 1) hits++;
				if (originalDots[iDot] == 1 && responseDots[iDot] == 0) misses++;
				if (originalDots[iDot] == 0 && responseDots[iDot] == 1) falseAlarms++;
				originalDotString += originalDots[iDot]; //add a 0 or a 1, depending on whether there's a dot in this square in the original pattern
				responseDotString += responseDots[iDot]; //add a 0 or a 1, depending on whether there's a dot in this square in the reproduced pattern
			}
			
			String patternResponseLine = currentTrialGroup.pattern.loadString + "\t" + originalDotString + "\t" + responseDotString + "\t" 
						+ (originalDotString.equals(responseDotString) ? 1 : 0) + "\t" + hits + "\t" + misses + "\t" + falseAlarms;
			
			for (String responseLine : responseLines) {
				IO.writeData(responseLine + "\t" + patternResponseLine);
			}
		}		
		showInterPatternBlankScreen();	
	}

	/**
	 * A blank screen, after reproduction of the previous pattern, before displaying the subsequent one.
	 */
	void showInterPatternBlankScreen() {
		gui.xpPane.showBlankScreen();
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	startNextTrialGroup();    
		    }
		}, Options.INTERN_PATTERN_DELAY);
	}
}