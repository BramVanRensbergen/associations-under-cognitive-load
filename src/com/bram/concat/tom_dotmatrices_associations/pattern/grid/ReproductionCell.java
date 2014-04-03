package com.bram.concat.tom_dotmatrices_associations.pattern.grid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A cell of the 4x4 grid that can be clicked by the user to add/remove dots, creating a pattern.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
@SuppressWarnings("serial")
public class ReproductionCell extends Cell implements ActionListener {
	protected ReproductionCell() {
		super(false);
		addActionListener(this);
		setEnabled(true);
	}	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (containsDot) {
			removeDot();
		} else {
			addDot();	
		}
	}
}