/**
 * Concrete factory for creating squares implemented by pairs of indexes
 */
package model.board;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A factory for creating squares, implemented by pairs of coordinates (file and rank)
 */
public class PairSquareFactory implements SquareFactory {

	public Square create(int file, int rank) {
		return new PairSquare(file, rank);
	}

	public boolean isSquare (int file, int rank) {
		return 1 <= file && file <= Square.MAX_FILE && 1 <= rank && rank <= Square.MAX_RANK;
	}
	
	public Iterator<Square> iterator() {
		return new Iterator<Square>() {
			private int file = 1; 
			private int rank = 1;
			public boolean hasNext() {
				return rank <= Square.MAX_RANK;
			}
			public Square next() {
				if (rank > Square.MAX_RANK) 
					throw new NoSuchElementException();
				Square f = new PairSquare (file, rank);
				if (file < Square.MAX_FILE)
					file++;
				else {
					file = 1; 
					rank++;
				};
				return f;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
}
