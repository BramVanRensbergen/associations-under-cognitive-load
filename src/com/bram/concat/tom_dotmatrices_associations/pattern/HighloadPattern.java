package com.bram.concat.tom_dotmatrices_associations.pattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bram.concat.tom_dotmatrices_associations.pattern.grid.Grid;
import com.bram.concat.tom_dotmatrices_associations.pattern.validity.CRC;
import com.bram.concat.tom_dotmatrices_associations.pattern.validity.MirrorAndRotation;
import com.bram.concat.tom_dotmatrices_associations.pattern.validity.Tom;

/**
 * Create a random pattern of four dots in a 4x4 matrix, fulfilling three conditions:
 * 1) no two vertically or horizontally adjacent dots, and no three or four dots in either diagonal (see validity.Tom)
 * 2) CRC is below the cutoff value (see validity.CRC)
 * 3) Rotating the pattern by 90 deg in any direction, and/or mirroring the pattern, results in eight unique pattern (see validity.MirrorAndRotation)
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
public class HighloadPattern extends Pattern {	
	public HighloadPattern() {
		super(HIGHLOAD);		
	}
	
	@Override
	protected Grid createGrid() {	
		return new Grid(createPattern());
	}
	
	private int[][] createPattern() {
		List<Integer> positionNumbers = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15); //indices of the 16 places where a dot COULD be placed
		Collections.shuffle(positionNumbers); //randomize their order		
		List<Integer> dotPositions = positionNumbers.subList(0, 4); //get the first four i.e. four random numbers between 0 and 15 (without replacement)
		
		int[][] squares = new int[NCELLS][NCELLS]; 
		
		for (int nb:dotPositions) { //go through the four indices of the positions where we will place dots
			//get the row/col in a 4x4 matrix corresponding to those indices
			int row = (int) Math.floor(nb / NCELLS); 
			int col = nb % NCELLS;
			squares[row][col] = 1;
		}		
		if (!CRC.validByCrc(squares)) return createPattern(); //CRC not below the threshold
		if (!Tom.validByTom(squares)) return createPattern(); //2 adjacent dots, or 3 or 4 dots in either diagonal
		if (!MirrorAndRotation.validByMirroring(squares)) return createPattern(); //mirroring or rotating the pattern creates at least one duplicate
		return squares;
	}		
}