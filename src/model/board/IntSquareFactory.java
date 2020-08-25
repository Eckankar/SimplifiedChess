package model.board;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A factory for creating squares with their position stored as one integer.
 * @author Sebastian Paaske Tørholm
 */
public class IntSquareFactory implements SquareFactory {
	@Override
	public Square create(int file, int rank) {
		return new IntSquare(file, rank);
	}

	/**
	 * Creates an IntSquare based on file and rank.
	 * 
	 * @param file
	 *            intended file for the square (as a letter)
	 * @param rank
	 *            intended rank for the square
	 * @require this.isSquare(Character.toLowerCase(file) - 'a' + 1, rank)
	 * @ensure this.file() = Character.toLowerCase(file) - 'a' + 1
	 * @ensure this.rank() = rank
	 */
	public Square create(char file, int rank) {		
		return new IntSquare(file, rank);
	}
	
	/**
	 * Creates an IntSquare based on a string representation of the tile
	 * position.
	 * 
	 * @param pos
	 *            string containing tile position
	 * @require new IntSquareFactory().isSquare(
	 *          Character.toUpperCase(pos.charAt(0)) - 'a' + 1,
	 *          Integer.parseInt(pos.substring(1)) );
	 * @ensure this.file() = Character.toLowerCase(pos.charAt(0)) - 'a' + 1
	 * @ensure this.rank() = Integer.parseInt(pos.substring(1))
	 */
	public Square create(String pos) {		
		return new IntSquare(pos);
	}

	@Override
	public boolean isSquare (int file, int rank) {
		return 1 <= file && file <= Square.MAX_FILE && 1 <= rank && rank <= Square.MAX_RANK;
	}


	@Override
	public Iterator<Square> iterator() {
		return new Iterator<Square>() {
			private int file = 0, // store file one below actual value
			            rank = 1; // so we can do modulo magic more easily on it
			
			@Override
			public boolean hasNext() {
				return isSquare(file+1, rank);
			}

			@Override
			public Square next() {
				if (!hasNext())
					throw new NoSuchElementException();
				
				Square nextSquare = new IntSquare(file+1, rank);
				
				file = (file+1)%Square.MAX_FILE;
				if (file == 0) // file wraparound, increase rank
					rank++;
				
				return nextSquare;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
