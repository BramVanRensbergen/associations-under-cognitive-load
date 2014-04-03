package com.bram.concat.tom_dotmatrices_associations.pattern.grid;

/**
 * A grid in which the user can create a pattern (reproducing the one memorized earlier).
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 *
 */
@SuppressWarnings("serial")
public class ReproductionGrid extends Grid {

	public ReproductionGrid(int[][] squares) {
		super(squares);		
	}
	
	protected void createCells(int[][] squares) {
		cells = new ReproductionCell[NCELLS][NCELLS];
		
		for (int row = 0; row < NCELLS; row++) {
			for (int col = 0; col < NCELLS; col++) {
				cells[row][col] = new ReproductionCell();
				add(cells[row][col]);
			}
		}	
	}
}
