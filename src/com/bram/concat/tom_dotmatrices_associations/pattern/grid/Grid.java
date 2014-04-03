package com.bram.concat.tom_dotmatrices_associations.pattern.grid;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.bram.concat.tom_dotmatrices_associations.pattern.Pattern;

/**
 * A 4x4 grid containing cells that may contain a dot, forming a pattern.
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
@SuppressWarnings("serial")
public class Grid extends JPanel {		
	public static int NCELLS = Pattern.NCELLS;	
	public Cell[][] cells;
	
	public Grid(int[][] squares) { //empty grid
		super(new GridLayout(NCELLS,NCELLS));		
		createCells(squares);
	}
	
	protected void createCells(int[][] squares) {
		cells = new Cell[NCELLS][NCELLS];
		
		for (int row = 0; row < NCELLS; row++) {
			for (int col = 0; col < NCELLS; col++) {
				cells[row][col] = new Cell(squares[row][col] == 1);
				add(cells[row][col]);
			}
		}	
	}
}