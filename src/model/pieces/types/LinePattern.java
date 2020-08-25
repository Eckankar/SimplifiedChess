package model.pieces.types;

import java.util.ArrayList;
import java.util.List;

import model.board.Position;
import model.board.Square;
import model.pieces.Piece;

/**
 * Defines a general pattern for pieces that move in straight lines.
 * @author Sebastian Paaske Tørholm
 */
public abstract class LinePattern implements MovementPattern {
	private final int[][] lineBoundaries;
	
	/**
	 * Create a new LinePattern.
	 * @param lineBoundaries boundaries for rays emanating from the piece
	 */
	protected LinePattern(int[][] lineBoundaries) {
		this.lineBoundaries = lineBoundaries;
	}
	
	@Override
	public List<int[]> legalMoves(Piece piece, Position board) {
		List<int[]> freeTiles = new ArrayList<int[]>();
		for (int[] ray : lineBoundaries)
			freeTiles.addAll(directionalRay(ray, piece, board));
		
		return freeTiles;
	}
	
	/**
	 * Returns which tiles are moveable to following a ray emanating with a given direction.
	 * Example: Say that direction is {3, 3}, then this method returns
	 * { {1,1}, {2,2}, {3,3} }, assuming there are no obstacles blocking the path to {3, 3}.
	 * If there is something blocking in {2, 2}, it will return { {1,1}, {2,2} }.
	 * Note that direction must either be on the form {0, y}, {x, 0} or
	 * {x, y} where Math.abs(x) == Math.abs(y).
	 * @param direction direction (and length) to emanate
	 * @param piece the piece to emanate from
	 * @param board the layout of the board
	 * @return see comment
	 * @require direction.length == 2
	 */
	private static List<int[]> directionalRay(int[] direction,
			                                  Piece piece,
			                                  Position board) {
		assert direction.length == 2;
		
		
		List<int[]> ray = new ArrayList<int[]>();
		
		// We're dealing with two kinds of rays here:
		// - Rays where one coordinate change
		// - Rays where both coordinates change by the same (mod sign) amount
		
		Square loc = piece.location();
		int f = loc.file(),
		    r = loc.rank(),
		    x = 0,
		    y = 0,
		    i = 0,
		    max, // numerical max, that is.
		    dx, dy;
		
		// Rays of one coordinate
		if (direction[0] == 0 || direction[1] == 0) {
			    max = direction[0] + direction[1]; 
			    dx = direction[0] != 0 ? 1 : 0;
			    dy = direction[1] != 0 ? 1 : 0;
			    
			    if (max < 0) {
			    	dx *= -1;
			    	dy *= -1;
			    	max *= -1;
			    }
		}
		
		// Rays of two coordinates (diagonal)
		else {
			max = Math.max(Math.abs(direction[0]), Math.abs(direction[1])); 
		    dx = direction[0] > 0 ? 1 : -1;
		    dy = direction[1] > 0 ? 1 : -1;	    
		}
		
		while (i < max) {
	    	x+=dx; y+=dy; i++;
	    	ray.add(new int[]{x, y});
	    	if (board.get(f+x,r+y) != null)
	    		break; // stop once we hit a piece
	    }
		
		return ray;
	}

}
