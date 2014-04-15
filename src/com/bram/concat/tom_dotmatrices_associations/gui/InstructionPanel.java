package com.bram.concat.tom_dotmatrices_associations.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bram.concat.tom_dotmatrices_associations.Options;
import com.bram.concat.tom_dotmatrices_associations.Text;
import com.bram.concat.tom_dotmatrices_associations.experiment.Experiment;

/**
 * Panel that displays the introduction to / explanation of the experiment..
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
@SuppressWarnings("serial")
public class InstructionPanel extends JPanel {		
	JEditorPane textPane;
	JButton mainButton, previousButton, nextButton;
	
	InstructionPanel() {
		setLayout(null); 	
		int width = (int) (Options.screenSize.width * 0.8);
		if (width > 1000) width = 1000;
		
		int height = 500;
		
		textPane = new JEditorPane("text/html", ""); //create text pane, make sure we can use html formatting
		textPane.setEditable(false);
		textPane.setBackground(getBackground());     //set backgroundcolor to parent's background
		textPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		textPane.setFont(Text.instructionFont);
		
		JScrollPane paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //hopefully we never need scrollbars, but just in case
        paneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        paneScrollPane.setBorder(BorderFactory.createEmptyBorder()); //remove border
		paneScrollPane.setBounds((int) (Options.screenSize.getWidth() / 2 - width / 2), 100, width, height); //centered
        add(paneScrollPane); //add instructions
        
        mainButton = new JButton("Beginnen"); 
		previousButton = new JButton("Vorige");
		nextButton = new JButton("Volgende");
		
		mainButton.setVisible(false);
		previousButton.setVisible(false);
		nextButton.setVisible(false);
		
		add(mainButton); 
		add(previousButton);
		add(nextButton);
		
		int buttonWidth = 120;
		mainButton.setBounds((int) (Options.screenSize.getWidth() / 2 - buttonWidth / 2), height + 125, buttonWidth, 35); 	
		previousButton.setBounds((int) (Options.screenSize.getWidth() / 2 - 120 / 2 - buttonWidth), height + 125, buttonWidth, 35); 	 
		nextButton.setBounds((int) (Options.screenSize.getWidth() / 2 + 120 / 2), height + 125, buttonWidth, 35); 	
		
		textPane.setCaretPosition(0); //scroll to top
	}
	
	public void showMainInstructions(final int pageIndex) {
		removeActionListeners(nextButton);
		removeActionListeners(previousButton);
		
		if (pageIndex < Text.mainInstructions.length) { //not last page
			nextButton.setText("Volgende");
			nextButton.setVisible(true);
			nextButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
				showMainInstructions(pageIndex + 1);
			}});
		} 
		
		if (pageIndex > 1) { //not first page
			previousButton.setText("Vorige");
			previousButton.setVisible(true);
			previousButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
				showMainInstructions(pageIndex - 1);
			}});
		} else {
			previousButton.setVisible(false);
		}
		
		if (pageIndex == Text.mainInstructions.length) { //last page			
			nextButton.setText("Beginnen");
			nextButton.setVisible(true);
			nextButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
				previousButton.setVisible(false);
				nextButton.setVisible(false);
				Experiment.xp.displayAndContinue(); 
			}});
		}				
		setInstructions(Text.mainInstructions[pageIndex - 1]);		
	}
	
	public void showFirstBlockPostTrainingInstructions() {
		mainButton.setVisible(true);
		mainButton.setText("Verder Gaan");
		removeActionListeners(mainButton);
		mainButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
			Experiment.xp.displayAndContinue(); 
		}});
		setInstructions(Text.postTrainingInstructions);	
	}
	
	/**
	 * @param lastblock Show the instructions for the last block
	 * Last block: generally slightly different from previous interblock instructions, e.g. they may refer to the upcoming block as the last one instead of the next one.
	 */
	public void showInterBlockInstructions(boolean lastblock) {
		mainButton.setText("Verder Gaan");
		removeActionListeners(mainButton);
		mainButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
			Experiment.xp.displayAndContinue(); 
		}});
		if (lastblock) {
			setInstructions(Text.interBlockInstructionsLast);
		} else {
			setInstructions(Text.interBlockInstructions);
		}
	}
	
	public void showXpOverText() {
		mainButton.setText("Afsluiten");
		removeActionListeners(mainButton);
		mainButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
			Experiment.gui.dispose(); 		//remove the layout
	        System.exit(0); //exit	
		}});
		setInstructions(Text.xpOverText);			
	}
	
	private void setInstructions(String text){
		Experiment.gui.showCursor();
		Experiment.gui.bg.removeAll(); 
		textPane.setText(text);	
		Experiment.gui.bg.add(this); //add introduction screen
		Experiment.gui.bg.validate(); 			  
		Experiment.gui.bg.repaint();
	}

	private void removeActionListeners(JButton button) {
		for (ActionListener al : button.getActionListeners()) {
			button.removeActionListener(al);
	    }
	}
}