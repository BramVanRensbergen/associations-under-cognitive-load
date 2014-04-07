package com.bram.concat.tom_dotmatrices_associations.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.bram.concat.tom_dotmatrices_associations.Options;
import com.bram.concat.tom_dotmatrices_associations.Text;
import com.bram.concat.tom_dotmatrices_associations.experiment.Experiment;
import com.bram.concat.tom_dotmatrices_associations.pattern.Pattern;

/**
 * Panel used to display the actual experiment; contains the dot-pattern, a fixation-cross, the pattern-reproduction screen, or a blank screen.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
@SuppressWarnings("serial")
public class ExperimentPanel extends JPanel {				
	
	/**
	 * The cue to which the ss is asked to give associations.
	 */
	private JLabel cue;
		
	/**
	 * Text that displays the participants first two responses to the cue.
	 */
	private JLabel[] previousAssociationLabels;   

	/**
	 * Ss enters their responses i.e. associations in this.
	 */
	private JTextField responseField;	
	
	/**
	 * Ss can click this to move to the next cue, because they don't know the word, or because they don't have any more associations.
	 */
	private SkipButton skipButton;					  
	
	private JLabel fixationCross;
	
	/**
	 * A button the ss can use to indicate he/she finished reproducing the pattern.
	 */
	private JButton patternReproductionDoneButton; 
		
	/**
	 * Used to record the time of the first keypress of each association, and to listen to 'enter' to submit an answer.
	 */
    private KeyboardFocusManager keyboardManager;
    
    /**
	 * Used to record the time of the first keypress of each association, and to listen to 'enter' to submit an answer.
	 */
	private ReponseDispatcher responseDispatcher;
	
	/**
	 * For each association, we record when ss began typing (first key pressed). If this is set to true, timeAtAssociationsFirstKeypress is set at the first keypress.
	 */
	private boolean listenToFirstKeypress;
	
	/**
	 * For each association, we record when ss began typing (first key pressed).
	 */
	private long timeAtAssociationsFirstKeypress;
	
	ExperimentPanel() {				
		setLayout(null);
		setVisible(true);	
		
		cue = new JLabel("");
		cue.setHorizontalAlignment(SwingConstants.CENTER);
		cue.setBounds(Options.screenSize.width / 2 - 400 / 2, 100, 400, 100);
		cue.setFont(Text.cueFont);
		
		previousAssociationLabels = new JLabel[Options.N_ASSOCIATIONS - 1];
		for (int i = 0; i < Options.N_ASSOCIATIONS - 1; i++) {
			previousAssociationLabels[i] = new JLabel("");
			previousAssociationLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			previousAssociationLabels[i].setBounds(Options.screenSize.width / 2 - 400 / 2, 150 + (1 + i) * 50, 400, 50);
			previousAssociationLabels[i].setForeground(Color.gray);
			previousAssociationLabels[i].setFont(Text.prevAssoFont);
		}

		responseField = new JTextField(50);
		responseField.setBounds(Options.screenSize.width/2 - 200 / 2, Options.screenSize.height - 500, 200, 55);
		responseField.setFont(Text.assoTextfieldFont);
		
		skipButton = new SkipButton();
		skipButton.setBounds(Options.screenSize.width - 200 - 75, Options.screenSize.height - 50 - 75, 200, 50);
		
		patternReproductionDoneButton = new JButton("Klaar"); //add ok button, to end pattern reproduction (not displayed yet)
		patternReproductionDoneButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, start the experiment		
			Experiment.xp.correctPatternReproduction();
		}});
		int heightOffset = Options.screenSize.height - (Options.screenSize.height - Options.GRID_PIXELSIZE * Pattern.NCELLS) / 4 - 50 / 2; //halfway between grid and bottom		
		patternReproductionDoneButton.setBounds(Options.screenSize.width / 2 - 100 / 2, heightOffset, 100, 50);
		
		fixationCross = new JLabel("+");
		fixationCross.setFont(new Font("Serif", Font.PLAIN, 50));
		int size = 30;
		fixationCross.setBounds(Options.screenSize.width / 2 - size / 2,  Options.screenSize.height / 2 - size / 2, size, size);
		
		keyboardManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		responseDispatcher = new ReponseDispatcher();
	}		
	
	/**
	 * Show a small fixation cross in the center of an otherwise empty screen.
	 */
	public void showFixationCross() {
		removeAll();
		add(fixationCross);
		validate(); 
		repaint();				
	}
	
	public void showBlankScreen(){ 
		removeAll();
		validate(); 
		repaint();				
	}
	
	/**
	 * Display the indicated pattern, so the ss can memorize it.
	 */
	public void showPattern(Pattern pattern) {
		removeAll();
		add(pattern.getGrid());
		int size = Options.GRID_PIXELSIZE * Pattern.NCELLS;
		pattern.getGrid().setBounds(Options.screenSize.width / 2 - size / 2, Options.screenSize.height / 2 - size / 2, size,  size);
		validate(); 
		repaint();			
	}		
	
	public void showCue(String word) {
		removeAll();
		cue.setForeground(Color.black);
		cue.setText(word);
		add(cue);
		skipButton.setState(SkipButton.unknownText);
		add(skipButton);
		responseField.setText("");
		add(responseField);
		for(int i = 0; i < previousAssociationLabels.length; i++) {
			previousAssociationLabels[i].setText("");
			add(previousAssociationLabels[i]);
		}
		
		validate();
		repaint();
		listenToFirstKeypress = true;
		enableKeyListener();
		responseField.grabFocus(); 		
	}
	
	public void showTooLateError() {
		removeAll();
		cue.setForeground(Color.red);
		cue.setText("Te traag!");
		add(cue);
		
		validate();
		repaint();
	}
	

	/**
	 * Submit the association the participant gave; if it was a first or second association, display it while pp thinks about subsequent associations.
	 */
	private void submitResponse(String a) {
		responseField.setText("");
		
		if (Experiment.xp.currentAssociationIndex < Options.N_ASSOCIATIONS) {		// this was first or second association	
			previousAssociationLabels[Experiment.xp.currentAssociationIndex - 1].setText(a); //-1 because that var is 1..3 and the array is 0..2		
			listenToFirstKeypress = true;
			skipButton.setState(SkipButton.noFurtherResponsesText);
		} else { //this was third association
			disableKeyListener();
		}
		Experiment.xp.submitResponse(a, timeAtAssociationsFirstKeypress, System.currentTimeMillis());
	}
	
	/**
	 * Show the screen in which the ss can reproduce the previously memorized pattern.
	 */
	public void showPatternReproduction() {		
		removeAll();
		showPattern(Pattern.emptyPattern);
		add(patternReproductionDoneButton);
		validate(); 
		repaint();	
	}
	
	private class SkipButton extends JButton {
		private static final String unknownText = "Onbekend woord";
		private static final String noFurtherResponsesText = "Geen verdere antwoorden";
		public String text;
		
		private SkipButton() {
			super(unknownText);
			text = unknownText;
			
			this.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { 
				disableKeyListener();
				if (text == unknownText) {
					Experiment.xp.skipFurtherAssociations("__UNKNOWN__");
				} else if (text == noFurtherResponsesText) {
					Experiment.xp.skipFurtherAssociations("__NO_FURTHER_RESPONSES__");
				} else {
					Experiment.xp.skipFurtherAssociations("__ERROR_(this should no be here)__");
				}
			}});	
		}
		
		public void setState(String state) {
			this.text = state;
			this.setText(text);
		}
		
	}
	
	private class ReponseDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED) {            	
            	if (e.getKeyCode() == KeyEvent.VK_ENTER) { //user pressed enter: try to submit response
            		if (responseField.getText().length() > 0) {            			
            			submitResponse(responseField.getText());
            		}
            	} else {
            		if (listenToFirstKeypress) {
            			Experiment.xp.removeResponseTimer();
            			listenToFirstKeypress = false;
            			timeAtAssociationsFirstKeypress = System.currentTimeMillis();
            		}
            	}			
            } 
            return false;
        }
	}

	public void enableKeyListener() {
		keyboardManager.addKeyEventDispatcher(responseDispatcher);
	}

	public void disableKeyListener() {
		keyboardManager.removeKeyEventDispatcher(responseDispatcher);
	}	

}