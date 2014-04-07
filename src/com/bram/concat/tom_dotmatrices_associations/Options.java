package com.bram.concat.tom_dotmatrices_associations;


import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
public abstract class Options {
	/*
	 * FEEL FREE TO CHANGE THESE
	 */
	
	public static final boolean DEBUG = false;
	
	/**
	 * Data files are written out to this directory.
	 */
	public static final String DATA_FOLDER    = "data/";
	
	public static final String STIM_FILE = "stimuli.txt";
	
	/**
	 * True: gui has no titlebar, close-buttons, etc.
	 */
	public static final boolean UNDECORATED   = true;
	
	/**
	 * Pattern (that the ss will have to reproduce later) will be displayed for this many MS. 
	 * Default value: 750 MS.
	 */
	public static final int PATTERN_DURATION  = (DEBUG ? 75 : 750); 
	
	/**
	 * Fixation cross will be displayed for this many MS. 
	 * Default value: 500 MS.
	 */
	public static final int FIXATION_DURATION = (DEBUG ? 50 : 500);
	
	/**
	 * Delay between two trials.
	 * Default: 500ms. 
	 */
	public static final int INTERTRIAL_DELAY  = (DEBUG ? 180 : 500);

	/**
	 * Delay between the pattern reproduction screen of one trialGroup, and the pattern screen of the subsequent trialGroup.
	 * Default: 2000ms.
	 */
	public static final int INTERN_PATTERN_DELAY  = (DEBUG ? 200 : 2000);
	
	/**
	 * Participants have this many MS to give each association; any longer, and the program skips to the next trial.
	 */
	public static final int MAX_RESPONSE_DURATION = (DEBUG ? 1000 : 7000);
	
	/**
	 * When participants answer too slowly, an error is displayed for this many MS before moving on.
	 */
	public static final int ERROR_DURATION = (DEBUG ? 1000 : 1000);
	
	/**
	 * Number of associations participants give to each cue. Defaults to 3; using very high values may mess with the UI.
	 */
	public static final int N_ASSOCIATIONS = 3;
	
	/**
	 * The experiment will be split up in this many blocks, with a break for participants between each block.
	 */
	public static final int N_BLOCKS = 3;
	
	/*
	 * ONLY CHANGE THESE IF YOU KNOW WHAT YOU ARE DOING
	 */
	
	/**
	 * Width and height of each cell of the grid in which the dot-pattern is displayed.
	 */
	public static final int GRID_PIXELSIZE    = 150;
	
	/**
	 * Size (in pixels) of the gui.
	 */
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//computers current resolution 

	/**
	 * Number of trials in one group; each group is assigned one pattern.
	 */
	public static final int TRIALS_PER_GROUP = 5;
}
